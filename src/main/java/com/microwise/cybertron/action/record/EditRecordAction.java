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
public class EditRecordAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(EditRecordAction.class);

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


    // Input
    /**
     * 卷 代码
     */
    private int volumeCode;

    /**
     * 上传的文件
     */
    private String files;

    /**
     * 档号
     */
    private String recordCode;

    /**
     * 档案id
     */
    private String recordId;

    /**
     * 附件ID
     */
    private String attatchId;

    /**
     * 用户填写的档号
     */
    private String code;

    /**
     * 显示
     */
    @Route("editRecord-view/{recordId}/volume/{volumeCode}")
    public String view() {
        //获取档案全宗号
        String siteId = Sessions.createByAction().currentSiteId();
        officialId = configService.findConfig(siteId).getOfficialId();
        record = recordService.findRecordById(recordId);
        volume = volumeService.findVolume(record.getVolumeCode());
        //获取用户可填写部分的档号，页面判断档号是否重复使用
        String recordCode = record.getRecordCode();
        code = recordCode.substring(recordCode.lastIndexOf("-") + 3);
        return Results.ftl("/cybertron/pages/layout.ftl");
    }


    /**
     * 添加
     */
    @Route("editRecord")
    public String editRecord() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            officialId = configService.findConfig(siteId).getOfficialId();
            //因为hibernate特性，获取有依赖关系的对象
            Record recordData = recordService.findRecordById(record.getUuid());
            recordData.setName(record.getName());
            recordData.setStateSecrets(record.getStateSecrets());
            recordData.setVolumeCode(record.getVolumeCode());
            recordData.setDepartment(record.getDepartment());
            recordData.setEstablishDate(record.getEstablishDate());
            recordData.setRecordCode(officialId + "-" + record.getVolumeCode() + record.getRecordCode());
            recordService.updateRecord(recordData);
            //编辑附件，有上传则添加记录入数据库
            if (!files.isEmpty()) {
                //页面获取的批量上传的文件名
                String[] fileNames = files.split(",");
                for (String name : fileNames) {
                    Attachment attachment = new Attachment();
                    attachment.setUuid(GalaxyIdUtil.get64UUID());
                    attachment.setRecord(recordData);
                    //获取的文件名 形式如  pathName-;.~-uploadName，将其分开，分别存入
                    String[] names = name.split("-;.~-");
                    attachment.setPath(names[0]);
                    attachment.setUserId(Sessions.createByAction().currentUser().getId());
                    attachment.setUploadDate(new Date());
                    attachment.setUploadName(names[1]);
                    attachmentService.addAttachment(attachment);
                }
            }
            ActionMessage.createByAction().success("编辑档案成功");
            log("档案管理", "编辑档案");
        } catch (Exception e) {
            log.error("档案管理编辑档案", e);
        }
        return Results.redirect("volume/" + record.getVolumeCode() + "/records");
    }


    /**
     * 页面案卷题名的js联动
     */
    @Route("findVolume/{volumeCode}")
    public String findVolume() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            volume = volumeService.findVolume(volumeCode);
            map.put("success", volume.getName());
        } catch (Exception e) {
            map.put("fail", e);
        }
        return Results.json().asRoot(map).done();
    }


    /**
     * 删除附件
     */
    @Route("deleteAttatch/{attatchId}")
    public String delete() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            attachmentService.deleteAttachment(attatchId);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return Results.json().asRoot(map).done();
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public String getAttatchId() {
        return attatchId;
    }

    public void setAttatchId(String attatchId) {
        this.attatchId = attatchId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
