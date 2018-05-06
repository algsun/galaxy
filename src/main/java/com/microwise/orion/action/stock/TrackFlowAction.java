package com.microwise.orion.action.stock;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

/**
 * 流程跟踪
 *
 * Created By xiedeng
 * Date: 13-6-4
 * Time: 上午9:50
 *
 * @check xiedeng 2013-6-6 16:02 svn:4084
 */
@Beans.Action
@Orion
public class TrackFlowAction extends OrionLoggerAction {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    // input
    private String processInstanceId;

    //ouput
    private InputStream inputStream;

    /**
     * 流程跟踪
     * @return
     */
    public String execute() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();

        inputStream = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
        log("出入库管理","流程跟踪");
        return Action.SUCCESS;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
