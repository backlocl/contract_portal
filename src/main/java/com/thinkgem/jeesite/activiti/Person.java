package com.thinkgem.jeesite.activiti;

import java.io.Serializable;

public class Person implements Serializable{
    private static final long serialVersionUID = -7901036861470876558L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String  name;

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    private String edu;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", edu='" + edu + '\'' +
                '}';
    }
}
