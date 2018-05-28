/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.salary.entity.SalaryBill;
import com.thinkgem.jeesite.modules.salary.dao.SalaryBillDao;

/**
 * 工资条管理Service
 * @author peng
 * @version 2018-03-22
 */
@Service
@Transactional(readOnly = true)
public class SalaryBillService extends CrudService<SalaryBillDao, SalaryBill> {

	@Autowired
	SalaryBillDao salaryBillDao;
	
	public SalaryBill get(String id) {
		return super.get(id);
	}
	
	public List<SalaryBill> findList(SalaryBill salaryBill) {
		return super.findList(salaryBill);
	}
	
	public Page<SalaryBill> findPage(Page<SalaryBill> page, SalaryBill salaryBill) {
		return super.findPage(page, salaryBill);
	}
	
	@Transactional(readOnly = false)
	public void save(SalaryBill salaryBill) {
		super.save(salaryBill);
	}
	
	@Transactional(readOnly = false)
	public void delete(SalaryBill salaryBill) {
		super.delete(salaryBill);
	}
	
	public SalaryBill getBySalaryMonthAndNameAndMail(SalaryBill salaryBill){
		return salaryBillDao.getBySalaryMonthAndNameAndMail(salaryBill);
	}
	
	@Transactional(readOnly = false)
	public int batchImport(List<SalaryBill> salaryBillList) {
		//工资条记录不多，逐条处理
		Date date = new Date();
		int successCount = 0;
		for(SalaryBill salaryBill : salaryBillList) {
			if(salaryBillDao.getBySalaryMonthAndNameAndMail(salaryBill) == null) {
				salaryBill.setCreateTime(date);
				salaryBill.setUpdateTime(date);
				super.save(salaryBill);
				successCount++;
			}else {
				throw new IllegalArgumentException("工资属期【" + salaryBill.getSalaryMonth() + "】，姓名【" + salaryBill.getName() + "】，邮箱【" + salaryBill.getMail() + "】的工资条记录重复");
			}
		}
		return successCount;
	}
	
	public List<String> listAllSalaryMonth(){
		return salaryBillDao.listAllSalaryMonth();
	}
}