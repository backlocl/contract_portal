/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.salary.entity.SalaryBill;
import com.thinkgem.jeesite.modules.salary.service.SalaryBillService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 工资条管理Controller
 * @author peng
 * @version 2018-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/salary/salaryBill")
public class SalaryBillController extends BaseController {

	@Autowired
	private SalaryBillService salaryBillService;
	
	@ModelAttribute
	public SalaryBill get(@RequestParam(required=false) String id) {
		SalaryBill entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = salaryBillService.get(id);
		}
		if (entity == null){
			entity = new SalaryBill();
		}
		return entity;
	}
	
	@RequiresPermissions("salary:salaryBill:view")
	@RequestMapping(value = {"list", ""})
	public String list(SalaryBill salaryBill, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SalaryBill> page = salaryBillService.findPage(new Page<SalaryBill>(request, response), salaryBill); 
		model.addAttribute("page", page);
		return "modules/salary/salaryBillList";
	}

	@RequiresPermissions("salary:salaryBill:view")
	@RequestMapping(value = "form")
	public String form(SalaryBill salaryBill, Model model) {
		model.addAttribute("salaryBill", salaryBill);
		return "modules/salary/salaryBillForm";
	}

	@RequiresPermissions("salary:salaryBill:edit")
	@RequestMapping(value = "save")
	public String save(SalaryBill salaryBill, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, salaryBill)){
			return form(salaryBill, model);
		}
		Date date = new Date();
		try {
			if(StringUtils.isEmpty(salaryBill.getId())) {//新增
				if(salaryBillService.getBySalaryMonthAndNameAndMail(salaryBill) != null) {
					throw new IllegalArgumentException("工资属期【" + salaryBill.getSalaryMonth() + "】，姓名【" + salaryBill.getName() + "】，邮箱【" + salaryBill.getMail() + "】的工资条记录重复");
				}
				salaryBill.setCreateTime(date);
			}
			salaryBill.setUpdateTime(date);
			salaryBillService.save(salaryBill);
			addMessage(redirectAttributes, "保存工资条成功");
		}catch(Exception e) {
			addMessage(redirectAttributes, "新增工资条失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/salary/salaryBill/?repage";
	}
	
	@RequiresPermissions("salary:salaryBill:edit")
	@RequestMapping(value = "delete")
	public String delete(SalaryBill salaryBill, RedirectAttributes redirectAttributes) {
		salaryBillService.delete(salaryBill);
		addMessage(redirectAttributes, "删除工资条成功");
		return "redirect:"+Global.getAdminPath()+"/salary/salaryBill/?repage";
	}

	/**
	 * 下载导入工资条数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("salary:salaryBill:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工资条数据导入模板.xlsx";
    		List<SalaryBill> list = Lists.newArrayList(); 
    		new ExportExcel("工资条数据", SalaryBill.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/salary/salaryBill/list?repage";
    }
	
	/**
	 * 导入工资条数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("salary:salaryBill:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SalaryBill> list = ei.getDataList(SalaryBill.class);
			for(SalaryBill salaryBill : list) {
				this.beanValidator(salaryBill);
			}
			successNum = salaryBillService.batchImport(list);
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条工资条记录");
		} catch(ConstraintViolationException ce) {
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ce, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入工资条失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/salary/salaryBill/list?repage";
    }
	
	
}