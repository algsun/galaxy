package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.saturn.bean.MediaDetailPO;
import com.microwise.saturn.bean.TextureVO;
import com.microwise.saturn.service.MediaDetailService;
import com.microwise.saturn.service.TextureService;
import com.microwise.saturn.sys.Saturn;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;

/**
 *
 */
@Controller
@Saturn
public class ResultController {

    @Autowired
    private MediaDetailService mediaDetailService;

    @Autowired
    private TextureService textureService;


    private static final String IMAGE_UPLOAD_PATH = "images";

    @RequestMapping(value = "/result")
    public String view(@RequestParam(required = false) String desc, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer quarterNum, @RequestParam(required = false) Integer textureId, Model model) {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            String path = Sessions.createByAction().getGalaxyResourceURL();
            path = path + File.separator + "saturn" + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId + File.separator;
            List<MediaDetailPO> mediaDetailPOs = mediaDetailService.findByConditions(desc, year, quarterNum, textureId);
            if (year == null) {
                year = new DateTime().getYear();
            }
            List<TextureVO> textureVOs = textureService.findAll();
            for (MediaDetailPO mediaDetailPO : mediaDetailPOs) {
                mediaDetailPO.setDetailImage(path + mediaDetailPO.getDetailImage());
                mediaDetailPO.setDetailSubImage(path + mediaDetailPO.getDetailSubImage());
            }
            model.addAttribute("_pagePath", "result-index.ftl");
            model.addAttribute("mediaDetailPOs", mediaDetailPOs);
            model.addAttribute("textureVOs", textureVOs);
            model.addAttribute("year", year);
            model.addAttribute("desc", desc);
            model.addAttribute("quarterNum", quarterNum);
            model.addAttribute("textureId", textureId);
        } catch (Exception e) {
        }
        return "saturn/pages/layout";
    }

    @RequestMapping(value = "findMediaDetail")
    @ResponseBody
    public MediaDetailPO findMediaDetail(@RequestParam Integer id) {
        String siteId = Sessions.createByAction().currentSiteId();
        String path = Sessions.createByAction().getGalaxyResourceURL();
        path = path + File.separator + "saturn" + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId + File.separator;
        MediaDetailPO mediaDetailPO = mediaDetailService.findMediaDetailById(id);
        mediaDetailPO.setDetailImage(path + mediaDetailPO.getDetailImage());
        mediaDetailPO.setDetailSubImage(path + mediaDetailPO.getDetailSubImage());
        return mediaDetailPO;
    }

    @RequestMapping(value = "findMediaDetailPOs")
    @ResponseBody
    public List<MediaDetailPO> findAll() {
        List<MediaDetailPO> mediaDetailPOs = mediaDetailService.findAll();
        return mediaDetailPOs;
    }
}
