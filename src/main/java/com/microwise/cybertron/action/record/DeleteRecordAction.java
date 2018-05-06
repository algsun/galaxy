package com.microwise.cybertron.action.record;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Maps;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.service.RecordService;
import com.microwise.cybertron.sys.Cybertron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 删除文档 EventAction
 *
 * @author li.jianfei
 * @date 2014-07-17
 */
@Beans.Action
@Cybertron
@Route("/cybertron")
public class DeleteRecordAction {

    public static final Logger log = LoggerFactory.getLogger(DeleteRecordAction.class);

    @Autowired
    private RecordService recordService;

    // Input
    /**
     * 档案ID
     */
    private String recordUuid;

    @Route(value = "records/{recordUuid}", method = MethodType.DELETE)
    public String ajaxDeleteRecord() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            recordService.deleteRecord(recordUuid);
            data.put("success", true);
        } catch (Exception e) {
            data.put("success", false);
            log.error("删除档案", e);
        }
        return Results.json().asRoot(data).done();
    }

    public String getRecordUuid() {
        return recordUuid;
    }

    public void setRecordUuid(String recordUuid) {
        this.recordUuid = recordUuid;
    }
}
