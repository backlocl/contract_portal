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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 工资条查看Controller
 * @author peng
 * @version 2018-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/salary/salaryBillView")
public class SalaryBillViewController extends BaseController {

	@Autowired
	private SalaryBillService salaryBillService;
	
	@RequiresPermissions("salary:salaryBillView:view")
	@RequestMapping(value = "authCheck")
	public String authCheck(Model model) {
		List<String> salaryMonthList = salaryBillService.listAllSalaryMonth();
		model.addAttribute("salaryMonthList", salaryMonthList);
		return "modules/salary/salaryBillViewAuthCheck";
	}
	
	@RequiresPermissions("salary:salaryBillView:view")
	@RequestMapping(value = "view")
	public String view(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try {
			String salaryMonth = request.getParameter("salaryMonth");
			String cardNo = request.getParameter("cardNo");
			if(StringUtils.isBlank(salaryMonth)) {
				throw new IllegalArgumentException("工资属期不能为空");
			}
			if(StringUtils.isBlank(cardNo)) {
				throw new IllegalArgumentException("工资卡号后4位不能为空");
			}
			User currentUser = UserUtils.getUser();
			if(StringUtils.isBlank(currentUser.getCardNo())) {
				throw new IllegalArgumentException("当前用户无工资卡记录，请联系管理员");
			}
			if(!cardNo.equalsIgnoreCase(StringUtils.substring(currentUser.getCardNo(), StringUtils.length(currentUser.getCardNo()) - 4))) {
				throw new IllegalArgumentException("工资卡卡号不正确");
			}
			SalaryBill param = new SalaryBill();
			param.setName(currentUser.getName());
			param.setMail(currentUser.getEmail());
			param.setSalaryMonth(salaryMonth);
			if(StringUtils.isBlank(param.getName())){
				throw new IllegalArgumentException("当前用户姓名为空");
			}
			if(StringUtils.isBlank(param.getMail())) {
				throw new IllegalArgumentException("当前用户邮箱为空");
			}
			SalaryBill salaryBill = salaryBillService.getBySalaryMonthAndNameAndMail(param);
			//double check，防止查出来显示的工资条不是本人的
			if(salaryBill!=null && !salaryBill.getName().equalsIgnoreCase(currentUser.getName())) {
				throw new IllegalArgumentException("数据异常");
			}
			model.addAttribute("salaryBill", salaryBill);
			return "modules/salary/salaryBillView";
//			我
            
		}catch(Exception e) {
			addMessage(redirectAttributes, "查询失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/salary/salaryBillView/authCheck?repage";
	}

}