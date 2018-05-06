package com.microwise.orion.action.relic;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Property;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicProperty;
import com.microwise.orion.service.RelicPropertyService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;

/**
 * 更新扩展属性信息.
 *
 * @author wanggeng
 * @date: 13-6-3 下午1:00
 * @check @gaohui #4046 2013-06-05
 */
@Beans.Action
@Orion
public class UpdateRelicPropertyInfoAction extends OrionLoggerAction {

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;

    /**
     * 文物属性信息
     */
    @Autowired
    private RelicPropertyService relicPropertyService;

    //input
    /**
     * 文物ID
     */
    private int relicId;

    /**
     * 接收到的属性信息值
     */
    private String propertyValue;
    /**
     * 属性ID
     * <p>
     */
    private int relicPropertyId = 0;
    /**
     * 属性id
     */
    private int propertyId;

    // input & output
    /**
     * 页面内跳转参数
     */
    private String pageNum;

    private int index;

    /**
     * 全文检索ip
     */
    private String ip = ConfigFactory.getInstance().
            getConfig(Constants.Config.CONFIG_PROPERTIES_URL).
            get("orion.relic.fullTextSearch.ip");
    /**
     * 全文检索端口
     */
    private Integer port = Integer.parseInt(ConfigFactory.getInstance().
            getConfig(Constants.Config.CONFIG_PROPERTIES_URL).
            get("orion.relic.fullTextSearch.port"));

    /**
     * 藏品信息，扩展信息保存
     *
     * @return
     */
    public String execute() {

        if (Strings.isNullOrEmpty(propertyValue) && propertyId != 6) {
            return Action.SUCCESS;
        }

        String configStr = ConfigFactory.getInstance().getConfig("config.properties")
                .get("orion.relic.fullTextSearch.enable");
        boolean fullTextSearchEnable = Boolean.parseBoolean(configStr);

        Client client = null;
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            Relic relic = relicService.findRelicByRelicId(relicId, siteId);
            Property property = new Property();
            property.setId(propertyId);

            RelicProperty relicProperty = new RelicProperty();
            if (relicPropertyId != 0) {
                relicProperty.setId(relicPropertyId);
            }
            relicProperty.setRelic(relic);
            relicProperty.setPropertyValue(propertyValue);
            relicProperty.setProperty(property);

            relicPropertyService.saveOrUpdateRelicProperty(Lists.newArrayList(relicProperty));

            if (fullTextSearchEnable) {
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
                // 重建索引
                relic = relicService.findRelicByRelicId(relic.getId(), relic.getSiteId());
                client.prepareUpdate("galaxy", relic.getSiteId(), "" + relic.getId()).setDoc(relicService.prepareRelicJson(relic)).get();
            }

            ActionMessage.createByAction().success("扩展信息保存成功");
            log("藏品信息", "编辑文物扩展信息，文物ID：" + relic.getId());
        } catch (Exception e) {
            ActionMessage.createByAction().fail("扩展信息保存失败");
            logFailed("藏品信息编辑失败", "扩展信息保存失败");
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return Action.SUCCESS;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public int getRelicPropertyId() {
        return relicPropertyId;
    }

    public void setRelicPropertyId(int relicPropertyId) {
        this.relicPropertyId = relicPropertyId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
