package com.microwise.api.orion;

import com.microwise.api.bean.ApiResult;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.orion.bean.EventRelic;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.OutEventAttachment;
import com.microwise.orion.bean.Rove;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.service.RoveService;
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
import java.util.List;
import java.util.Map;

/**
 * 库房确认通过
 */
@Beans.Action
@Controller
public class ConfirmStockOutAction {

    public static final Logger log = LoggerFactory.getLogger(ConfirmStockOutAction.class);

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;
    @Autowired
    private TaskService taskService;
    /**
     * 流传经历service
     */
    @Autowired
    private RoveService roveService;

    private boolean couldCheck = true;

    @RequestMapping(value = "/orion/confirmStockOut/{eventId}/{taskId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取库房确认列表数据", position = 6, httpMethod = "GET",
            notes = "获取库房确认列表数据"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<Map<String, Object>> view(@PathVariable String eventId,
                                               @PathVariable String taskId,
                                               @PathVariable String userId) {
        ApiResult<Map<String, Object>> result = new ApiResult<Map<String, Object>>();
        OutEvent outEvent = outEventService.findById(eventId);
        List<OutEventAttachment> outEventAttachmentList = outEventService.findOutEventAttachmentByEventId(eventId);
        for (EventRelic evenRelic : outEvent.getEventRelics()) {
            if (evenRelic.getState() != EventRelic.STATE_EXIST) {
                couldCheck = false;
            }
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("couldCheck", couldCheck);
        data.put("outEvent", outEvent);
        data.put("outEventAttachmentList", outEventAttachmentList);
        result.setData(data);
        return result;
    }

    /**
     * 库房确认通过
     *
     * @return
     */
    @RequestMapping(value = "/orion/doConfirmStockOut/{eventId}/{taskId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "库房确认", position = 7, httpMethod = "GET",
            notes = "库房确认"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    public ApiResult<String> execute(@PathVariable String eventId, @PathVariable String taskId,
                                     @PathVariable String userId) {
        ApiResult<String> result = new ApiResult<String>();
        boolean success = true;
        try {
            outEventService.doStockOut(eventId);

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("stockOutConfirmApproved", true);
            taskService.complete(taskId, variables);

            //出库信息作为最新流传经历信息
            OutEvent outEvent = outEventService.findById(eventId);
            for (EventRelic eventRelic : outEvent.getEventRelics()) {
                String roveInfo = "出库时间：" + DateTimeUtil.format("yyyy-MM-dd HH:mm", outEvent.getBeginDate())
                        + "，提用目的：" + outEvent.getUseFor();
                Rove rove = new Rove();
                rove.setRoveInfo(roveInfo);
                rove.setRoveDate(outEvent.getBeginDate());
                rove.setRelic(eventRelic.getRelic());
                roveService.addRelicRove(rove);
            }

            log.info("出入库管理", "库房确认通过，申请单号：" + eventId);
        } catch (Exception e) {
            log.error("出库确认失败", e);
            success = false;
        }
        String message = success ? "确认成功" : "确认失败";
        result.setSuccess(success);
        result.setMessage(message);
        return result;
    }
}
