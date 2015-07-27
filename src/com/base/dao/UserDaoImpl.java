package com.base.dao;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.base.entity.User;


@Repository("userDao")
public class UserDaoImpl{
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public User findByUserName(String name) {
		String hql = "from User u where u.username=:id ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("id", name);
		return (User)query.uniqueResult();
	}
	
	
	public void addEntiry(Object obj) {
		sessionFactory.getCurrentSession().save(obj);
	}
	
}
