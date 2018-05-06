package com.microwise.orion.action.stock;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 完成任务
 *
 * @author gaohui
 * @date 13-5-30 21:00
 *
 * @check xiedeng 2013-6-6 15:24 svn:3953
 */
@Beans.Action
@Orion
public class CompleteTaskAction extends OrionLoggerAction {

    @Autowired
    private TaskService taskService;

    //input
    // taskId
    private String taskId;

    // task variable
    private Map<String, String> var;
    // variable 对应的类型
    private Map<String, String> type;

    //完成后跳转到的页面, 以 "/" 开始
    private String forward;

    //output
    // 调用成功与否
    private boolean success;

    /**
     *  完成任务
     * @return
     */
    public String execute() {
        try {
            if (var == null) {
                taskService.complete(taskId);
            } else {
                taskService.complete(taskId, convertToSuitableType());
            }
            success = true;
        } catch (Exception e) {
            logFailed("完成任务", "完成任务失败");
            e.printStackTrace();
            success = false;
        }

        if (forward.contains("?")) {
            forward = forward + "&_success=" + success;
        } else {
            forward = forward + "?_success=" + success;
        }
        return Action.SUCCESS;
    }

    /**
     * 将 var 中的变量转换为合适的类型
     */
    private Map<String,Object> convertToSuitableType() {
        Map<String,Object> variables = new HashMap<String, Object>();

        for (Map.Entry<String, String> entry : var.entrySet()) {
            if (type.containsKey(entry.getKey())) {
                String suitableType = type.get(entry.getKey());
                if (suitableType.equals("boolean")) {
                    variables.put(entry.getKey(), Boolean.valueOf(entry.getValue()));
                } else if (suitableType.equals("int")) {
                    variables.put(entry.getKey(), Integer.valueOf(entry.getValue()));
                }else{
                    variables.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return variables;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, String> getVar() {
        return var;
    }

    public void setVar(Map<String, String> var) {
        this.var = var;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getType() {
        return type;
    }

    public void setType(Map<String, String> type) {
        this.type = type;
    }
}
