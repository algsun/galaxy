package com.microwise.api;

import com.microwise.api.bean.ApiResult;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.service.PictureService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 本体监测系统 API for Encke
 *
 * @author li.jianfei
 * @date 2014-02-27
 */
@Controller
public class ProximaAction {

    public static final Logger log = LoggerFactory.getLogger(ProximaAction.class);

    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;


    @Autowired
    private PictureService pictureService;

    private String pictureBasePath;

    @RequestMapping(value = "/sites/{siteId}/opticsDVPlaces", method = RequestMethod.GET)
    @ApiOperation(value = "查询光学摄像机列表", position = 2, httpMethod = "GET",
            notes = "查询光学摄像机列表"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<OpticsDVPlaceBean>> getOpticsDVPlaces(@PathVariable String siteId) {
        ApiResult<List<OpticsDVPlaceBean>> result = new ApiResult<List<OpticsDVPlaceBean>>();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            pictureBasePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort() + "/galaxy-resources" + "/proxima/images";
            List<OpticsDVPlaceBean> opticsDVPlaceList = opticsDVPlaceService.findAll(siteId);

            for (OpticsDVPlaceBean opticsDVPlace : opticsDVPlaceList) {
                // 查询最近一张照片
                List<PictureBean> pictures = pictureService.findRecentPictures(opticsDVPlace.getId(), 1);
                if (pictures != null && pictures.size() > 0) {
                    PictureBean picture = pictures.get(0);
                    opticsDVPlace.setLastPictureUrl(pictureBasePath + "/" + picture.getPath() + "/" + picture.getName());
                }
            }
            result.setSuccess(true);
            result.setMessage("查询光学摄像机列表成功");
            result.setData(opticsDVPlaceList);
        } catch (Exception e) {
            log.error("查询光学摄像机列表", e);
            result.setSuccess(false);
            result.setMessage("查询光学摄像机列表失败");
        }
        return result;
    }

    @RequestMapping(value = "/dv-places/{dvPlaceId}/pictures", method = RequestMethod.GET)
    @ApiOperation(value = "查询光学摄像机照片", position = 2, httpMethod = "GET",
            notes = "查询光学摄像机照片"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<PictureBean>> getPictures(@PathVariable String dvPlaceId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date lastPictureDate,
                                                    @RequestParam int pictureCount) {
        ApiResult<List<PictureBean>> result = new ApiResult<List<PictureBean>>();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            pictureBasePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort() + "/galaxy-resources" + "/proxima/images";
            List<PictureBean> pictureList = pictureService.findPicturesAfter(dvPlaceId, lastPictureDate, pictureCount);
            result.setSuccess(true);
            result.setMessage(pictureBasePath);
            result.setData(pictureList);
        } catch (Exception e) {
            log.error("查询光学摄像机照片", e);
            result.setSuccess(false);
            result.setMessage("查询光学摄像机照片失败");
        }
        return result;
    }

}

