package com.microwise.orion.action.stock;

import com.google.common.collect.Lists;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.bean.EventZone;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.service.EventZoneService;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.service.UserZoneService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.orion.util.WorkFlows;
import com.microwise.orion.vo.UserZoneVo;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文物出入库查询
 *
 * @author gaohui
 * @date 13-5-28 15:38
 * @check xiedeng  2013-6-6  svn:4084
 */
@Beans.Action
@Orion
public class QueryStockInAndOutAction extends OrionLoggerAction {

    @Autowired
    private OutEventService outEventService;
    @Autowired
    private EventZoneService eventZoneService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    /**
     * 用户区域关系 service
     */
    @Autowired
    private UserZoneService userZoneService;

    //input & output
    // 当前页
    private int page = 1;
    // 总页数
    private int pageCount;

    private String userStr = "";

    // output
    private List<OutEvent> outEvents;
    private Map<String, Map<String, Object>> processes;

    private String processInstanceId;

    private Map<String, List<String>> map = new HashMap<String, List<String>>();

    /**
     * 文物出入库查询
     *
     * @return
     */
    public String execute() {
        ActionMessage.createByAction().consume();

        String siteId = Sessions.createByAction().currentSiteId();
        int userId = Sessions.createByAction().currentUser().getId();

        int count = outEventService.findOutEventCountBySiteId(siteId);
        pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
        outEvents = outEventService.findOutEventsBySiteId(siteId, page, Constants.SIZE_PER_PAGE);

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
            log("出入库管理", "查询出入库");
        }

        return Action.SUCCESS;
    }

    private ActivityImpl currentActivity(ProcessInstance processInstance) {
        return WorkFlows.currentActivity(repositoryService, processInstance);
    }

    public String queryHandleListAgency() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();

        Map<String, Object> variables = runtimeService.getVariables(processInstance.getProcessInstanceId());
        String outEventId = (String) variables.get("outEventId");

        if (processInstance.getActivityId().equals("stockOutListCheckTask")) {
            List<EventZone> eventZones = eventZoneService.findEventZones(outEventId);
            for (EventZone eventZone : eventZones) {
                List<User> users = eventZone.getUsers();
                if (eventZone.getZone() == null || eventZone.getUsers().isEmpty()) continue;
                userStr = userStr + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;" + eventZone.getZone().getName() + "(";
                int index = 0;
                for (User u : users) {
                    Task task = taskService.createTaskQuery()
                            .taskCandidateUser(String.valueOf(u.getId()))
                            .processInstanceId(processInstance.getProcessInstanceId())
                            .singleResult();
                    if (task != null) {
                        if (index == 0) {
                            userStr += u.getUserName();
                            index = 1;
                        } else {
                            userStr += "&nbsp;&nbsp;" + u.getUserName();
                        }
                    }
                }
                userStr += ")";
            }
        } else {
            List<User> users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
            List<User> candidateOrAssigneeUsers = Lists.newArrayList();
            for (User user : users) {
                Task task = taskService.createTaskQuery()
                        .taskCandidateUser(String.valueOf(user.getId()))
                        .processInstanceId(processInstance.getProcessInstanceId())
                        .singleResult();
                if (task == null) {
                    task = taskService.createTaskQuery()
                            .taskAssignee(String.valueOf(user.getId()))
                            .processInstanceId(processInstance.getProcessInstanceId())
                            .singleResult();
                }
                if (task != null) {
                    candidateOrAssigneeUsers.add(user);
                }
            }
            for (User user : candidateOrAssigneeUsers) {
                userStr = userStr + "   " + user.getUserName();
            }
        }
        return Action.SUCCESS;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<OutEvent> getOutEvents() {
        return outEvents;
    }

    public void setOutEvents(List<OutEvent> outEvents) {
        this.outEvents = outEvents;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Map<String, Map<String, Object>> getProcesses() {
        return processes;
    }

    public void setProcesses(Map<String, Map<String, Object>> processes) {
        this.processes = processes;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<String>> map) {
        this.map = map;
    }

    public String getUserStr() {
        return userStr;
    }

    public void setUserStr(String userStr) {
        this.userStr = userStr;
    }
}
