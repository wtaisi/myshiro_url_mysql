package com.base.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

@Entity
@Table(name="SYS_USERSS")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7234908391692730154L;
	private int id;
	private String username;
	private String password;
	private String salt;
	private boolean locked;
	
	
	private String newPwd;//新密码
	private String confirmPwd;//确认密码
	
	
	@Transient
	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	@Transient
	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	private List<Roles> roleList = Lists.newArrayList();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="salt")
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/*两者都是设定加载策略，前者是JPA标准设定加载策略的注解属性，后者是hibernate自有加载策略注解。可选值如下：
	@Fetch(FetchMode.JOIN)：始终立刻加载，使用外连（outer join）立刻加载关联对象，且忽略FetchType.LAZY。
	@Fetch(FetchMode.SELECT)   默认懒加载（除非设定lazy=false)，在访问每一个关联对象时用Select查询加载。有n+1次查询。
	@Fetch(FetchMode.SUBSELECT)  默认懒加载（除非设定lazy=false)，在第一次访问关联对象时才一次性加载所有关联对象。共产生两条sql语句，且FetchType设定有效。
	*/
	// 多对多定义
	@ManyToMany(
			targetEntity=com.base.entity.Roles.class,
			//设定加载策略，是JPA标准设定加载策略的注解属性
    	fetch=FetchType.EAGER,//急加载，加载一个实体时，定义急加载的属性会立即从数据库中加载。（FetchType.LAZY：懒加载）从类在主类加载的时候同时加载
    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "SYS_USERS_ROLESS", 
		joinColumns = {@JoinColumn(name = "user_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "role_id") })
	//是hibernate加载策略
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//缓存方式：只读模式，在此模式下，如果对数据进行更新操作，会有异常；
	@JsonIgnore
	public List<Roles> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Roles> roleList) {
		this.roleList = roleList;
	}
	
	@Column(name="locked")
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", salt=" + salt + ", locked=" + locked + "]";
	}
	
}
