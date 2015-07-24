package com.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYS_ROLES_PERMISSIONSS")
public class RolesPermissions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -227405828701041142L;
	private int id;
	private int roleId;
	private int permissionId;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="role_id")
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
		return "RolesPermissions [roleId=" + roleId + ", permissionId="
				+ permissionId + "]";
	}
	

}
