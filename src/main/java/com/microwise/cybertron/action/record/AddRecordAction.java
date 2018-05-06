package com.microwise.cybertron.action.record;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.cybertron.bean.Attachment;
import com.microwise.cybertron.bean.Record;
import com.microwise.cybertron.bean.Volume;
import com.microwise.cybertron.service.AttachmentService;
import com.microwise.cybertron.service.ConfigService;
import com.microwise.cybertron.service.RecordService;
import com.microwise.cybertron.service.VolumeService;
import com.microwise.cybertron.sys.Cybertron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Beans.Action
@Cybertron
@Route("/cybertron")
public class AddRecordAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(AddRecordAction.class);

    private static final String _pagePath = "record/add-edit-record.ftl";

    @Autowired
    private RecordService recordService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private VolumeService volumeService;
    /**
     * 档案
     */
    private Record record;


    /**
     * 档案全宗号
     */
    private String officialId;

    /**
     * 卷
     */
    private Volume volume;

    /**
     * 卷 代码
     */
    private int volumeCode;

    /**
     * 上传的文件名
     */
    private String files;

    /**
     * 档号
     */
    private String recordCode;


    /**
     * 显示
     */
    @Route("addRecord-view/{volumeCode}")
    public String view() {
        //获得档案全宗号
        String siteId = Sessions.createByAction().currentSiteId();
        officialId = configService.findConfig(siteId).getOfficialId();
        //获得卷对象
        volume = volumeService.findVolume(volumeCode);
        return Results.ftl("/cybertron/pages/layout.ftl");
    }

    /**
     * 判断用户填写的档号是否存在
     */
    @Route("isRecordExist/{recordCode}")
    public String isExist() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            boolean isExist = recordService.isRecordCodeExist(recordCode, siteId);
            map.put("success", isExist);
        } catch (Exception e) {
            map.put("success", "error");
        }
        return Results.json().asRoot(map).done();
    }


    /**
     * 添加档案
     */
    @Route("addRecord")
    public String addRecord() {
        try {
            //获取全宗号
            String siteId = Sessions.createByAction().currentSiteId();
            officialId = configService.findConfig(siteId).getOfficialId();
            record.setUuid(GalaxyIdUtil.get64UUID());
            record.setSiteId(siteId);
            //拼接档号
            record.setRecordCode(officialId + "-" + record.getVolumeCode() + record.getRecordCode());
            //添加档案
            recordService.addRecord(record);
            //获取有依赖关系的档案对象
            record = recordService.findRecordById(record.getUuid());
            if (!files.isEmpty()) {
                //页面获取的批量上传的文件名
                String[] fileNames = files.split(",");
                for (String name : fileNames) {
                    Attachment attachment = new Attachment();
                    attachment.setUuid(GalaxyIdUtil.get64UUID());
                    attachment.setRecord(record);
                    //获取的文件名 形式如  pathName-;.~-uploadName，将其分开，分别存入
                    String[] names = name.split("-;.~-");
                    attachment.setPath(names[0]);
                    attachment.setUserId(Sessions.createByAction().currentUser().getId());
                    attachment.setUploadDate(new Date());
                    attachment.setUploadName(names[1]);
                    //保存附件
                    attachmentService.addAttachment(attachment);
                }
            }
            ActionMessage.createByAction().success("添加档案成功");
            log("档案管理", "添加档案");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("添加档案失败");
            log.error("档案管理添加档案", e);
        }
        return Results.redirect("volume/" + record.getVolumeCode() + "/records");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public int getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(int volumeCode) {
        this.volumeCode = volumeCode;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getOfficialId() {
        return officialId;
    }

    public void setOfficialId(String officialId) {
        this.officialId = officialId;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

}
