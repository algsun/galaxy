package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.saturn.bean.MediaDetailPO;
import com.microwise.saturn.bean.MediaShowPO;
import com.microwise.saturn.bean.TextureVO;
import com.microwise.saturn.service.MediaDetailService;
import com.microwise.saturn.service.MediaShowService;
import com.microwise.saturn.service.TextureService;
import com.microwise.saturn.sys.Saturn;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 更新展示内容Controller
 *
 * @author 王耕
 * @date 2015-3-19
 */
@Controller
@Saturn
@RequestMapping(value = "/manager")
public class UpdateMediaDetailController {

    private static final String _pagePath = "mediadetail/mediadetail-update.ftl";

    /**
     * 图片上传子目录
     */
    private static final String IMAGE_UPLOAD_PATH = "images";

    @Autowired
    private MediaDetailService mediaDetailService;

    @Autowired
    private MediaShowService mediaShowService;

    @Autowired
    private TextureService textureService;

    @RequestMapping(value = "toEditMediaDetail", method = RequestMethod.GET)
    public String toEditMediaDetail(Model model, @RequestParam int mediaDetailId) {
        String siteId = Sessions.createByAction().currentSiteId();
        List<MediaShowPO> mediaShows = mediaShowService.findAll();
        List<TextureVO> textures = textureService.findAll();
        String path = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "saturn" + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId;
        MediaDetailPO mediaDetail = mediaDetailService.findMediaDetailById(mediaDetailId);

        model.addAttribute("_pagePath", _pagePath);
        model.addAttribute("mediaShows", mediaShows);
        model.addAttribute("textures", textures);
        model.addAttribute("mediaDetail", mediaDetail);
        model.addAttribute("imagePath", path);
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/editMediaDetail", method = RequestMethod.POST)
    public String editMediaDetail(@ModelAttribute MediaDetailPO mediaDetailPO) {
        try {
            //删掉服务器上的图片，重新上传
            deleteImages(mediaDetailPO);
            uploadImages(mediaDetailPO);
            mediaDetailPO.setPubDate(new Date());
            mediaDetailPO.setLine(0);
            mediaDetailService.update(mediaDetailPO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:mediaDetails";
    }

    /**
     * 删除图片
     *
     * @param mediaDetailPO
     */
    private void deleteImages(MediaDetailPO mediaDetailPO) {
        String siteId = Sessions.createByAction().currentSiteId();
        String path = UpLoadFileUtil.getUploadPath("/saturn") + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId;
        String detailImageName = mediaDetailPO.getDetailImage();
        String detailSubImageName = mediaDetailPO.getDetailSubImage();
        File imageFileMain = new File(path + File.separator + detailImageName);
        File imageFileSub = new File(path + File.separator + detailSubImageName);
        long mainSize = mediaDetailPO.getDetailImageFile().getSize();
        long subSize = mediaDetailPO.getDetailSubImageFile().getSize();

        if (mainSize != 0 && imageFileMain.exists() && imageFileMain.isFile()) {
            imageFileMain.delete();
        }

        if (subSize != 0 && imageFileSub.exists() && imageFileSub.isFile()) {
            imageFileSub.delete();
        }
    }

    /**
     * 上传图片
     *
     * @param mediaDetailPO
     */
    private void uploadImages(MediaDetailPO mediaDetailPO) {
        try {
            long currentMills = System.currentTimeMillis();
            String siteId = Sessions.createByAction().currentSiteId();
//            String path = Constants.GALAXY_RESOURCES_BASE_URL + File.separator + "saturn" + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId;
            String path = UpLoadFileUtil.getUploadPath("/saturn") + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId;
            String photoNameMain = currentMills + "_main.jpg";
            String photoNameSub = currentMills + "_sub.jpg";
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            String newPathMain = path + File.separator + photoNameMain;
            String newPathSub = path + File.separator + photoNameSub;
            File destImageFileMain = new File(newPathMain);
            File destImageFileSub = new File(newPathSub);
            long mainSize = mediaDetailPO.getDetailImageFile().getSize();
            long subSize = mediaDetailPO.getDetailSubImageFile().getSize();
            if (mainSize != 0) {
                FileUtils.copyInputStreamToFile(mediaDetailPO.getDetailImageFile().getInputStream(), destImageFileMain);
                mediaDetailPO.setDetailImage(photoNameMain);
            }
            if (subSize != 0) {
                FileUtils.copyInputStreamToFile(mediaDetailPO.getDetailSubImageFile().getInputStream(), destImageFileSub);
                mediaDetailPO.setDetailSubImage(photoNameSub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
