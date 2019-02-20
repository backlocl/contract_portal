package com.thinkgem.jeesite.SecondDay;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.InputStream;

public class parallel {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    @Test
    public void deploy(){
        InputStream parallelGateWayBpmnIo = this.getClass().getResourceAsStream("parallelGateWay.bpmn");

        InputStream parallelGateWayPngIo = this.getClass().getResourceAsStream("parallelGateWay.png");

        if(parallelGateWayBpmnIo==null||parallelGateWayPngIo==null){
            System.out.println("null");
        }

        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .addInputStream("parallelGateWay.bpmn", parallelGateWayBpmnIo)
                .addInputStream("parallelGateWay.png", parallelGateWayPngIo)
                .name("平行网关")
                .deploy();

        System.out.println("部署成功 id为："+deploy.getId());
    }
}
