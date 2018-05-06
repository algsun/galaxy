package com.microwise.saturn.action;

import com.microwise.blackhole.bean.User;
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

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 保存展示内容Controller
 *
 * @author 王耕
 * @date 2015-3-19
 */
@Controller
@Saturn
@RequestMapping(value = "/manager")
public class SaveMediaDetailController {

    private static final String _pagePath = "mediadetail/mediadetail-save.ftl";

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

    @RequestMapping(value = "/toSaveMediaDetail")
    public String toSaveMediaDetail(Model model) {

        List<MediaShowPO> mediaShows = mediaShowService.findAll();
        List<TextureVO> textures = textureService.findAll();

        model.addAttribute("_pagePath", _pagePath);
        model.addAttribute("mediaShows", mediaShows);
        model.addAttribute("textures", textures);
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/saveMediaDetail", method = RequestMethod.POST)
    public String uploadDocument(@ModelAttribute MediaDetailPO mediaDetailPO) {
        String siteId = Sessions.createByAction().currentSiteId();
        User user = Sessions.createByAction().currentUser();
        long currentMills = System.currentTimeMillis();
        try {
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
            FileUtils.copyInputStreamToFile(mediaDetailPO.getDetailImageFile().getInputStream(), destImageFileMain);
            FileUtils.copyInputStreamToFile(mediaDetailPO.getDetailSubImageFile().getInputStream(), destImageFileSub);
            mediaDetailPO.setDetailImage(photoNameMain);
            mediaDetailPO.setDetailSubImage(photoNameSub);
            mediaDetailPO.setCreateOn(new Date());
            mediaDetailPO.setCreateBy(user.getUserName());
            mediaDetailPO.setLine(0);
            mediaDetailService.save(mediaDetailPO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:mediaDetails";
    }
}
