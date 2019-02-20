package com.thinkgem.jeesite.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class HelloWorld {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    //部署流程
    @Test
    public void deployment(){
        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .name("hello world 入门程序")
                .addClasspathResource("activiti/HelloWorld.bpmn")
                .addClasspathResource("activiti/HelloWorld.png")
                .deploy();

        System.out.println(deploy.getName());//hello world 入门程序
        System.out.println(deploy.getId());//17501
    }
    //启动流程
    @Test
    public void startProcess(){
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey("helloworld");
        //对应的是act_ru_task表中的proc_inst_id_
        System.out.println(pi.getId());//流程实例id 20001
        System.out.println(pi.getProcessDefinitionId());//流程定义id helloworld:2:17504
    }
    //查询流程
    @Test
    public void findMyPersonTask(){
        String assignee ="张三";
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getExecutionId());
            System.out.println(task.getProcessDefinitionId());
        }
    }
    //结束流程
    @Test
    public void compelete(){
        String taskId = "25002";
        processEngine.getTaskService().
                complete(taskId);
        System.out.println("完成了任务id："+taskId);
    }

}
