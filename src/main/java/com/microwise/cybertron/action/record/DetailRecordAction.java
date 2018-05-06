package com.microwise.cybertron.action.record;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Maps;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Record;
import com.microwise.cybertron.service.RecordService;
import com.microwise.cybertron.sys.Cybertron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 档案详情 EventAction
 *
 * @author li.jianfei
 * @date 2014-07-22
 */
@Beans.Action
@Cybertron
@Route("/cybertron")
public class DetailRecordAction {
    public static final Logger log = LoggerFactory.getLogger(DetailRecordAction.class);
    private static final String _pagePath = "record/detail-record.ftl";

    @Autowired
    private RecordService recordService;

    /**
     * 卷 代码
     */
    private int volumeCode;

    /**
     * 档案ID
     */
    private String recordId;

    @Route("volumes/{volumeCode}/records/{recordId}/view")
    public String view() {
        return Results.ftl("/cybertron/pages/layout.ftl");
    }

    @Route(value = "records/{recordId}", method = MethodType.GET)
    public String recordDetail() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            Record record = recordService.findRecordById(recordId);
            data.put("success", true);
            data.put("record", record);
        } catch (Exception e) {
            data.put("success", false);
            log.error("查询档案详情", e);
        }
        return Results.json().asRoot(data).done();
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public int getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(int volumeCode) {
        this.volumeCode = volumeCode;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
