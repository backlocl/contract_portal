package com.thinkgem.jeesite.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;
@SuppressWarnings("all")
public class ProcessInstance {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    //部署流程，从zip部署
    public void depolymentProcessDefinition_zip() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("activiti/zip.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .name("流程定义")
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println(deploy.getId());//
        System.out.println(deploy.getName());//
    }
    @Test
    public void start(){
        /*org.activiti.engine.runtime.ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceById("helloworld:1:7504");
        //部署的是流程实列表中的id
        System.out.println(processInstance.getId());*/
        //不建议通过以上的方式启动 id是自动生成的  通过key启动，默认使用最新的版本
        org.activiti.engine.runtime.ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey("helloworld");
        //对应的是act_ru_task表中的proc_inst_id_
        System.out.println(pi.getId());//10001
        System.out.println(pi.getProcessDefinitionId());//

    }
    @Test
    public void findMyPersonTask(){
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .processInstanceId("10001")
                .list();
        for (Task task : list) {
            System.out.println(task.getAssignee());
        }

    }
    @Test
    public void compelte(){
        processEngine.getTaskService().complete("15002");
    }
    @Test
    public void select(){
        Execution execution = processEngine.getRuntimeService()
                .createExecutionQuery()
                .processInstanceId("10001")
                .singleResult();
        if(execution==null){
            System.out.println("执行完毕");
        }else{
            System.out.println("在执行中");
        }

    }
    @Test
    public void history(){
        List<HistoricProcessInstance> list1 = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .list();
        for (HistoricProcessInstance historicProcessInstance : list1) {
            System.out.println(historicProcessInstance.getEndTime());
        }
        /*ProcessInstanceHistoryLog processInstanceHistoryLog = processEngine.getHistoryService()
                .createProcessInstanceHistoryLogQuery("20001")
                .singleResult();

        System.out.println(processInstanceHistoryLog.getStartTime());*/

        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee("张三")
                .list();

        for (HistoricTaskInstance historicTaskInstance : list) {
            System.out.println(historicTaskInstance.getEndTime());
        }

    }
    @Test
    public void getAll(){
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            System.out.println(historicProcessInstance.getEndTime());
        }
    }

}
