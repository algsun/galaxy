package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCItemPO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 布局新增操作Action
 *
 * @author wang.geng
 * @date 13-12-6 下午3:00
 * @check @liu.zhu wang.geng 2013-12-18 #7081
 * @check @xie.deng li.jianfei 2014-3-5 #  8049
 */
@Beans.Action
@Blueplanet
public class DcAddLayoutAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(DcAddLayoutAction.class);

    /**
     * 数据中心服务
     */
    @Autowired
    private DataCenterService dataCenterService;

    //output
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-add-layout.ftl";

    /**
     * 添加布局结果对象
     */
    private Map<String, Object> resultMap;

    //input
    /**
     * 布局参数json字符串
     */
    private String jsonStr;

    /**
     * 布局的UUID
     */
    private String uuid;

    /**
     * 布局名称
     */
    private String layoutDescription;

    /**
     * siteId站点id
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/blueplanet/dataCenter/charts/addLayoutItems.json")
    public String addLayoutItems() {

        // 获取当前站点组id
        int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();

        resultMap = new HashMap<String, Object>();
        List<DCItemPO> items = new ArrayList<DCItemPO>();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("jsonString");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                DCItemPO item = new DCItemPO();
                item.setRelated_layoutId(uuid);
                item.setItem_id(jsonObj.get("id").toString());
                item.setData_col(Integer.parseInt(jsonObj.get("data-col").toString()));
                item.setData_row(Integer.parseInt(jsonObj.get("data-row").toString()));
                item.setData_sizex(Integer.parseInt(jsonObj.get("data-sizex").toString()));
                item.setData_sizey(Integer.parseInt(jsonObj.get("data-sizey").toString()));
                item.setItemType(Integer.parseInt(jsonObj.get("itemType").toString()));
                items.add(item);
            }
            if (items.size() > 0) {
                dataCenterService.saveLayoutItems(items);
            }
            if (!Strings.isNullOrEmpty(layoutDescription)) {
                dataCenterService.updateLayoutName(uuid, layoutDescription, logicGroupId);
                resultMap.put("layoutDescription", layoutDescription);
            }
            resultMap.put("success", true);
        } catch (JSONException e) {
            resultMap.put("success", false);
            log.error("数据中心(图表组件化),添加布局控件控件", e);
        }
        log("数据中心(图表组件化)", "添加布局控件控件");
        return Results.json().root("resultMap").done();
    }

    @Route("/blueplanet/dataCenter/charts/toAddLayout")
    public String addLayout() {
        try {
            uuid = GalaxyIdUtil.get64UUID();
        } catch (Exception e) {
            log.error("数据中心(图表组件化),生成uuid", e);
        }
        log("数据中心(图表组件化)", "生成uuid");
        return Results.redirect(String.format("/blueplanet/dataCenter/charts/index/%s", uuid));
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public String getLayoutDescription() {
        return layoutDescription;
    }

    public void setLayoutDescription(String layoutDescription) {
        this.layoutDescription = layoutDescription;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
