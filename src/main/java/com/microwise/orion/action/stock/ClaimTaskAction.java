package com.microwise.orion.action.stock;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaohui
 * @date 13-5-30 17:19
 * @check xiedeng 2013-6-6 15:24 svn:3909
 */
@Beans.Action
@Orion
public class ClaimTaskAction extends OrionLoggerAction {

    @Autowired
    private TaskService taskService;

    //input
    private String taskId;

    public String execute() {
        try{
            taskService.claim(taskId, String.valueOf(Sessions.createByAction().currentUser().getId()));
            ActionMessage.createByAction().success("签收成功");
        }catch (Exception e){
            logFailed("签收任务", "签收任务失败");
            e.printStackTrace();
            ActionMessage.createByAction().fail("签收失败");
        }

        return Action.SUCCESS;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
