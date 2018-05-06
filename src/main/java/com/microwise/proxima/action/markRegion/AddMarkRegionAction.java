package com.microwise.proxima.action.markRegion;

import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.bean.InfraredPictureDataBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.daemon.infraredImageResolution.ColorWheel;
import com.microwise.proxima.daemon.infraredImageResolution.ColorWheelHolder;
import com.microwise.proxima.daemon.infraredImageResolution.InfraredAnalyzer;
import com.microwise.proxima.service.InfraredMarkRegionDataService;
import com.microwise.proxima.service.InfraredMarkRegionService;
import com.microwise.proxima.service.InfraredPictureDataService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 添加区域区域
 *
 * @author gaohui
 * @date 2012-9-10
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 #8388
 * <p/>
 */
@Beans.Action
@Proxima
public class AddMarkRegionAction {
    private static final Logger log = LoggerFactory.getLogger(AddMarkRegionAction.class);

    @Autowired
    private InfraredMarkRegionService markRegionService;
    @Autowired
    private InfraredAnalyzer infraredAnalyzer;

    @Autowired
    private InfraredMarkRegionDataService infraredMarkRegionDataService;

    @Autowired
    private InfraredPictureDataService infraredPictureDataService;

    @Autowired
    private PictureService pictureService;


    // input
    /**
     * 摄像机点位ID
     */
    private String dvPlaceId;
    /**
     * 标记区域名称
     */
    private String markName;
    /**
     * 左上角 x 位置
     */
    private int positionX;
    /**
     * 左上角 y 位置
     */
    private int positionY;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 图片ID
     */
    private String pictureId;

    // output
    /**
     * 添加的标记区域的ID
     */
    private String markRegionId;
    /**
     * 成功与否
     */
    private boolean success;
    /**
     * 如果失败，返回的失败消息
     */
    private String message;
    /**
     * 从数据库中加载 ColorWheel接口
     */
    @Autowired
    private ColorWheelHolder colorWheelHolder;


    public String execute() throws IOException {
        try {
            // 构造 markRegion, 填充数据
            InfraredMarkRegionBean markRegion = new InfraredMarkRegionBean();
            markRegion.setId(GalaxyIdUtil.get64UUID());
            markRegion.setName(markName);
            markRegion.setPositionX(positionX);
            markRegion.setPositionY(positionY);
            markRegion.setRegionWidth(width);
            markRegion.setRegionHeight(height);

            InfraredDVPlaceBean dvPlace = new InfraredDVPlaceBean();
            dvPlace.setId(dvPlaceId);

            markRegion.setDvPlace(dvPlace);

            ColorWheel colorWheel = colorWheelHolder.getColorWheel();
            markRegionId = markRegionService.save(markRegion);

            //根据摄像机id查找出所有红外图片，分析所有图片标记区域
            List<PictureBean> pictureBeanList = pictureService.findPictures(dvPlaceId);
            for (PictureBean pictureBean : pictureBeanList) {
                InfraredPictureDataBean infraredPictureData = infraredPictureDataService.findByPicId(pictureBean.getId());
                PictureBean picture = pictureService.findById(pictureBean.getId());
                File file = new File(UpLoadFileUtil.getUploadPath("proxima" + File.separator + "images") + File.separator + picture.getPath() + File.separator + picture.getName());
                InputStream inputStream = new FileInputStream(file);
                inputStream.close();
                infraredMarkRegionDataService.infraredSingleRegionAnalyzer(markRegion, infraredPictureData, file, colorWheel, picture);
            }
            MessageActionUtil.success("添加成功");
        } catch (Exception ex) {
            MessageActionUtil.fail("添加失敗");
            log.error("添加标记区域", ex);
        }
        return Action.SUCCESS;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMarkRegionId() {
        return markRegionId;
    }

    public void setMarkRegionId(String markRegionId) {
        this.markRegionId = markRegionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public InfraredMarkRegionService getMarkRegionService() {
        return markRegionService;
    }

    public void setMarkRegionService(InfraredMarkRegionService markRegionService) {
        this.markRegionService = markRegionService;
    }

    public InfraredAnalyzer getInfraredAnalyzer() {
        return infraredAnalyzer;
    }

    public void setInfraredAnalyzer(InfraredAnalyzer infraredAnalyzer) {
        this.infraredAnalyzer = infraredAnalyzer;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }
}
