package com.microwise.saturn.action;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.PagingUtil;
import com.microwise.saturn.bean.MediaShowPO;
import com.microwise.saturn.service.MediaShowService;
import com.microwise.saturn.sys.Saturn;
import com.microwise.saturn.sys.SaturnLoggerAction;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 展示类别action
 *
 * @author 王耕
 * @date 2015-3-17
 */
@Controller
@Saturn
@RequestMapping(value = "/manager")
public class MediaShowController extends SaturnLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(MediaShowController.class);

    private static final String _pagePath = "mediashow/mediashow-list.ftl";

    @Autowired
    private MediaShowService mediaShowService;

    @RequestMapping(value = "/mediaShows")
    public String view(Model model) {
        try {
            int count = mediaShowService.findMediaShowCount(null);
            //总分页数
            int pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
            List<MediaShowPO> mediaShows = mediaShowService.findAllByPage(1, Constants.SIZE_PER_PAGE);
            model.addAttribute("_pagePath", _pagePath);
            model.addAttribute("mediaShows", mediaShows);
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("currentPageIndex", 1);
            log("考古研究院", "类别展示列表");
        } catch (Exception e) {
            logger.error("考古研究院,类别展示列表", e);
        }

        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/findMediaShowByConditions", method = RequestMethod.GET)
    public String findMediaShowByConditions(Model model,
                                            @RequestParam String mediaShowTitle,
                                            @RequestParam String remark,
                                            @RequestParam int currentPageIndex
    ) {
        try {
            MediaShowPO mediaShow = new MediaShowPO();
            mediaShow.setTitle(mediaShowTitle);
            mediaShow.setRemark(remark);
            List<MediaShowPO> mediaShows = mediaShowService.findByCondition(mediaShow, currentPageIndex, Constants.SIZE_PER_PAGE);
            model.addAttribute("mediaShows", mediaShows);
            model.addAttribute("_pagePath", _pagePath);
            model.addAttribute("mediaShowTitle", mediaShowTitle);
            model.addAttribute("remark", remark);

            //分页
            int count = mediaShowService.findMediaShowCount(mediaShow);
            //总分页数
            int pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("currentPageIndex", currentPageIndex);
            log("考古研究院", "条件查询展示类别");
        } catch (Exception e) {
            logger.error("考古研究院,条件查询展示类别", e);
        }
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/deleteMediaShow", method = RequestMethod.GET)
    public String deleteMediaShow(@RequestParam int mediaShowId) {
        try {
            mediaShowService.delete(mediaShowId);
            log("考古研究院", "删除展示类别");
        } catch (Exception e) {
            logger.error("考古研究院,删除展示类别", e);
        }
        return "redirect:mediaShows";
    }

    @RequestMapping(value = "/editMediaShow", method = RequestMethod.POST)
    public String editMediaShow(
            @RequestParam int mediaShowId,
            @RequestParam String title,
            @RequestParam String remark
    ) {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            MediaShowPO mediaShow = new MediaShowPO();
            mediaShow.setId(mediaShowId);
            mediaShow.setTitle(title);
            mediaShow.setRemark(remark);
            mediaShow.setSiteCode(siteId);
            mediaShowService.update(mediaShow);
            log("考古研究院", "编辑展示类别");
        } catch (Exception e) {
            logger.error("考古研究院,编辑展示类别", e);
        }
        return "redirect:mediaShows";
    }

    @RequestMapping(value = "/saveMediaShow", method = RequestMethod.POST)
    public String saveMediaShow(
            @RequestParam String title,
            @RequestParam String remark
    ) {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            User user = Sessions.createByAction().currentUser();
            MediaShowPO mediaShow = new MediaShowPO();
            mediaShow.setTitle(title);
            mediaShow.setRemark(remark);
            mediaShow.setSiteCode(siteId);
            mediaShow.setCreateOn(new Date());
            mediaShow.setCreateBy(user.getUserName());
            mediaShowService.save(mediaShow);
            log("考古研究院", "新增展示类别");
        } catch (Exception e) {
            logger.error("考古研究院,新增展示类别", e);
        }
        return "redirect:mediaShows";
    }
}
