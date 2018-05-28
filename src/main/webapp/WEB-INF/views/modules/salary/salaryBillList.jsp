<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工资条管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/salary/salaryBill/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/salary/salaryBill/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/salary/salaryBill/">工资条列表</a></li>
		<shiro:hasPermission name="salary:salaryBill:edit"><li><a href="${ctx}/salary/salaryBill/form">工资条添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="salaryBill" action="${ctx}/salary/salaryBill/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>工资属期：</label>
				<form:input path="salaryMonth" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>ID</th>
				<th>工资属期</th>
				<th>姓名</th>
				<th>邮箱</th>
				<th>固定工资</th>
				<th>应发浮动工资</th>
				<th>应发工资合计</th>
				<th>实发工资</th>
				<th>创建时间</th>
				<shiro:hasPermission name="salary:salaryBill:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="salaryBill">
			<tr>
				<td><a href="${ctx}/salary/salaryBill/form?id=${salaryBill.id}">
					${salaryBill.id}
				</a></td>
				<td>
					${salaryBill.salaryMonth}
				</td>
				<td>
					${salaryBill.name}
				</td>
				<td>
					${salaryBill.mail}
				</td>
				<td>
					${salaryBill.fixSalary}
				</td>
				<td>
					${salaryBill.floatingSalary}
				</td>
				<td>
					${salaryBill.shouldPay}
				</td>
				<td>
					${salaryBill.realPay}
				</td>
				<td>
					<fmt:formatDate value="${salaryBill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="salary:salaryBill:edit"><td>
    				<a href="${ctx}/salary/salaryBill/form?id=${salaryBill.id}">修改</a>
					<a href="${ctx}/salary/salaryBill/delete?id=${salaryBill.id}" onclick="return confirmx('确认要删除该工资条吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>