package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Charsets;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCConditionPO;
import com.microwise.blueplanet.bean.po.DCConfigPO;
import com.microwise.blueplanet.bean.po.DCItemPO;
import com.microwise.blueplanet.bean.po.DCLayoutPO;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.bean.vo.ZoneLocationVO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 布局编辑操作Action.
 *
 * @author wang.geng
 * @date 13-12-9  下午2:39
 * @check @liu.zhu wang.geng 2013-12-18 #7222
 * @check @li.jianfei, xie.deng 2014年3月5日 #8053
 */
@Beans.Action
@Blueplanet
public class PreViewAction extends BlueplanetLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(PreViewAction.class);

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-add-layout.ftl";

    /**
     * 数据中心service
     */
    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private LocationService locationService;

    //input
    /**
     * 布局的UUID
     */
    private String uuid;

    // output
    /**
     * 设备List
     */
    private List<DeviceVO> deviceVOList;

    /**
     * 默认结束时间
     */
    private Date startTime;

    /**
     * 默认结束时间
     */
    private Date endTime;

    /**
     * 布局控件集合
     */
    private List<DCItemPO> itemList;

    /**
     * 所有的图形数据对象map
     */
    private Map<String, Map<String, Object>> highChartDataMaps;

    /**
     * 操作标记：preview为布局预览;edit为编辑操作
     */
    private String operMark;

    /**
     * 行政站点子站点列表
     */
    private List<SiteVO> siteVOList;

    /**
     * 用户登录组
     */
    private int loginLogicGroupId;

    /**
     * 布局名称
     */
    private String layoutDescription;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    //初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator+ "blueplanet"+File.separator+"images"+File.separator+"dataCenterConf";
    }

    /**
     * 是否配置了背景图片
     */
    private boolean hasBgUrl;

    /**
     * 输出流
     */
    private ByteArrayInputStream inputStream;

    /**
     * 位置点分组（按区域）
     */
    private List<ZoneLocationVO> zoneLocationList;

    /**
     * siteId站点id
     */
    private String siteId;

    @Route("/blueplanet/dataCenter/charts/toPreView/{uuid}/{operMark}")
    public String toUpdateItem() {
        try {
            siteId = Sessions.createByAction().currentSiteId();
            if ("edit".equals(operMark)) {
                if (siteId == null) {
                    siteVOList = dataCenterService.findSiteVOByLogicGroupId(Sessions.createByAction().currentLogicGroup().getId());
                } else {
                    zoneLocationList = dataCenterService.findZoneLocationBySiteId(siteId);
                }
            }
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            startTime = DateTimeUtil.startOfDay(new Date());
            endTime = DateTimeUtil.endOfDay(new Date());
            itemList = dataCenterService.findItemByLayoutId(uuid);
            List<DCLayoutPO> layouts = dataCenterService.findAllLayouts(logicGroupId);
            for (DCLayoutPO layout : layouts) {
                if (uuid.equals(layout.getLayoutId())) {
                    layoutDescription = layout.getDescription();
                }
            }

            DCConfigPO dcConfigPO = dataCenterService.findDCConfig(uuid);

            hasBgUrl = dcConfigPO != null;

            deleteUnnecessaryData(uuid);

        } catch (Exception e) {
            log.error("修改item", e);
        }
        log("数据中心", "更新");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    @Route("/blueplanet/dataCenter/charts/shortcutlogin/{uuid}/{operMark}/{loginLogicGroupId}")
    public String shortcutLogin() {
        try {
            List<DCLayoutPO> layouts = dataCenterService.findAllLayouts(loginLogicGroupId);
            for (DCLayoutPO layout : layouts) {
                if (uuid.equals(layout.getLayoutId())) {
                    layoutDescription = layout.getDescription();
                }
            }
            itemList = dataCenterService.findItemByLayoutId(uuid);
            DCConfigPO dcConfigPO = dataCenterService.findDCConfig(uuid);
            hasBgUrl = dcConfigPO != null;
            deleteUnnecessaryData(uuid);
        } catch (Exception e) {
            log.error("修改item", e);
        }
        log("数据中心", "更新");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout-login");
    }

    @Route("/blueplanet/dataCenter/charts/downLoadShort/{uuid}/{layoutDescription}")
    public String downLoadShortcut() {

        int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        HttpServletRequest request = ServletActionContext.getRequest();

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        basePath = basePath + "blueplanet/dataCenter/charts/shortcutlogin/" + uuid + "/shortcut/" + logicGroupId;
        String content = "[InternetShortcut]\n" + "URL=" + basePath;

        byte[] bytes = content.getBytes();
        inputStream = new ByteArrayInputStream(bytes);
        layoutDescription = new String(layoutDescription.getBytes(), Charsets.ISO_8859_1);
        return Results.stream().inputName("inputStream")
                .contentType("application/octet-stream; charset=ISO8859-1")
                .contentDisposition("attachment;filename=" + layoutDescription + ".url")
                .done();
    }

    /**
     * 删除图标条件保存，但控件未保存的数据
     *
     * @param layoutId 布局ID
     */
    private void deleteUnnecessaryData(String layoutId) {
        List<DCConditionPO> conditions = dataCenterService.findConditions(layoutId);
        List<DCItemPO> items = dataCenterService.findItemByLayoutId(layoutId);
        for (DCConditionPO conditionPO : conditions) {
            boolean flag = false;
            String conditionItemId = conditionPO.getRelated_item_id();
            for (DCItemPO itemPO : items) {
                String itemId = itemPO.getItem_id();
                if (conditionItemId.equals(itemId)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                dataCenterService.deleteConditionByItemId(conditionItemId);
            }
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<DeviceVO> getDeviceVOList() {
        return deviceVOList;
    }

    public void setDeviceVOList(List<DeviceVO> deviceVOList) {
        this.deviceVOList = deviceVOList;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<DCItemPO> getItemList() {
        return itemList;
    }

    public void setItemList(List<DCItemPO> itemList) {
        this.itemList = itemList;
    }

    public Map<String, Map<String, Object>> getHighChartDataMaps() {
        return highChartDataMaps;
    }

    public void setHighChartDataMaps(Map<String, Map<String, Object>> highChartDataMaps) {
        this.highChartDataMaps = highChartDataMaps;
    }

    public String getOperMark() {
        return operMark;
    }

    public void setOperMark(String operMark) {
        this.operMark = operMark;
    }

    public String getLayoutDescription() {
        return layoutDescription;
    }

    public void setLayoutDescription(String layoutDescription) {
        this.layoutDescription = layoutDescription;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }

    public boolean isHasBgUrl() {
        return hasBgUrl;
    }

    public void setHasBgUrl(boolean hasBgUrl) {
        this.hasBgUrl = hasBgUrl;
    }

    public int getLoginLogicGroupId() {
        return loginLogicGroupId;
    }

    public void setLoginLogicGroupId(int loginLogicGroupId) {
        this.loginLogicGroupId = loginLogicGroupId;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<SiteVO> getSiteVOList() {
        return siteVOList;
    }

    public void setSiteVOList(List<SiteVO> siteVOList) {
        this.siteVOList = siteVOList;
    }

    public List<ZoneLocationVO> getZoneLocationList() {
        return zoneLocationList;
    }

    public void setZoneLocationList(List<ZoneLocationVO> zoneLocationList) {
        this.zoneLocationList = zoneLocationList;
    }
}
