package com.microwise.orion.action.relic;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.Inscription;
import com.microwise.orion.bean.Photo;
import com.microwise.orion.bean.Rubbing;
import com.microwise.orion.service.AttachmentService;
import com.microwise.orion.service.InscriptionService;
import com.microwise.orion.service.PhotoService;
import com.microwise.orion.service.RubbingService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 删除挂接文档
 *
 * @author : wanggeng
 * @date: 13-6-5 下午4:05
 */
@Beans.Action
@Orion
public class DeleteRelicFileAction extends OrionLoggerAction {
    /**
     * 藏品信息挂接文档上传子目录
     */
    private static final String FILE_UPLOAD_PATH = "file";

    /**
     * 文档编辑，图片上传子目录
     */
    private static final String IMAGE_UPLOAD_PATH = "image";

    /**
     * 文物挂接文档 service
     */
    @Autowired
    private AttachmentService attachmentService;

    /**
     * 图片 service
     */
    @Autowired
    private PhotoService photoService;
    /**
     * 拓片 service
     */
    @Autowired
    private RubbingService rubbingService;

    /**
     * 铭文题跋 service
     */
    @Autowired
    private InscriptionService inscriptionService;

    //input
    /**
     * 根据ID删除挂接文档
     */
    private int attachmentId;

    // Input
    /**
     * 照片ID
     */
    private int photoId;


    /**
     * 删除类型
     * inscription 铭文题跋
     * rubbing     拓片
     * photo       照片
     */
    private String type;

    //output
    /**
     * 文物ID
     */
    private int relicId;

    /**
     * 页面跳转参数
     */
    private String pageNum;


    private int index;

    /**
     * 挂接文档删除
     *
     * @return
     */
    public String execute() {
        try {
            if (attachmentId == 0) {
                return Action.SUCCESS;
            }
            String filePath = attachmentService.findById(attachmentId).getPath();
            attachmentService.deleteAttachment(attachmentId);

            String subdirectory = FILE_UPLOAD_PATH + File.separator + Sessions.createByAction().currentSiteId() + File.separator + relicId;
            UpLoadFileUtil.deleteFileByFileName(filePath, subdirectory, "/orion");
            ActionMessage.createByAction().success("文件删除成功");
            log("成功删除挂接文档", "文物ID：" + relicId);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("文件删除失败");
            logFailed("删除失败", "文件删除失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 删除照片
     *
     * @return
     */
    public String deletePhotoById() {
        try {
            if (photoId == 0) {
                return Action.SUCCESS;
            }
            String subdirectory = IMAGE_UPLOAD_PATH + File.separator + Sessions.createByAction().currentSiteId() + File.separator + relicId;
            String fileName = "";

            if (type.equals(Inscription.INSCRIPTION)) {
                Inscription inscription = inscriptionService.findById(photoId);
                fileName = inscription.getPath();
                inscriptionService.deleteInscription(inscription.getId());
            } else if (type.equals(Rubbing.RUBBING)) {
                Rubbing rubbing = rubbingService.findById(photoId);
                fileName = rubbing.getPath();
                rubbingService.deleteRubbing(rubbing.getId());
            } else if (type.equals(Photo.PHOTO)) {
                Photo p = photoService.findById(photoId);
                fileName = p.getPath();
                photoService.delete(p);
            }
            UpLoadFileUtil.deleteFileByFileName(fileName, subdirectory, "/orion");

            ActionMessage.createByAction().success("照片删除成功");
            log("成功删除照片", "照片名：" + fileName);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("照片删除失败");
            logFailed("删除失败", "照片删除失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
