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
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>shiro 登入界面</title>
</head>
<body>
	<h1><%=u%></h1>
	<h3>shiro 登入界面</h3>
	
	<%=basePath%>
	<form action="<%=basePath%>login/submit" method="post">
		<table>
			<tr>
				<td>账号</td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交" /></td>
			</tr>
		</table>
	</form>
	<a href="<%=basePath%>login/logout">登出</a>
</body>
</html>