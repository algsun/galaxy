package com.microwise.cybertron.action.record;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Maps;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Record;
import com.microwise.cybertron.bean.Volume;
import com.microwise.cybertron.service.RecordService;
import com.microwise.cybertron.service.VolumeService;
import com.microwise.cybertron.sys.Cybertron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 档案查询 EventAction
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
@Beans.Action
@Cybertron
@Route("/cybertron")
public class QueryRecordAction {
    public static final Logger log = LoggerFactory.getLogger(QueryRecordAction.class);
    private static final String _pagePath = "index.ftl";

    @Autowired
    private RecordService recordService;

    @Autowired
    private VolumeService volumeService;

    // Input
    /**
     * 卷代码
     */
    private int volumeCode;

    @Route("volume/{volumeCode}/records")
    public String index() {
        ActionMessage.createByAction().consume();
        return Results.ftl("/cybertron/pages/layout.ftl");
    }

    @Route(value = "volume/{volumeCode}/records.json", method = MethodType.GET)
    public String ajaxQueryRecords() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            List<Record> recordList = recordService.findRecordList(siteId, volumeCode);
            data.put("success", true);
            data.put("records", recordList);
        } catch (Exception e) {
            data.put("success", false);
            log.error("查询卷——档案", e);
        }
        return Results.json().asRoot(data).done();
    }

    @Route("find-volume-tree.json")
    public String findVolumeTree() {
        List<Volume> volumeList = volumeService.findVolumeList();
        return Results.json().asRoot(getTreeJson(volumeList)).done();
    }

    /**
     * 封装json数据
     */
    private List<Map<String, Object>> getTreeJson(List<Volume> volumeList) {
        List<Map<String, Object>> stringMapList = new ArrayList<Map<String, Object>>();
        for (Volume v : volumeList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", v.getVolumeCode());
            map.put("pId", v.getParentCode());
            map.put("name", v.getName());
            map.put("iconSkin", "icon01");
            stringMapList.add(map);
        }
        return stringMapList;
    }


    public int getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(int volumeCode) {
        this.volumeCode = volumeCode;
    }

    public static String get_pagePath() {
        return _pagePath;
    }
}
