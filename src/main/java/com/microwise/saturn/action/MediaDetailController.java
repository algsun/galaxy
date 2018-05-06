package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.PagingUtil;
import com.microwise.saturn.bean.MediaDetailPO;
import com.microwise.saturn.bean.MediaShowPO;
import com.microwise.saturn.bean.TextureVO;
import com.microwise.saturn.service.MediaDetailService;
import com.microwise.saturn.service.MediaShowService;
import com.microwise.saturn.service.TextureService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.List;

/**
 * 展示内容Controller
 *
 * @author 王耕
 * @date 2015-3-17
 */
@Controller
@Saturn
@RequestMapping(value = "/manager")
public class MediaDetailController {

    private static final String _pagePath = "mediadetail/mediadetail-list.ftl";

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

    @RequestMapping(value = "/mediaDetails")
    public String view(Model model) {
        String siteId = Sessions.createByAction().currentSiteId();
        int count = mediaDetailService.findMediaDetailCount(null);
        List<MediaShowPO> mediaShows = mediaShowService.findAll();
        List<TextureVO> textures = textureService.findAll();

        String path = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "saturn" + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId;

        //分页
        //总分页数
        int pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPageIndex", 1);
        List<MediaDetailPO> mediaDetails = mediaDetailService.findAllByPage(1, Constants.SIZE_PER_PAGE);

        model.addAttribute("_pagePath", _pagePath);
        model.addAttribute("mediaDetails", mediaDetails);
        model.addAttribute("mediaShows", mediaShows);
        model.addAttribute("textures", textures);
        model.addAttribute("enable", 0);
        model.addAttribute("material", 0);
        model.addAttribute("detailTitle", "");
        model.addAttribute("detailContent", "");
        model.addAttribute("imagePath", path);
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/findMediaDetailByConditions", method = RequestMethod.GET)
    public String findMediaDetailByConditions(
            Model model,
            @RequestParam int enable,
            @RequestParam String material,
            @RequestParam String detailTitle,
            @RequestParam String detailContent,
            @RequestParam int currentPageIndex
    ) {
        String siteId = Sessions.createByAction().currentSiteId();

        String path = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "saturn" + File.separator + IMAGE_UPLOAD_PATH + File.separator + siteId;
        MediaDetailPO mediaDetail = new MediaDetailPO();
        mediaDetail.setDetailTitle(detailTitle);
        mediaDetail.setDetailContent(detailContent);
        mediaDetail.setMaterial(material);
        mediaDetail.setEnable(enable);
        List<MediaDetailPO> mediaDetails = mediaDetailService.findByConditions(mediaDetail, currentPageIndex, Constants.SIZE_PER_PAGE);
        List<TextureVO> textures = textureService.findAll();

        //分页
        int count = mediaDetailService.findMediaDetailCount(mediaDetail);
        //总分页数
        int pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);

        model.addAttribute("_pagePath", _pagePath);
        model.addAttribute("mediaDetails", mediaDetails);
        model.addAttribute("textures", textures);
        model.addAttribute("enable", enable);
        model.addAttribute("material", material);
        model.addAttribute("detailTitle", detailTitle);
        model.addAttribute("detailContent", detailContent);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPageIndex", currentPageIndex);
        model.addAttribute("imagePath", path);
        return "saturn/pages/manager/layout";
    }
}
