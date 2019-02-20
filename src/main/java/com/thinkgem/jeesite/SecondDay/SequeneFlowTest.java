package com.thinkgem.jeesite.SecondDay;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SequeneFlowTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void testSequenceFlow(){
        InputStream sequeneFlowBpmnIo = this.getClass().getResourceAsStream("sequeneFlow.bpmn");

        InputStream sequeneFlowPngIo = this.getClass().getResourceAsStream("sequeneFlow.png");

        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .addInputStream("sequeneFlow.bpmn", sequeneFlowBpmnIo)
                .addInputStream("sequeneFlow.png", sequeneFlowPngIo)
                .name("连线")
                .deploy();

        System.out.println("部署成功 id为："+deploy.getId());

    }
    @Test
    public void start(){
        processEngine.getRuntimeService()
                .startProcessInstanceByKey("sequeneFlow");
        System.out.println("启动成功");
    }
    @Test
    public void findMyPersonTask(){
        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("赵六")
                .singleResult();
        System.out.println(task.getId());
    }


    @Test
    public void complete(){
        String id = "12503";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("message","重要");
        processEngine.getTaskService()
                .complete(id,map);
        System.out.println("完成成功");
    }
}
