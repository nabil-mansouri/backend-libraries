package com.nm.permissions.acl.dtos;

import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface DtoAcl extends Dto, DtoAclSid, DtoAclObjectId, DtoAclPermission {
	public boolean grant();
}
