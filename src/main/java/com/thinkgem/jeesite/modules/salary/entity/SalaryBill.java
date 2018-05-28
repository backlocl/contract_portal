/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 工资条管理Entity
 * @author peng
 * @version 2018-03-22
 */
public class SalaryBill extends DataEntity<SalaryBill> {
	
	private static final long serialVersionUID = 1L;
	
	private String salaryMonth;		// 工资属期
	private String company;		// 所属公司
	private String name;		// 姓名
	private String mail;		// 邮箱
	private String fixSalary;		// 固定工资
	private String floatingSalary;		// 应发浮动工资
	private String debit;		// 扣款
	private String reissue;		// 补发
	private String shouldPay;		// 应发工资合计
	private String accFund;		// 代扣代缴公积金
	private String socialSecurity;		// 代扣代缴社会保险
	private String otherSubsidy;		// 其他补款
	private String otherDebig;		// 其他扣款
	private String netShouldPay;		// 扣除代扣代缴及其他补扣款应发合计
	private String personIncomeTax;		// 个人所得税
	private String realPay;		// 实发工资
	private String remark;		// 备注
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	public SalaryBill() {
		super();
	}

	public SalaryBill(String id){
		super(id);
	}

	@Length(min=1, max=20, message="工资属期长度必须介于 1 和 20 之间")
	@ExcelField(title="工资属期", align=2, sort=10)
	public String getSalaryMonth() {
		return salaryMonth;
	}

	public void setSalaryMonth(String salaryMonth) {
		this.salaryMonth = salaryMonth;
	}
	
	@Length(min=1, max=50, message="所属公司长度必须介于 1 和 50 之间")
	@ExcelField(title="所属公司", align=2, sort=20)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Length(min=1, max=20, message="姓名长度必须介于 1 和 20 之间")
	@ExcelField(title="姓名", align=2, sort=30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=30, message="邮箱长度必须介于 1 和 30 之间")
	@ExcelField(title="邮箱", align=2, sort=40)
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Length(min=1, max=20, message="固定工资长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="固定工资", align=2, sort=50)
	public String getFixSalary() {
		return fixSalary;
	}

	public void setFixSalary(String fixSalary) {
		this.fixSalary = fixSalary;
	}
	
	@Length(min=1, max=20, message="应发浮动工资长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="应发浮动工资", align=2, sort=60)
	public String getFloatingSalary() {
		return floatingSalary;
	}

	public void setFloatingSalary(String floatingSalary) {
		this.floatingSalary = floatingSalary;
	}
	
	@Length(min=1, max=20, message="扣款长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="扣款", align=2, sort=70)
	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}
	
	@Length(min=1, max=20, message="补发长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="补发", align=2, sort=80)
	public String getReissue() {
		return reissue;
	}

	public void setReissue(String reissue) {
		this.reissue = reissue;
	}
	
	@Length(min=1, max=20, message="应发工资合计长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="应发工资合计", align=2, sort=90)
	public String getShouldPay() {
		return shouldPay;
	}

	public void setShouldPay(String shouldPay) {
		this.shouldPay = shouldPay;
	}
	
	@Length(min=1, max=20, message="代扣代缴公积金长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="代扣代缴公积金", align=2, sort=100)
	public String getAccFund() {
		return accFund;
	}

	public void setAccFund(String accFund) {
		this.accFund = accFund;
	}
	
	@Length(min=1, max=20, message="代扣代缴社会保险长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="代扣代缴社会保险", align=2, sort=110)
	public String getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	
	@Length(min=1, max=20, message="其他补款长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="其他补款", align=2, sort=120)
	public String getOtherSubsidy() {
		return otherSubsidy;
	}

	public void setOtherSubsidy(String otherSubsidy) {
		this.otherSubsidy = otherSubsidy;
	}
	
	@Length(min=1, max=20, message="其他扣款长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="其他扣款", align=2, sort=130)
	public String getOtherDebig() {
		return otherDebig;
	}

	public void setOtherDebig(String otherDebig) {
		this.otherDebig = otherDebig;
	}
	
	@Length(min=1, max=20, message="扣除代扣代缴及其他补扣款应发合计长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="扣除代扣代缴及其他补扣款应发合计", align=2, sort=140)
	public String getNetShouldPay() {
		return netShouldPay;
	}

	public void setNetShouldPay(String netShouldPay) {
		this.netShouldPay = netShouldPay;
	}
	
	@Length(min=1, max=20, message="个人所得税长度必须介于 1 和 20 之间")
	@Digits(integer=10, fraction=2) 
	@ExcelField(title="个人所得税", align=2, sort=150)
	public String getPersonIncomeTax() {
		return personIncomeTax;
	}

	public void setPersonIncomeTax(String personIncomeTax) {
		this.personIncomeTax = personIncomeTax;
	}
	
	@Length(min=1, max=20, message="实发工资长度必须介于 1 和 20 之间")
	@ExcelField(title="实发工资", align=2, sort=160)
	@Digits(integer=10, fraction=2) 
	public String getRealPay() {
		return realPay;
	}

	public void setRealPay(String realPay) {
		this.realPay = realPay;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	@ExcelField(title="备注", align=2, sort=170)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}