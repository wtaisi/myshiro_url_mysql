package com.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_PERMISSIONS_URLS")
public class PermissionsUrl implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8472909773986346688L;
	private int id;
	private int urlId;
	private int permissionId;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="url_id")
	public int getUrlId() {
		return urlId;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	@Column(name="permission_id")
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	@Override
	public String toString() {
		return "PermissionsUrl [urlId=" + urlId + ", permissionId="
				+ permissionId + "]";
	}
	
}
