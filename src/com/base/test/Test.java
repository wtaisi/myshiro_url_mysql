package com.base.test;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.base.entity.User;
import com.base.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={
		"classpath:spring-hibernate.xml",
		"classpath:applicationContext-shiro.xml"
		})
public class Test {
	
	@Resource
	private UserServiceImpl userService;
	
	
	@org.junit.Test
	public void aaaaaa(){
		User user=userService.findByUserName("zhang");
		System.out.println(user);
		System.out.println(user.getRoleList());
		System.out.println(user.getRoleList().get(0).getPermissionsList());
		System.out.println(user.getRoleList().get(0).getPermissionsList().get(0).getUrlList());
	}
	

}
