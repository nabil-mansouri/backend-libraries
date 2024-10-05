package com.nm.permissions.acl.dtos;

import java.io.Serializable;

import com.nm.permissions.acl.EnumObjectID;
import com.nm.permissions.acl.EnumPermission;
import com.nm.permissions.acl.EnumSID;
import com.nm.permissions.constants.Action;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoAclImpl implements DtoAcl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean grant;
	// IDENTITY
	private EnumObjectID identity;
	private Serializable id;
	private Class<?> clazz;
	private String string;
	private Object object;
	// SID
	private EnumSID sid;
	private String username;
	private String grantedAuthority;
	// PERMISSION
	private EnumPermission permission;
	private int permissionMask;
	private String permissionName;
	private Action permissionAction;

	public Action getPermissionAction() {
		return permissionAction;
	}

	public void setPermissionAction(Action permissionAction) {
		this.permissionAction = permissionAction;
	}

	public int getPermissionMask() {
		return permissionMask;
	}

	public void setPermissionMask(int permissionMask) {
		this.permissionMask = permissionMask;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Serializable getId() {
		return id;
	}

	public String getString() {
		return string;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public void setString(String string) {
		this.string = string;
	}

	public EnumObjectID getIdentity() {
		return identity;
	}

	public void setIdentity(EnumObjectID identity) {
		this.identity = identity;
	}

	public boolean grant() {
		return grant;
	}

	public void setGrant(boolean grant) {
		this.grant = grant;
	}

	public boolean isGrant() {
		return grant;
	}

	public EnumSID getSid() {
		return sid;
	}

	public void setSid(EnumSID sid) {
		this.sid = sid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGrantedAuthority() {
		return grantedAuthority;
	}

	public void setGrantedAuthority(String grantedAuthority) {
		this.grantedAuthority = grantedAuthority;
	}

	public EnumPermission getPermission() {
		return permission;
	}

	public void setPermission(EnumPermission permission) {
		this.permission = permission;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}
