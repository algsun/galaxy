package com.microwise.orion.util;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.InputStream;
import java.util.List;

/**
 * activiti 工具类
 *
 * @author gaohui
 * @date 13-6-5 14:47
 */
public class WorkFlows {

    /**
     * 返回某个流程的当前节点，如果无返回 null
     *
     * @param repositoryService
     * @param processInstance
     * @return
     */
    public static ActivityImpl currentActivity(RepositoryService repositoryService, ProcessInstance processInstance) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processDefinition;
        for (ActivityImpl activity : processDefinitionEntity.getActivities()) {
            if (processInstance.getActivityId().equals(activity.getId())) {
                return activity;
            }
        }

        return null;
    }

    /**
     * 高亮显示
     *
     * @param processEngineConfiguration
     * @param bpmnModel
     * @param highLightList
     * @return
     */
    public static InputStream processInstanceDiagram(ProcessEngineConfigurationImpl processEngineConfiguration, BpmnModel bpmnModel, List<String> highLightList) {
        // 解决乱码问题
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        InputStream inputStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", highLightList);
        Context.removeProcessEngineConfiguration();
        return inputStream;
    }
}
