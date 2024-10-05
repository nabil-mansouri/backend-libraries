package com.nm.permissions.acl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.Permission;

import com.nm.permissions.acl.dtos.DtoAcl;
import com.nm.permissions.acl.dtos.DtoAclObjectId;
import com.nm.permissions.acl.dtos.DtoAclPermission;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SoaAcl {

	public <T> Collection<T> filter(Collection<T> list, Collection<Permission> perms) throws AclException;

	public <T> Collection<T> filter(Collection<T> list, Permission... permissions) throws AclException;

	public void checkAuthorization(DtoAclObjectId object, Collection<? extends DtoAclPermission> perm)
			throws AccessDeniedException;

	public void checkAuthorization(Object object, Permission... permissions) throws AccessDeniedException;

	public void checkAuthorization(Object object, Collection<Permission> perms) throws AccessDeniedException;

	public Serializable saveAcl(DtoAcl identity) throws AclException;

	public void deleteAcl(DtoAcl identity, boolean withChildren) throws AclException;
}
