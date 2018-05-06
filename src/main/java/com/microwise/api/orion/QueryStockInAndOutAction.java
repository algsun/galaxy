package com.microwise.api.orion;

import com.microwise.api.bean.ApiResult;
import com.microwise.api.bean.EventAction;
import com.microwise.api.bean.OutEventVo;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.util.WorkFlows;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 14-1-8
 */
@Beans.Action
@Controller
public class QueryStockInAndOutAction {

    public static final Logger log = LoggerFactory.getLogger(QueryStockInAndOutAction.class);

    @Autowired
    private OutEventService outEventService;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

    private static final String GET = "get";

    private static final String POST = "post";

    private static final String internalVerifyApproved = "internalVerifyApproved";

    private static final String externalVerifyApproved = "externalVerifyApproved";

    private Map<String, Map<String, Object>> processes;

    @RequestMapping(value = "/orion/outEvent/{siteId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取出入库列表", position = 1, httpMethod = "GET",
            notes = "获取出入库列表"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<List<OutEventVo>> getOutEvents(@PathVariable String siteId,
                                                    @PathVariable String userId) {
        ApiResult<List<OutEventVo>> result = new ApiResult<List<OutEventVo>>();
        List<OutEvent> outEvents = outEventService.findOutEventsBySiteId(siteId, 0, 10000);
        processes = new HashMap<String, Map<String, Object>>();
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("stock-out")
                .variableValueEquals("siteId", siteId)
                .orderByProcessInstanceId().desc()
                .list();

        for (ProcessInstance processInstance : processInstances) {
            Map<String, Object> variables = runtimeService.getVariables(processInstance.getProcessInstanceId());
            String outEventId = (String) variables.get("outEventId");
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("variables", variables);
            context.put("prcIns", processInstance);
            context.put("activity", currentActivity(processInstance));

            // 候选人是我
            Task task = taskService.createTaskQuery()
                    .taskCandidateUser(String.valueOf(userId))
                    .processInstanceId(processInstance.getProcessInstanceId())
                    .singleResult();

            if (task == null) {
                // 指派人是我
                task = taskService.createTaskQuery()
                        .taskAssignee(String.valueOf(userId))
                        .processInstanceId(processInstance.getProcessInstanceId())
                        .singleResult();
            }
            context.put("task", task);
            processes.put(outEventId, context);
        }

        List<OutEventVo> outEventVos = new ArrayList<OutEventVo>();
        for (OutEvent oe : outEvents) {
            OutEventVo oev = OutEventVo.castEvent2Vo(oe);
            if (processes.get(oe.getId()) != null && processes.get(oe.getId()).get("activity") != null) {
                ActivityImpl activity = (ActivityImpl) processes.get(oe.getId()).get("activity");
                oev.setEventState(activity.getProperty("name").toString());
            } else {
                switch (oev.getState()) {
                    case 1:
                        oev.setEventState("已出库");
                        break;
                    case 2:
                        oev.setEventState("已回库");
                        break;
                }
            }
            List<EventAction> actions = new ArrayList<EventAction>();
            String eventId = oe.getId();
            if (processes.get(eventId) != null) {
                if (processes.get(eventId).get("task") != null) {
                    Task task = (Task) processes.get(eventId).get("task");
                    String taskId = task.getId();
                    oev.setTaskId(taskId);
                    String taskDefinitionKey = task.getTaskDefinitionKey();
                    if (taskDefinitionKey.equals("stockOutListCheckTask")) {
                        actions.add(new EventAction("通过", "api/orion/changeStatus/" + eventId
                                + "/" + taskId + "/" + userId + "/1", GET, null));
                        actions.add(new EventAction("不通过", "api/orion/changeStatus/" + eventId
                                + "/" + taskId + "/" + userId + "/2", GET, null));
                    } else {
                        if (task.getAssignee() != null) {
                            switch (taskDefinitionKey) {
                                case "adjustApplicationTask":
//                                    actions.add(new EventAction("重新申请", "api/orion/adjustStockOut/"
//                                            + eventId + "/" + taskId + "/" + userId, GET, null));
                                    actions.add(new EventAction("取消申请", "api/orion/cancelStockOut/"
                                            + eventId + "/" + taskId + "/" + userId, GET, null));
                                    break;
                                case "internalVerifyTask":
                                    actions.add(new EventAction("同意", "/api/orion/complete",
                                            POST, setParams(taskId, true, internalVerifyApproved)));
                                    actions.add(new EventAction("不同意", "/api/orion/complete",
                                            POST, setParams(taskId, false, internalVerifyApproved)));
                                    break;
                                case "externalVerifyTask":
                                    actions.add(new EventAction("同意", "/api/orion/complete",
                                            POST, setParams(taskId, true, externalVerifyApproved)));
                                    actions.add(new EventAction("不同意", "/api/orion/complete",
                                            POST, setParams(taskId, false, externalVerifyApproved)));
                                    break;
                                case "stockOutConfirmTask":
                                    actions.add(new EventAction("库房确认", "/api/orion/confirmStockOut/" +
                                            eventId + "/" + taskId + "/" + userId, GET, null));
                                    break;
                            }
                        } else {
                            actions.add(new EventAction("签收", "/api/orion/claimTask/" + taskId + "/" + userId, GET, null));
                        }
                    }
                }
            } else if (oev.getState() != 2) {
                //todo url 权限控制
                actions.add(new EventAction("回库", "/api/orion/returnStockOutView/" + eventId + "/" + userId, GET, null));
            }
            oev.setActions(actions);
            outEventVos.add(oev);
        }

        result.setSuccess(true);
        result.setMessage("获取出入库列表成功");
        result.setData(outEventVos);
        return result;
    }

    private ActivityImpl currentActivity(ProcessInstance processInstance) {
        return WorkFlows.currentActivity(repositoryService, processInstance);
    }

    private Map<String, Object> setParams(String taskId, boolean success, String type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("taskId", taskId);
        params.put("type", type);
        params.put("value", success);
        return params;
    }
}
