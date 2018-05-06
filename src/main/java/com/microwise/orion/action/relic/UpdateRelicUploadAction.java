package com.microwise.orion.action.relic;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.QiniuUtil;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图片上传Action
 * 照片，拓片，铭文题跋
 *
 * @author wanggeng
 * @date: 13-6-3 下午1:42
 * @check @gaohui #4008 2013-06-05
 */
@Beans.Action
@Orion
public class UpdateRelicUploadAction extends OrionLoggerAction {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 藏品信息挂接文档上传子目录
     */
    private static final String FILE_UPLOAD_PATH = "file";

    /**
     * 文档编辑，图片上传子目录
     */
    private static final String IMAGE_UPLOAD_PATH = "images";

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;
    /**
     * 图片
     */
    @Autowired
    private PhotoService photoService;

    /**
     * 拓片
     */
    @Autowired
    private RubbingService rubbingService;

    /**
     * 铭文题跋
     */
    @Autowired
    private InscriptionService inscriptionService;

    /**
     * 挂接文档
     */
    @Autowired
    private AttachmentService attachmentService;

    //input
    /**
     * 文物ID
     */
    private int relicId;
    /**
     * 站点
     */
    private String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();
    /**
     * 图片上传类型
     * type:photo 照片
     * rubbing 拓片
     * inscription 铭文题跋
     */
    private String photoType;
    /**
     * 上传的图片文件
     */
    private File fileImage;
    /**
     * 挂接文档上传
     */
    private File srcUploadFile;
    /**
     * 铭文题跋信息
     */
    private String fileImagetext;
    /**
     * 隐藏域传递文件名
     */
    private String hideFileName;

    /**
     * 文件名
     */
    private String srcUploadFileFileName;

    /**
     * 铭文提拔描述
     */
    private String inscriptionDescription;

    //output
    /**
     * 档案
     */
    private Relic relic;
    /**
     * 页面内跳转参数
     */
    private String pageNum;

    private int index;

    /**
     * 文件上传Action
     * 挂接文档上传
     *
     * @return
     */
    public String uploadFileAction() {
        relic = relicService.findRelicByRelicId(relicId, siteId);
        if (hideFileName == null) {
            ActionMessage.createByAction().fail("文件上传不能超过5M");
            return Action.SUCCESS;
        }
        String fileName = srcUploadFileFileName;
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "【" + dateFormat.format(new Date()) + "】" + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String path = UpLoadFileUtil.getUploadPath("/orion") + File.separator + FILE_UPLOAD_PATH + File.separator + siteId + File.separator + relicId;
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
        ConfigFactory.Configs config = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        boolean isUploadToCloud = Boolean.parseBoolean(config.get(Constants.Config.RESOURCE_IS_UPLOAD_TO_CLOUD));
        if (isUploadToCloud) {
            String pictureDirPath = destUploadFile.getAbsolutePath();
            String absolutePath = pictureDirPath.substring(pictureDirPath.lastIndexOf("orion"));
            QiniuUtil.upload(destUploadFile, absolutePath);
        }
        return Action.SUCCESS;
    }

