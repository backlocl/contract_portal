package com.thinkgem.jeesite.activiti;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import java.util.Map;

public class ProcessVariablesTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    @Test
    public void deploy_inputstream(){
        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .name("审批流程（流程变量）")
                .addClasspathResource("activiti/processVariables.bpmn")
                .addClasspathResource("activiti/processVariables.png")
                .deploy();

        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }
    @Test
    public void start(){
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("processVariables");
    }
    @Test
    public void setVariable(){
//        TaskService taskService = processEngine.getTaskService();
//        String id = "2504";
//        Person p = new Person();
//        p.setId(20);
//        p.setName("王八蛋2");
//
//        taskService.setVariable(id,"人员信息2",p);
//        String id = "7502";
//        taskService.setVariableLocal(id,"请假天数",5); //与任务id绑定
//        taskService.setVariable(id,"请假日期",new Date());
//        taskService.setVariable(id,"请假原因","回家探亲,随便相亲");
        //设置变量必须和task向绑定
        Person p = new Person();
        p.setName("王小二");
        p.setId(233);
        p.setEdu("野鸡");
        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("张晓晓")
                .singleResult();
        processEngine.getTaskService()
                .setVariable(task.getId(),"请假原因",p);
        System.out.println("ok");

    }
    @Test
    public void getVariable(){
        //传递的是act_ru_task表中的主键和键值对中的键
        /*TaskService taskService = processEngine.getTaskService();

        Object o = taskService.getVariable("2504", "请假原因");

        System.out.println(o);*/
        /*TaskService taskService = processEngine.getTaskService();

        Map<String, Object> map = taskService.getVariables("2504");

        for (String s : map.keySet()) {
            Object o = map.get(s);

            System.out.println(o);
        }*/
        //获取runtimeService 操作的是act_ru_execution 表
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        Map<String, Object> map = runtimeService.getVariables("2501");
//        //只能获取两个，有一个和act_ru_task绑定了
//        for (String s : map.keySet()) {
//            Object o = map.get(s);
//
//            System.out.println(o);
//        }

        //taskservice对应的是 task表
        //runtimeservice 对应的是 execution表
        Execution execution = processEngine.getRuntimeService()
                .createExecutionQuery()
                .processInstanceId("2501")
                .singleResult();
        Map<String, Object> map = processEngine.getRuntimeService()
                .getVariables(execution.getId());

        for (String s : map.keySet()) {
            System.out.println(map.get(s));
        }


    }
    @Test
    public void getHistory(){
        HistoricVariableInstance result = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId("2501")
                .singleResult();
        System.out.println(result.getVariableName());
    }

    @Test
    public void getNew(){
        Object o = processEngine.getTaskService()
                .getVariable("2504", "人员信息2");
        System.out.println(o);
    }

    @Test
    public void complete(){
        String id ="7502";
        processEngine.getTaskService()
                .complete(id);
        System.out.println("ok");
    }
    @Test
    public void select_history(){
        HistoricVariableInstance historicVariableInstance = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .id("7502")
                .singleResult();
        System.out.println(historicVariableInstance.getVariableName());
    }
}
