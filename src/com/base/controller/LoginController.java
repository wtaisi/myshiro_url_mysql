/**
 * 
 */
package com.base.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.base.entity.Permissions;
import com.base.entity.Roles;
import com.base.entity.Url;
import com.base.entity.User;
import com.base.service.UserServiceImpl;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	@RequestMapping()
	public String index() {
		if(SecurityUtils.getSubject().getSession()!=null){
			if(SecurityUtils.getSubject().getSession().getAttribute("user")!=null){
				return "index";
			}
		}
		return "login";
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/";
	}
	/**
	 * admin
	 * @return
	 */
	@RequestMapping(value="/admin")
	public String admin() {
		return "admin";
	}
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping(value="/signup")
	public String signup() {
		return "signup";
	}
	/*
	 * 修改个人信息
	 */
	@RequestMapping(value="/updateAccount")
	public String updateAccount() {
		return "updateAccount";
	}
	/**
	 * 创建用户
	 * @return
	 */
	@RequestMapping(value="/createUser")
	public String createUser() {
		return "createUser";
	}
	
	/**
	 * 动态添加权限
	 * @return
	 */
	@RequestMapping(value="/submitCreateUser", method = RequestMethod.POST)
	public String submitCreateUser(String username, String password, String roles, String permission, String url,String roleDescription,String urlDescription) {
		List<Roles> roleList=new ArrayList<Roles>();
		List<Permissions> permissionsList=new ArrayList<Permissions>();
		List<Url> urlList=new ArrayList<Url>();
		if(SecurityUtils.getSubject().getSession()!=null){
			if(SecurityUtils.getSubject().getSession().getAttribute("user")!=null){
				Subject subject = SecurityUtils.getSubject();
				if(subject.isPermitted("user:create")){
					User u = (User) subject.getSession().getAttribute("user");
					
					Url ur=new Url();
					ur.setUrl(url);
					ur.setAvailable(true);
					ur.setDescription(urlDescription);
					urlList.add(ur);
					
					Permissions p=new Permissions();
					p.setPermission(permission);
					p.setAvailable(true);
					p.setUrlList(urlList);
					permissionsList.add(p);
					
					Roles r=new Roles();
					r.setRole(roles);
					r.setDescription(roleDescription);
					r.setAvailable(true);
					roleList.add(r);
					r.setPermissionsList(permissionsList);
					
					u.setUsername(username);
					u.setPassword(password);
					u.setLocked(false);
					u.setSalt("456");
					u.setRoleList(roleList);
					
					userService.addUser(u);
					return "index";
				}
			}
		}
		return "redirect:/";
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
