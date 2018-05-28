/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.salary.entity.SalaryBill;

/**
 * 工资条管理DAO接口
 * @author peng
 * @version 2018-03-22
 */
@MyBatisDao
public interface SalaryBillDao extends CrudDao<SalaryBill> {
	
	public SalaryBill getBySalaryMonthAndNameAndMail(SalaryBill salaryBill);
	
	public List<String> listAllSalaryMonth();
}