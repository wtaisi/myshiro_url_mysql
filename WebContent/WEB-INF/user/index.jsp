<%@page import="com.base.entity.Url"%>
<%@page import="com.base.entity.Permissions"%>
<%@page import="com.base.entity.Roles"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>shiro 成功界面</title>
</head>
<body>
	<h1><%=u%></h1>
	<h3>1.验证当前用户是否为“访客”，即未认证（包含未记住）的用户 </h3>
	<shiro:guest>
    Hi there!  Please <a href="<%=basePath%>login">Login</a> or <a href="<%=basePath%>login/Signup">Signup</a> today!
	</shiro:guest>
	
	<h3>2.user标签 认证通过或已记住的用户 </h3>
	<shiro:user>
    Welcome back John!  Not John? Click <a href="<%=basePath%>login">here</a> to login.
	</shiro:user>
	
	<h3>3.authenticated标签  已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。 </h3>
	<shiro:authenticated>
    <a href="<%=basePath%>login/updateAccount">Update your contact information</a>.
	</shiro:authenticated>
	
	<h3>4.notAuthenticated标签  未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。 </h3>
	<shiro:notAuthenticated>
    Please <a href="<%=basePath%>login">login</a> in order to update your credit card information.
	</shiro:notAuthenticated>
	
	<h3>5.principal 	标签 输出当前用户信息，通常为登录帐号信息  </h3>
	Hello, <shiro:principal/>, how are you today?
	
	
	<h3>6.hasRole标签 		验证当前用户是否属于该角色 </h3>
	<shiro:hasRole name="admin">
    <a href="<%=basePath%>login/admin">Administer the system</a>
	</shiro:hasRole>
	
	<h3>7.lacksRole标签 	 与hasRole标签逻辑相反，当用户不属于该角色时验证通过 </h3>
	<shiro:lacksRole name="admin">
    Sorry, you are not allowed to administer the system.
	</shiro:lacksRole>
	
	<h3>8.hasAnyRole标签 	验证当前用户是否属于以下任意一个角色。  </h3>
	<shiro:hasAnyRoles name="admin, user, administrator">
    You are either a admin, user, or administrator.
	</shiro:hasAnyRoles>
	
	<h3>9.hasPermission标签 	验证当前用户是否拥有制定权限 </h3>
	<shiro:hasPermission name="user:create">
    <a href="<%=basePath%>login/createUser">Create a new User</a>
	</shiro:hasPermission>
	
	
	<h3>10.lacksPermission标签 	与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过 </h3>
	<shiro:lacksPermission name="user:create">
    <a href="createUser.jsp">Create a new User</a>
	</shiro:lacksPermission>
	
<hr/>	
<hr/>	

	 <a href="<%=basePath%>login/logout">登出</a>
	 <h3>用户：	<%=u.getUsername()%><%-- ${user.username} --%> 	登录成功</h3>
	 <h3>角色为:</h3>
	 <%
	 for(int i=0;i<u.getRoleList().size();i++){
		 Roles role=u.getRoleList().get(i);
	 	out.write(role.getRole()+"<br/>");
	 }
	 %>
	 <h3>权限为：</h3>
	 <%
	 for(int i=0;i<u.getRoleList().size();i++){
		 for(int k=0;k<u.getRoleList().get(i).getPermissionsList().size();k++){
			 Permissions authority=u.getRoleList().get(i).getPermissionsList().get(k);
	 	 	 out.write(authority.getPermission()+","+authority.getDescription()+"<br/>");
		 }
	 }
	 %>
	 <h3>url:</h3>
	<%
	 for(int i=0;i<u.getRoleList().size();i++){
		 for(int k=0;k<u.getRoleList().get(i).getPermissionsList().size();k++){
			 for(int j=0;j<u.getRoleList().get(i).getPermissionsList().get(k).getUrlList().size();j++){
				 Url menu=u.getRoleList().get(i).getPermissionsList().get(k).getUrlList().get(j);
	 	 	 	out.write("<a href='"+menu.getUrl()+"' target='_black'>"+u.getRoleList().get(i).getPermissionsList().get(k).getDescription()+"("+menu.getDescription()+")"+"</a><br/>");
			 }
		 }
	 }
	 %>
</body>
</html>