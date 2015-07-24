/**
 * 
 */
package com.base.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.base.entity.Permissions;
import com.base.entity.User;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	
	@RequestMapping()
	public String index() {
		if(SecurityUtils.getSubject().getSession()!=null){
			if(SecurityUtils.getSubject().getSession().getAttribute("user")!=null){
				return "index";
			}
		}
		return "login";
	}
	
	@RequestMapping(value="/go",method = RequestMethod.POST)
	public String forward() {
		return "login";
	}
	
	@RequestMapping(value="/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/";
	}
	
	@RequestMapping(value="/signup")
	public String signup() {
		SecurityUtils.getSubject().logout();
		return "signup";
	}

	@RequestMapping(value="/updateAccount")
	public String updateAccount() {
		return "updateAccount";
	}
	
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(String username, String password) {
		try {
				UsernamePasswordToken token = new UsernamePasswordToken(username,password);
				Subject subject = SecurityUtils.getSubject();
				subject.login(token);
				if (subject.hasRole("admin")||subject.hasRole("user")) {// 券商
					User u = (User) subject.getSession().getAttribute("user");
					String q = u.getUsername();
					System.out.println("用户名:	"+q+"，角色："+u.getRoleList().get(0).getRole());
				}
				if(subject.isPermitted("user:create")){
					User u = (User) subject.getSession().getAttribute("user");
					List<Permissions> li= u.getRoleList().get(0).getPermissionsList();
					for(Permissions p:li){
						System.out.println("权限为名:	"+p.getPermission());
					}
				}
		}catch (UnknownAccountException e) {
			return "UnknownAccountException";
		}catch (IncorrectCredentialsException  e) {
			return "IncorrectCredentialsException";
		}catch (LockedAccountException  e) {
			return "LockedAccountException";
		}catch (ExcessiveAttemptsException  e) {
			return "ExcessiveAttemptsException";
		}catch (AuthenticationException  e) {
			return "AuthenticationException";
		}
		
		return "index";
	}
}
