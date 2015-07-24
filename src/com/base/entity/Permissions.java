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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

@Entity
@Table(name="SYS_PERMISSIONSS")
public class Permissions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 523947516201521089L;
	private int id;
	private String permission;
	private String description;
	private boolean available;
	
	
	private String menus;
	
	private List<Roles> roleList = Lists.newArrayList();
	//关联url
	private List<Url> urlList = Lists.newArrayList();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
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
		// 多对多定义
		//persist 级联保存，级联更新（merge)
		@ManyToMany(
				targetEntity=com.base.entity.Roles.class,
	    	fetch=FetchType.EAGER,//立刻加载，在查询主对象的时候同时加载关联对象。
	    	/**
	    	 *  CascadeType.PERSIST只有A类新增时，会级联B对象新增。若B对象在数据库存（跟新）在则抛异常（让B变为持久态） 
				CascadeType.MERGE指A类新增或者变化，会级联B对象（新增或者变化） 
				CascadeType.REMOVE只有A类删除时，会级联删除B类； 
				CascadeType.ALL包含所有； 
				CascadeType.REFRESH没用过。 
				综上：大多数情况用CascadeType.MERGE就能达到级联跟新又不报错，用CascadeType.ALL时要斟酌下CascadeType.REMOVE 
	    	 */
	    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
		)
		//joinColumns写的都是本表在中间表的外键名称
		@JoinTable(name = "SYS_ROLES_PERMISSIONSS", //关联表名
			joinColumns = {@JoinColumn(name = "permission_id") }, //name="主表外键"
		//inverseJoinColumns写的是另一个表在中间表的外键名称。
			inverseJoinColumns = { @JoinColumn(name = "role_id") })//name="从表外键"
		@Fetch(FetchMode.SUBSELECT)//默认懒加载(除非设定关联属性lazy=false),在访问第一个关联对象时加载所有的关联对象。会累计产生两条sql语句。且FetchType设定有效。
		@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//基于时间戳判定机制，，对于数据同步要求严格的情况，使用频繁 (二级缓存方案)
		@JsonIgnore
		public List<Roles> getRoleList() {
			return roleList;
		}
		public void setRoleList(List<Roles> roleList) {
			this.roleList = roleList;
		}
		
		/**
		 * 获取所有url的id
		 * */
		@JsonIgnore
		@Transient
		public List<Integer> getUrlIds(){
			List<Integer> ids = Lists.newArrayList();
			if(urlList!=null && urlList.size()>0){
				for(Url menu: urlList){
					ids.add(menu.getId());
				}
			}
			return ids;
		}
		
		
		// 多对多定义
		@ManyToMany(
				targetEntity=com.base.entity.Url.class,
	    	fetch=FetchType.EAGER,
	    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
		)
		@JoinTable(name = "SYS_PERMISSIONS_URLS", 
			joinColumns = {@JoinColumn(name = "permission_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "url_id") })
		@Fetch(FetchMode.SUBSELECT)
		@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
		@JsonIgnore
	public List<Url> getUrlList() {
			return urlList;
		}
	public void setUrlList(List<Url> urlList) {
			this.urlList = urlList;
		}
	
	@Transient
	public String getMenus() {
		return menus;
	}
	public void setMenus(String menus) {
		this.menus = menus;
	}
	
	@Override
	public String toString() {
		return "Permissions [id=" + id + ", permission=" + permission
				+ ", description=" + description + ", available=" + available
				+  "]";
	}
	
}
