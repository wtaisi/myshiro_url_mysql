package com.base.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.google.common.collect.Lists;

@Entity
@Table(name="SYS_ROLESS")
public class Roles implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 4135502044300658564L;
		private int id;
		private String role;
		private String description;
		private boolean available;
		private List<User> userList = Lists.newArrayList();
		private List<Permissions> permissionsList = Lists.newArrayList();
		
		//存放角色对应的人员和权限
		private String member;
		private String permission;
		
		@Transient
		public String getMember() {
			return member;
		}
		public void setMember(String member) {
			this.member = member;
		}
		@Transient
		public String getPermission() {
			return permission;
		}
		public void setPermission(String permission) {
			this.permission = permission;
		}
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isAvailable() {
			return available;
		}
		public void setAvailable(boolean available) {
			this.available = available;
		}
		@ManyToMany(
				targetEntity=com.base.entity.Permissions.class,
		    	fetch=FetchType.EAGER,
		    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
			)
		@JoinTable(name = "SYS_ROLES_PERMISSIONSS", 
			joinColumns = {@JoinColumn(name = "role_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "permission_id") })
		@Fetch(FetchMode.SUBSELECT)
		@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
		public List<Permissions> getPermissionsList() {
			return permissionsList;
		}
		public void setPermissionsList(List<Permissions> permissionsList) {
			this.permissionsList = permissionsList;
		}
		
		// 多对多定义
		@ManyToMany(
				targetEntity=com.base.entity.User.class,
	    	fetch=FetchType.EAGER,
	    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
		)
		@JoinTable(name = "SYS_USERS_ROLESS", 
				joinColumns = {@JoinColumn(name = "role_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "user_id") })
		@Fetch(FetchMode.SUBSELECT)
		@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
		public List<User> getUserList() {
			return userList;
		}
		public void setUserList(List<User> userList) {
			this.userList = userList;
		}
		/**
		 * 获取所有权限的标识
		 * @return
		 */
		@Transient
		public List<String> getPermissions(){
			List<String> permissions = Lists.newArrayList();
			if(permissionsList!=null && permissionsList.size()>0){
				for(Permissions auth: permissionsList){
					permissions.add(auth.getPermission());
				}
			}
			return permissions;
		}
		
		
		@Override
		public String toString() {
			return "Roles [id=" + id + ", role=" + role + ", description="
					+ description + ", available=" + available + "]";
		}
	
}
