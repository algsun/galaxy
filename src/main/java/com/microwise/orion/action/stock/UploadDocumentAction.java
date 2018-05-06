package com.microwise.orion.action.stock;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.OutEventAttachment;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 挂接文档Action
 *
 * @author: wanggeng
 * @date: 13-8-1 下午1:18
 */
@Beans.Action
@Orion
public class UploadDocumentAction extends OrionLoggerAction {
    /**
     * 文档拼接的日期格式
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 出入库管理挂接文档上传子目录名
     */
    public static final String OUT_EVEBT_UPLOAD_PATH = "stock_document";

    /**
     * 出库之间Service
     */
    @Autowired
    private OutEventService outEventService;

    //input
    /**
     * 出库时间id
     */
    private String outEventId;

    /**
     * 隐藏域传递文件名
     */
    private String hideFileName;

    /**
     * 挂接文档上传
     */
    private File srcUploadFile;

    /**
     *挂接文档名称
     */
    private String srcUploadFileFileName;

    /**
     * 被下载文件的id
     */
    private int downloadFileId;

    //output
    /**
     * 出库事件
     */
    private OutEvent outEvent;

    /**
     * 根据传入的 attachmentId 删除 文档
     */
    private int attachmentId;

    /**
     * 出库事件的关联文档
     */
    private List<OutEventAttachment> outEventAttachmentList;

    /**
     * 跳转到文档上传页面
     *
     * @return
     */
    public String view() {
        try {
            ActionMessage.createByAction().consume();
            outEvent = outEventService.findById(outEventId);
            outEventAttachmentList = outEventService.findOutEventAttachmentByEventId(outEventId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 文档下载
     *
     * @return
     */
    public String downloadDocFile() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            int fileId = Integer.parseInt(request.getParameter("downloadFileId"));
            OutEventAttachment outEventAttachment = getOutEventAttachmentById(fileId, outEventId);
            String fileName = "";
            if (outEventAttachment != null) {
                fileName = outEventAttachment.getPath();
            }
            UpLoadFileUtil.fileDownLoad(OUT_EVEBT_UPLOAD_PATH + File.separator + outEventId, fileName, "/orion");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 根据id删除文档
     * 1.删除数据库文档记录
     * 2.删除服务器对应文档实体
     *
     * @return
     */
    public String deleteDocumentById() {
        try {
            if (attachmentId == 0) {
                return Action.SUCCESS;
            }

            //获取文件名
            OutEventAttachment outEventAttachment = getOutEventAttachmentById(attachmentId, outEventId);
            String fileName = "";
            if (outEventAttachment != null) {
                fileName = getOutEventAttachmentById(attachmentId, outEventId).getPath();
            }

            //删除文档数据库记录
            outEventService.deleteOutEventAttachment(attachmentId);

            //删除服务器对应文档实体
            UpLoadFileUtil.deleteFileByFileName(fileName, OUT_EVEBT_UPLOAD_PATH + File.separator + outEventId, "/orion");
            ActionMessage.createByAction().success("文件删除成功");
            log("成功删除挂接文档", "出库单号：" + outEventId);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("文件删除失败");
            logFailed("删除文档失败", "删除文档失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 文档上传
     *
     * @return
     */
    public String execute() {
        if (hideFileName == null) {
            ActionMessage.createByAction().fail("文件上传不能超过5M");
            return Action.SUCCESS;
        }
        String fileName = srcUploadFileFileName;
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "【" + dateFormat.format(new Date()) + "】" + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String path = UpLoadFileUtil.getUploadPath("/orion") + File.separator + OUT_EVEBT_UPLOAD_PATH + File.separator + outEventId;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String newPath = path + File.separator + fileName;
        File destUploadFile = new File(newPath);
        String isUpload = UpLoadFileUtil.fileUpload(srcUploadFile, destUploadFile);
        if ("outOfLength".equals(isUpload)) {
            ActionMessage.createByAction().fail("上传文件不能超过5M");
            return Action.SUCCESS;
        }
        if ("true".equals(isUpload)) {
            try {
                addFileToDB(fileName);
                ActionMessage.createByAction().success("文件上传成功");
                log("藏品信息", "挂接文档上传，文档名称： " + fileName);
            } catch (Exception e) {
                ActionMessage.createByAction().fail("文件上传失败");
                logFailed("挂接文档上传失败", "挂接文档上传失败");
                e.printStackTrace();
            }
        }
        return Action.SUCCESS;
    }

    /**
     * 添加挂接文档信息到数据库
     *
     * @param fileName
     */
    private void addFileToDB(String fileName) {
        try {
            outEventService.addOutEventAttachment(outEventId,
                    Sessions.createByAction().currentUser().getId(), fileName, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据出库事件id与文档id，返回文档实体
     *
     * @param attachmentId 文档id
     * @param outEventId   出库事件id
     * @return outEventAtt文件实体
     */
    private OutEventAttachment getOutEventAttachmentById(int attachmentId, String outEventId) {
        OutEventAttachment outEventAtt = null;
        try {
            List<OutEventAttachment> outEventAttachments = outEventService.findOutEventAttachmentByEventId(outEventId);
            for (OutEventAttachment outEventAttachment : outEventAttachments) {
                if (outEventAttachment.getId() == attachmentId) {
                    outEventAtt = outEventAttachment;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outEventAtt;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }

    public List<OutEventAttachment> getOutEventAttachmentList() {
        return outEventAttachmentList;
    }

    public void setOutEventAttachmentList(List<OutEventAttachment> outEventAttachmentList) {
        this.outEventAttachmentList = outEventAttachmentList;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getHideFileName() {
        return hideFileName;
    }

    public void setHideFileName(String hideFileName) {
        this.hideFileName = hideFileName;
    }

    public File getSrcUploadFile() {
        return srcUploadFile;
    }

    public void setSrcUploadFile(File srcUploadFile) {
        this.srcUploadFile = srcUploadFile;
    }

    public int getDownloadFileId() {
        return downloadFileId;
    }

    public void setDownloadFileId(int downloadFileId) {
        this.downloadFileId = downloadFileId;
    }

    public String getSrcUploadFileFileName() {
        return srcUploadFileFileName;
    }

    public void setSrcUploadFileFileName(String srcUploadFileFileName) {
        this.srcUploadFileFileName = srcUploadFileFileName;
    }
}
