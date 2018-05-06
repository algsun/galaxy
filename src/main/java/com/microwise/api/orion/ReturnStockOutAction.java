package com.microwise.api.orion;

import com.microwise.api.bean.ApiResult;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.service.OutEventService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 回库, 根据出库申请单将文物入库
 *
 * @author gaohui
 * @date 13-6-3 14:00
 * @check xiedeng 2013-6-6 15:41 svn:3990
 */
@Beans.Action
@Controller
public class ReturnStockOutAction {

    public static final Logger log = LoggerFactory.getLogger(ReturnStockOutAction.class);
    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    private OutEvent outEvent;

    @RequestMapping(value = "/orion/returnStockOutView/{eventId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "回库", position = 9, httpMethod = "GET",
            notes = "回库"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    public ApiResult<OutEvent> view(@PathVariable String eventId) {
        ApiResult<OutEvent> result = new ApiResult<OutEvent>();
        OutEvent outEvent = outEventService.findById(eventId);
        result.setData(outEvent);
        result.setSuccess(true);
        return result;
    }

    /**
     * 回库, 根据出库申请单将文物入库
     *
     * @return
     */
    @RequestMapping(value = "/orion/returnStockOut/{eventId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "回库", position = 10, httpMethod = "GET",
            notes = "回库"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<String> execute(@PathVariable String eventId,
                                     @PathVariable String userId) {
        boolean success = true;
        ApiResult<String> result = new ApiResult<String>();
        try {
            outEventService.backToStoreRoom(eventId);
            log.info("出入库管理", "文物回库，申请单号：" + eventId);
        } catch (Exception e) {
            success = false;
            log.error("出库单回库失败", e);
        }
        String message = success ? "回库成功" : "回库失败";
        result.setSuccess(success);
        result.setMessage(message);
        return result;
    }
}
