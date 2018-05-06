package com.microwise.proxima.action.markRegion;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.bean.InfraredPictureDataBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.daemon.infraredImageResolution.ColorWheel;
import com.microwise.proxima.daemon.infraredImageResolution.ColorWheelHolder;
import com.microwise.proxima.service.InfraredMarkRegionDataService;
import com.microwise.proxima.service.InfraredMarkRegionService;
import com.microwise.proxima.service.InfraredPictureDataService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 更新标记区域
 *
 * @author gaohui
 * @date 2012-9-12
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 #8328
 * <p/>
 */
@Beans.Action
@Proxima
public class UpdateMarkRegionAction {

    private static final Logger log = LoggerFactory.getLogger(UpdateMarkRegionAction.class);
    @Autowired
    private InfraredMarkRegionService markRegionService;
    @Autowired
    private InfraredMarkRegionDataService infraredMarkRegionDataService;

    @Autowired
    private InfraredPictureDataService infraredPictureDataService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ColorWheelHolder colorWheelHolder;


    // input
    /**
     * 标记区域 json
     * <p/>
     * <pre>
     * 格式为:
     * [
     *   {
     *      id: markRegionId,
     *      x: positionX,
     *      y: positionY,
     *      width: regionWidth,
     *      height: regionHeight
     *   },
     *   ...
     * ]
     * </pre>
     */
    private String markRegionsJson;
    /**
     * 摄像机点位
     */
    private String dvPlaceId;

    // output
    /**
     * 成功与否
     */
    private boolean success;

    public String execute() throws JSONException {
        try {

            markRegionService.updateMarkRegions(markRegionsJson);
            JSONArray markRegions = new JSONArray(markRegionsJson);

            ColorWheel colorWheel = colorWheelHolder.getColorWheel();
            for (int i = 0; i < markRegions.length(); i++) {
                InfraredMarkRegionBean markRegionBean = new InfraredMarkRegionBean();
                JSONObject markRegion = markRegions.getJSONObject(i);
                markRegionBean.setId(markRegion.getString("id"));
                markRegionBean.setPositionX(markRegion.getInt("x"));
                markRegionBean.setPositionX(markRegion.getInt("y"));
                markRegionBean.setRegionWidth(markRegion.getInt("width"));
                markRegionBean.setRegionHeight(markRegion.getInt("height"));

                //根据摄像机id查找出所有红外图片，分析所有图片标记区域
                List<PictureBean> pictureBeanList = pictureService.findPictures(dvPlaceId);
                for (PictureBean pictureBean : pictureBeanList) {
                    InfraredPictureDataBean infraredPictureData = infraredPictureDataService.findByPicId(pictureBean.getId());
                    PictureBean picture = pictureService.findById(pictureBean.getId());
                    File file = new File(UpLoadFileUtil.getUploadPath("proxima" + File.separator + "images") + File.separator + picture.getPath() + File.separator + picture.getName());
                    InputStream inputStream = new FileInputStream(file);
                    inputStream.close();
                    infraredMarkRegionDataService.infraredSingleRegionAnalyzer(markRegionBean, infraredPictureData, file, colorWheel, picture);
                }
            }
            success = true;
        } catch (Exception ex) {
            success = false;
            log.error("", ex);
        }
        return Action.SUCCESS;
    }

    public String getMarkRegionsJson() {
        return markRegionsJson;
    }

    public void setMarkRegionsJson(String markRegionsJson) {
        this.markRegionsJson = markRegionsJson;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
