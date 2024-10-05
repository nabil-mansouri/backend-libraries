package com.nm.permissions;

import java.util.Collection;

import com.nm.permissions.daos.PermissionGridQueryBuilder;
import com.nm.permissions.dtos.DtoPermissionGrid;
import com.nm.permissions.dtos.DtoSubject;
import com.nm.permissions.models.Subject;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SoaPermission {
	// TODO
	// public Set<GrantedAuthority> authorities(DtoSubject subject, OptionsList
	// options);
	public Subject getOrCreate(DtoSubject dto, OptionsList options) throws PermissionException;

	public DtoPermissionGrid editGrid(DtoSubject subject, OptionsList options) throws PermissionException;

	public DtoPermissionGrid viewGrid(DtoSubject subject, OptionsList options) throws PermissionException;

	public DtoPermissionGrid resetGrid(DtoSubject subject, OptionsList options) throws PermissionException;

	public DtoPermissionGrid saveOrUpdate(DtoPermissionGrid permission, OptionsList options) throws PermissionException;

	public Collection<DtoPermissionGrid> fetch(PermissionGridQueryBuilder query, OptionsList options)
			throws PermissionException;

}
