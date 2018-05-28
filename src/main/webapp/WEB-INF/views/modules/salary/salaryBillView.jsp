<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工资条查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/salary/salaryBillView/authCheck">工资条查询</a></li>
	</ul><br/>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">工资属期：</label>
			<div class="controls">
				${salaryBill.salaryMonth}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属公司：</label>
			<div class="controls">
				${salaryBill.company}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				${salaryBill.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				${salaryBill.mail}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">固定工资：</label>
			<div class="controls">
				${salaryBill.fixSalary}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应发浮动工资：</label>
			<div class="controls">
				${salaryBill.floatingSalary}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣款：</label>
			<div class="controls">
				${salaryBill.debit}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补发：</label>
			<div class="controls">
				${salaryBill.reissue}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应发工资合计：</label>
			<div class="controls">
				${salaryBill.shouldPay}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代扣代缴公积金：</label>
			<div class="controls">
				${salaryBill.accFund}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代扣代缴社会保险：</label>
			<div class="controls">
				${salaryBill.socialSecurity}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他补款：</label>
			<div class="controls">
				${salaryBill.otherSubsidy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他扣款：</label>
			<div class="controls">
				${salaryBill.otherDebig}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣除代扣代缴及其他补扣款应发合计：</label>
			<div class="controls">
				${salaryBill.netShouldPay}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人所得税：</label>
			<div class="controls">
				${salaryBill.personIncomeTax}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实发工资：</label>
			<div class="controls">
				${salaryBill.realPay}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				${salaryBill.remark}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>