    /**
     * 添加挂接文档信息到数据库
     *
     * @param fileName
     */
    private void addFileToDB(String fileName) {
        Attachment attachment = new Attachment();
        attachment.setRelic(relic);
        attachment.setPath(fileName);
        attachment.setAttachmentDate(DateTime.now().withTimeAtStartOfDay().toDate());
        try {
            attachmentService.addAttachment(attachment);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 图片上传Action
     *
     * @return
     */
    public String uploadPhotoAction() {
        try {
            relic = relicService.findRelicByRelicId(relicId, siteId);
            String path = UpLoadFileUtil.getUploadPath("/orion") + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId + File.separator + relicId;
            String photoName = getFileName(photoType);
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            String newPath = path + File.separator + photoName;
            File destImageFile = new File(newPath);
            String isUpload = UpLoadFileUtil.fileUpload(fileImage, destImageFile);
            if ("true".equals(isUpload)) {

                addPhotoToDB(photoType, photoName);
                ActionMessage.createByAction().success("照片上传成功");
                log("藏品信息", "图片名称：" + photoName);

            }
            ConfigFactory.Configs config = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
            boolean isUploadToCloud = Boolean.parseBoolean(config.get(Constants.Config.RESOURCE_IS_UPLOAD_TO_CLOUD));
            if (isUploadToCloud) {
                String pictureDirPath = destImageFile.getAbsolutePath();
                String absolutePath = pictureDirPath.substring(pictureDirPath.lastIndexOf("orion"));
                QiniuUtil.upload(destImageFile, absolutePath);
            }
        } catch (Exception e) {
            ActionMessage.createByAction().fail("照片上传失败");
            logFailed("图片上传失败", "图片上传失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public String saveInscriptionDescription() {

        relic = relicService.findRelicByRelicId(relicId, siteId);
        if (relic != null) {
            inscriptionService.deleteInscriptionByRelicId(relic.getId());
        }

        if (Strings.isNullOrEmpty(inscriptionDescription)) {
            return Action.SUCCESS;
        }

        Inscription i = new Inscription();
        i.setPath("inscriptionDescription");
        i.setInfo(inscriptionDescription);
        i.setRelic(relic);
        inscriptionService.addInscription(i);
        return Action.SUCCESS;
    }

    /**
     * 添加图片信息到数据库，根据type不同添加不同的图片
     * type: "photo"        照片
     * "rubbing"      拓片
     * "inscription"  铭文题跋
     *
     * @param type
     * @param photoName
     */
    private void addPhotoToDB(String type, String photoName) {
        try {
            if ("photo".equals(type)) {
                Photo p = new Photo();
                p.setPath(photoName);
                p.setPhotoDate(DateTime.now().withTimeAtStartOfDay().toDate());
                p.setRelic(relic);
                photoService.addPhoto(p);
            } else if ("rubbing".equals(type)) {
                Rubbing r = new Rubbing();
                r.setPath(photoName);
                r.setRubbingDate(DateTime.now().withTimeAtStartOfDay().toDate());
                r.setRelic(relic);
                rubbingService.addRubbing(r);
            } else if ("inscription".equals(type)) {
                Inscription i = new Inscription();
                i.setPath(photoName);
                i.setInfo(fileImagetext);
                i.setRelic(relic);
                inscriptionService.addInscription(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入的type返回上传图片的名称
     *
     * @return 图片上传时会将图片以.tmp格式的文件临时存放在C盘中，
     * 所以这里将所有的图片的格式规定为.jpg格式
     */
    private String getFileName(String type) {
        long currentStr = System.currentTimeMillis();
        String photoName = "";
        if (type.equals(Photo.PHOTO)) {
            photoName = "photo_" + currentStr + ".jpg";
        } else if (type.equals(Rubbing.RUBBING)) {
            photoName = "rubbing_" + currentStr + ".jpg";
        } else if (type.equals(Inscription.INSCRIPTION)) {
            photoName = "inscription_" + currentStr + ".jpg";
        }
        return photoName;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public File getFileImage() {
        return fileImage;
    }

    public void setFileImage(File fileImage) {
        this.fileImage = fileImage;
    }

    public File getSrcUploadFile() {
        return srcUploadFile;
    }

    public void setSrcUploadFile(File srcUploadFile) {
        this.srcUploadFile = srcUploadFile;
    }

    public String getFileImagetext() {
        return fileImagetext;
    }

    public void setFileImagetext(String fileImagetext) {
        this.fileImagetext = fileImagetext;
    }

    public String getHideFileName() {
        return hideFileName;
    }

    public void setHideFileName(String hideFileName) {
        this.hideFileName = hideFileName;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getInscriptionDescription() {
        return inscriptionDescription;
    }

    public void setInscriptionDescription(String inscriptionDescription) {
        this.inscriptionDescription = inscriptionDescription;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSrcUploadFileFileName() {
        return srcUploadFileFileName;
    }

    public void setSrcUploadFileFileName(String srcUploadFileFileName) {
        this.srcUploadFileFileName = srcUploadFileFileName;
    }
}
