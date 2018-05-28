<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工资条管理</title>
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
	<form:form id="inputForm" class="form-horizontal" method="post" action="${ctx}/salary/salaryBillView/view">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">工资属期：</label>
			<div class="controls">
				<select name="salaryMonth" class="input-xlarge required">
				<c:forEach items="${salaryMonthList}" var="list">  
		        	<option value="${list}">${list}</option>  
		        </c:forEach>  
		        </select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工资卡后4位：</label>
			<div class="controls">
				<input name="cardNo" htmlEscape="false" maxlength="4" type="password" class="input-xlarge required"></input>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="salary:salaryBillView:view"><input id="btnSubmit" class="btn btn-primary" type="submit" value="确 定"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>