package com.microwise.api.orion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.microwise.api.bean.ApiResult;
import com.microwise.api.bean.OutEventVo;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.EventZone;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.EventZoneService;
import com.microwise.orion.service.OutEventService;
import com.opensymphony.xwork2.Action;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.activiti.engine.TaskService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiedeng on 2017/7/18.
 */
@Beans.Action
@Controller
public class StockOutCheckAction {

    public static final Logger log = LoggerFactory.getLogger(StockOutCheckAction.class);

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    /**
     * 出库单文物分单确认service
     */
    @Autowired
    private EventZoneService eventZoneService;

    @Autowired
    private TaskService taskService;

    // 库房区域对应此区域内的文件是否可以审核通过, 如果区域内所在的文物有一件是非“在库”状态，则不能审核通过 @gaohui
    private Map<Integer, Boolean> zoneCouldChecks = new HashMap<Integer, Boolean>();


    @RequestMapping(value = "/orion/stockOutCheck/{eventId}/{taskId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "出入库单核对页面数据", position = 2, httpMethod = "GET",
            notes = "出入库单核对页面数据"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<EventZone>> stockOutCheck(@PathVariable String eventId,
                                                    @PathVariable String taskId,
                                                    @PathVariable String userId) {
        ApiResult<List<EventZone>> result = new ApiResult<List<EventZone>>();
        OutEvent outEvent = outEventService.findById(eventId);
        OutEventVo outEventVo = OutEventVo.castEvent2Vo(outEvent);
        List<EventZone> zones = eventZoneService.findEventZones(eventId);
        for (EventZone eventZone : zones) {
            boolean couldCheck = true;
            for (Relic relic : eventZone.getRelics()) {
                // 如果区域内所在的文物有一件是非“在库”状态，则不能审核通过 @gaohui
                if (relic.getState() != Relic.STATE_IN) {
                    couldCheck = false;
                    break;
                }
            }
            zoneCouldChecks.put(eventZone.getId(), couldCheck);
        }
        result.setData(zones);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate4Module());
        try {
            String jsonString = objectMapper.writeValueAsString(result);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF8");
            response.getWriter().print(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 修改文物出库审批状态
     *
     * @return
     */
    @RequestMapping(value = "/orion/changeStatus/{eventId}/{taskId}/{userId}/{state}", method = RequestMethod.GET)
    @ApiOperation(value = "出入库单核对", position = 3, httpMethod = "GET",
            notes = "出入库单核对"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<String> changeStatus(@PathVariable String eventId,
                                          @PathVariable String taskId,
                                          @PathVariable int userId,
                                          @PathVariable int state) {
        //更新流程状态
        boolean success = true;
        try {
            eventZoneService.updateEventZoneUserAndState(eventId, userId, state, null);
            Map<String, Object> variables = new HashMap<String, Object>();
            if (state == EventZone.EVENT_ZONE_STATE_PASS) { //通过
                List<EventZone> zones = eventZoneService.findEventZones(eventId);
                if (zones != null && zones.size() > 0) {
                    for (EventZone eventZone : zones) {
                        //有管理员未审批或者不同意时
                        if (eventZone.getState() != EventZone.EVENT_ZONE_STATE_PASS) {
                            return new ApiResult<String>(true, "出入库单核对成功", null, null);
                        }
                    }
                }
                // 将文物状态改为“出库申请中”
                outEventService.updateRelicState(eventId, Relic.STATE_APPLYING_OUT);
                //判断，所有人都通过的时候才能通过
                variables.put("listCheckApproved", true);
            } else if (state == EventZone.EVENT_ZONE_STATE_NOT_PASS) { //不通过
                variables.put("listCheckApproved", false);
            }
            //出库单核对完成，流程转向下一环节
            taskService.complete(taskId, variables);
        } catch (Exception e) {
            log.error("出入库单核对出错", e);
            success = false;
        }
        String message = success ? "出入库单核对成功" : "出入库单核对出错";
        return new ApiResult<String>(success, message, null, null);
    }
}
