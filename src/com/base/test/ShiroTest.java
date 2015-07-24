package com.base.test;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * Shiro
 * 
 * JSP
 * shiro:hasPermission
 * shiro:lacksPermission
 * shiro:hasRole
 * shiro:hasAnyRoles
 * shiro:lacksRole
 * shiro:authenticated
 * shiro:notAuthenticated
 * shiro:user
 * shiro:guest
 * shiro:principal
 * 
 * Java
 * @RequiresRoles
 * @RequiresAuthentication //无变量
 * @RequiresGuest	//no variable
 * @RequiresPermissions// String[] value;
 * @RequiresRoles //String[] value
 * @RequiresUser//no variable
 * 
 * */
public class ShiroTest {

	public static void main(String[] args) {
		
	}
	/**
	 * 角色
	 * */
	@RequiresRoles(value = {"Admin", "User" }, logical = Logical.OR)//表示当前Subject需要角色admin 或者 user。
	public void save(){
		
	}
	@RequiresRoles("Admin")//表示当前Subject需要角色admin
	public void update(){
		
	}
	@RequiresPermissions("user:create")//表示当前Subject需要权限user:edit。  
	public void edit(){
		
	}
	//可以用户类/属性/方法，用于表明当前用户需是经过认证的用户。
	@RequiresAuthentication
	public void updateAccount(Account userAccount) {
	    //this method will only be invoked by a 
	    //Subject that is guaranteed authenticated
	}

}
