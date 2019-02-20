package com.thinkgem.jeesite.activiti;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class ProcessDefinitionTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程
    @Test
    public void deployment() {
        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .name("流程定义")
                //该步骤必须指定的是bpmn文件，而不是xml格式结尾的文件
                .addClasspathResource("activiti/HelloWorld.bpmn")
                .addClasspathResource("activiti/HelloWorld.png")
                .deploy();
        //定义一个新的流程，会写入到act_re_deployment表中
        //与此同时，会在act_re_procdef表中写入数据，该表中的deployment_id对应的是会写入到act_re_deployment表中的id
        System.out.println(deploy.getName());//
        System.out.println(deploy.getId());//
    }
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

    //查询流程定义
    @Test
    public void findProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.getResourceName());
        }
    }

    //删除流程定义
    @Test
    public void delete(){
        //流程没有完成的时候，删除会报错
        String id = "1";
//        processEngine.getRepositoryService()
//                .deleteDeployment(id);
        //级联删除，删除所有的外键，只能开放给超级管理员
        processEngine.getRepositoryService()
                .deleteDeployment("1",true);
        System.out.println("删除成功");
    }
    @Test
    public void getPic() throws IOException {
        List<String> names = processEngine.getRepositoryService()
                .getDeploymentResourceNames("5001");

        String picName = null;
        for (String name : names) {
            if(name.indexOf(".bpmn")!=-1){
                picName = name;
            }
        }
        InputStream in = processEngine.getRepositoryService()
                .getResourceAsStream("5001", picName);
        File file = new File("/Users/zhangtianzhi/Desktop/"+picName);
        FileUtils.copyInputStreamToFile(in,file);
    }

    //流程的定义只能去增加最新的版本，要么就是删除，不能修改


    //查询最新版本的流程定义
    @Test
    public void selectMostNew(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .asc().list();

        Map<String,ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();

        for(ProcessDefinition processDefinition:list){
            map.put(processDefinition.getKey(),processDefinition);
        }

        for (String s : map.keySet()) {
            System.out.println(map.get(s).getVersion());
        }

    }
    @Test
    public void deleteBykey(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionName("helloworldProcess")
                .list();

        for (ProcessDefinition processDefinition : list) {
            processEngine.getRepositoryService()
                    .deleteDeployment(processDefinition.getDeploymentId(),true);
        }
        System.out.println("删除成功");
    }
}
