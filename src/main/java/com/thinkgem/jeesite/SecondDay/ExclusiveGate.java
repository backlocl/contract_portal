package com.thinkgem.jeesite.SecondDay;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExclusiveGate {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    @Test
    public void deploy(){
        InputStream exclusiveGateWayBpmnIo = this.getClass().getResourceAsStream("exclusiveGateWay.bpmn");

        InputStream exclusiveGateWayPngIo = this.getClass().getResourceAsStream("exclusiveGateWay.png");

        if(exclusiveGateWayBpmnIo==null||exclusiveGateWayPngIo==null){
            System.out.println("null");
        }

        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .addInputStream("exclusiveGateWay.bpmn", exclusiveGateWayBpmnIo)
                .addInputStream("exclusiveGateWay.png", exclusiveGateWayPngIo)
                .name("网关")
                .deploy();

        System.out.println("部署成功 id为："+deploy.getId());
    }

    @Test
    public void start(){
        processEngine.getRuntimeService()
                .startProcessInstanceByKey("parallelGateWay");
        System.out.println("启动成功");
    }
    @Test
    public void select(){
        TaskService service = processEngine.getTaskService();
        service.complete("10002");
    }
    @Test
    public void get(){
        processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId("").list();
    }
}
