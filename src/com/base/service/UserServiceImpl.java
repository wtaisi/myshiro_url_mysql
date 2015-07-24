package com.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.UserDaoImpl;
import com.base.entity.User;
@Service("userService")
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl {
	
	@Autowired
	private UserDaoImpl userDao;

	
	public User findByUserName(String username){
		return userDao.findByUserName(username);
	}
	
}
