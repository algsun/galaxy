package com.microwise.api.orion;

import com.microwise.api.bean.ApiResult;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 取消申请
 */
@Beans.Action
@Controller
public class CancelStockOutAction {

    public static final Logger log = LoggerFactory.getLogger(AdjustStockOutAction.class);

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    @Autowired
    private TaskService taskService;

    /**
     * 取消任务申请
     *
     * @return
     */
    @RequestMapping(value = "/orion/cancelStockOut/{eventId}/{taskId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "取消任务申请", position = 11, httpMethod = "GET",
            notes = "取消任务申请"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<String> execute(@PathVariable String eventId,
                                     @PathVariable String taskId,
                                     @PathVariable String userId) {
        boolean success = true;
        try {
            // 取消任务
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("resendRequest", false);
            taskService.complete(taskId, variables);
            // 删除 outEvent
            outEventService.deleteOutEvent(eventId);

            log.info("出入库管理", "取消出库申请，申请单号：" + eventId);
        } catch (Exception e) {
            success = false;
            log.error("取消申请失败", e);
            e.printStackTrace();
        }
        String message = success ? "取消出库申请成功" : "取消出库申请失败";
        return new ApiResult<String>(success, message, null, null);
    }
}
