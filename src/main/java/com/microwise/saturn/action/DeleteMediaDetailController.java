package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.saturn.bean.MediaDetailPO;
import com.microwise.saturn.service.MediaDetailService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

/**
 * 展示内容删除Controller
 *
 * @author 王耕
 * @date 2015-3-19
 */
@Controller
@Saturn
@RequestMapping(value = "/manager")
public class DeleteMediaDetailController {

    /**
     * 图片上传子目录
     */
    private static final String IMAGE_UPLOAD_PATH = "images";

    @Autowired
    private MediaDetailService mediaDetailService;

    @RequestMapping(value = "/deleteMediaDetail", method = RequestMethod.GET)
    public String deleteMediaDetail(@RequestParam int mediaDetailId) {
        try {
            MediaDetailPO mediaDetail = mediaDetailService.findMediaDetailById(mediaDetailId);
            mediaDetailService.delete(mediaDetailId);
            String siteId = Sessions.createByAction().currentSiteId();
            String path = UpLoadFileUtil.getUploadPath("/saturn") + "/" + IMAGE_UPLOAD_PATH + "/" + siteId;
            String detailImageName = mediaDetail.getDetailImage();
            String detailSubImageName = mediaDetail.getDetailSubImage();
            File imageFileMain = new File(path + "/" + detailImageName);
            File imageFileSub = new File(path + "/" + detailSubImageName);
            if (imageFileMain.exists() && imageFileMain.isFile()) {
                imageFileMain.delete();
            }
            if (imageFileSub.exists() && imageFileSub.isFile()) {
                imageFileSub.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:mediaDetails";
    }
}
