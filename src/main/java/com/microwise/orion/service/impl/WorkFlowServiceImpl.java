package com.microwise.orion.service.impl;

import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.service.WorkFlowService;
import com.microwise.orion.sys.Orion;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaohui
 * @date 13-5-31 14:41
 */
@Beans.Service
@Orion
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Override
    public Map<String,String> findOutEventsAtStockOutConfirm(String siteId) {
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(Constants.StockOut.PROCESS_DEFINITION_KEY)
                .taskDefinitionKey(Constants.StockOut.TASK_STOCK_OUT_CONFIRM)
                .processVariableValueEquals("siteId", siteId)
                .list();

        Map<String,String> outEventIds = new HashMap<String,String>();
        for (Task task : tasks) {
            Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
            String outEventId = (String) variables.get("outEventId");
            outEventIds.put(outEventId,task.getId());
        }

        return outEventIds;
    }

    @Override
    public String findTaskIdAtStockOutConfirm(String outEventId) {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(Constants.StockOut.PROCESS_DEFINITION_KEY)
                .taskDefinitionKey(Constants.StockOut.TASK_STOCK_OUT_CONFIRM)
                .processVariableValueEquals("outEventId",outEventId)
                .singleResult();
        if(task != null){
            return task.getId();
        }
        return null;
    }
}
