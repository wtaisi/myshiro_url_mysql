<%@page import="com.base.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
	<%  
        String path = request.getContextPath();  
        String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path + "/";  
        
        User u=(User)session.getAttribute("user");
    %>
    <!-- 
    Shiro提供了一套JSP标签库来实现页面级的授权控制。 
	在使用Shiro标签库前，首先需要在JSP引入shiro标签： 
	-->
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>动态添加权限</title>
</head>
<body>
	
	<form action="<%=basePath%>login/submitCreateUser" method="post">
		<table>
			<tr>
				<td>账号</td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="text" name="password" /></td>
			</tr>
			<tr>
				<td>角色</td>
				<td><input type="text" name="roles" /></td>
			</tr>
			<tr>
				<td>角色description</td>
				<td><input type="text" name="description" /></td>
			</tr>
			<tr>
				<td>权限</td>
				<td><input type="text" name="permission" /></td>
			</tr>
			<tr>
				<td>url</td>
				<td><input type="text" name="url" /></td>
			</tr>
			<tr>
				<td>urlDescription</td>
				<td><input type="text" name="urlDescription" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交" /></td>
			</tr>
		</table>
	</form>

</body>
</html>