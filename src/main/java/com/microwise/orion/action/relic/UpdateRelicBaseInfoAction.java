package com.microwise.orion.action.relic;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Era;
import com.microwise.orion.bean.Level;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.Texture;
import com.microwise.orion.service.EraService;
import com.microwise.orion.service.LevelService;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.service.TextureService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.proxima.bean.Zone;
import com.opensymphony.xwork2.Action;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.util.List;

/**
 * 更新档案的基础信息
 *
 * @author wanggeng
 * @date : 13-6-3 上午10:01
 * @check @gaohui #3997 2013-06-05
 */
@Beans.Action
@Orion
public class UpdateRelicBaseInfoAction extends OrionLoggerAction {

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;

    /**
     * 时代
     */
    @Autowired
    private EraService eraService;

    /**
     * 质地
     */
    @Autowired
    private TextureService textureService;

    /**
     * 级别
     */
    @Autowired
    private LevelService levelService;

    //input
    /**
     * 文物ID
     */
    private int relicId;

    //output
    /**
     * 查询时代列表
     */
    private List<Era> listEra;

    /**
     * 查询级别列表
     */
    private List<Level> listLevel;

    /**
     * 查询质地列表
     */
    private List<Texture> listTexture;

    //input and output
    //---字典表信息
    /**
     * 时代
     */
    private String era;
    /**
     * 级别
     */
    private String level;
    /**
     * 文物质地
     */
    private String texture;

    /**
     * 编目号
     */
    private String catalogCode;

    /**
     *文物总登记号
     * */
    private String totalCode;
    /**
     * 分类号
     */
    private String typeCode;
    /**
     * 名称
     */
    private String name;

    /**
     * 件数
     */
    private int count;
    /**
     * 区域ID
     */
    private String zoneId;

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
     * 藏品信息，基础信息更新
     *
     * @return
     */
    public String execute() {
        Client client = null;

        String configStr = ConfigFactory.getInstance().getConfig("config.properties")
                .get("orion.relic.fullTextSearch.enable");
        boolean fullTextSearchEnable = Boolean.parseBoolean(configStr);
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            listEra = eraService.findAllEra();
            listLevel = levelService.findAllLevel();
            listTexture = textureService.findAllTexture();
            Relic relic = relicService.findRelicByRelicId(relicId, siteId);
            for (Era e : listEra) {
                if (e.getId() == Integer.parseInt(era)) {
                    relic.setEra(e);
                }
            }

            for (Level l : listLevel) {
                if (l.getId() == Integer.parseInt(level)) {
                    relic.setLevel(l);
                }
            }

            for (Texture t : listTexture) {
                if (t.getId() == Integer.parseInt(texture)) {
                    relic.setTexture(t);
                }
            }
            if (Strings.isNullOrEmpty(zoneId)) {
                relic.setZone(null);
            } else {
                Zone z = new Zone();
                z.setId(zoneId);
                relic.setZone(z);
            }
            if ("".equals(totalCode.trim())) {
                relic.setTotalCode(null);
            }else{
                relic.setTotalCode(totalCode);
            }
            relic.setTypeCode(typeCode);
            relic.setCatalogCode(catalogCode);
            relic.setCount(count);
            relic.setName(name);
            relicService.updateRelic(relic);

            if (fullTextSearchEnable) {

                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));

                // 重建索引
                relic = relicService.findRelicByRelicId(relic.getId(), relic.getSiteId());
                client.prepareIndex("galaxy", relic.getSiteId(), "" + relic.getId()).setSource(relicService.prepareRelicJson(relic)).execute().actionGet();
            }

            ActionMessage.createByAction().success("基础信息保存成功");
            log("藏品信息", "基础信息更新，文物ID：" + relic.getId());
        } catch (Exception e) {
            ActionMessage.createByAction().fail("基础信息保存失败");
            logFailed("信息保存失败", "基础信息保存失败");
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

    public List<Era> getListEra() {
        return listEra;
    }

    public void setListEra(List<Era> listEra) {
        this.listEra = listEra;
    }

    public List<Level> getListLevel() {
        return listLevel;
    }

    public void setListLevel(List<Level> listLevel) {
        this.listLevel = listLevel;
    }

    public List<Texture> getListTexture() {
        return listTexture;
    }

    public void setListTexture(List<Texture> listTexture) {
        this.listTexture = listTexture;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }
}
