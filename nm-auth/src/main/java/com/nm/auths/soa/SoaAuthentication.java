package com.nm.auths.soa;

import java.util.Collection;

import com.nm.auths.AuthenticationException;
import com.nm.auths.daos.QueryBuilderUser;
import com.nm.auths.daos.QueryBuilderUserGroup;
import com.nm.auths.dtos.DtoAuthentication;
import com.nm.auths.dtos.DtoGroup;
import com.nm.auths.dtos.DtoUser;
import com.nm.auths.models.Authentication;
import com.nm.auths.models.UserGroup;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SoaAuthentication {
	Collection<DtoGroup> fetch(QueryBuilderUserGroup q, OptionsList options) throws AuthenticationException;

	Collection<DtoUser> fetch(QueryBuilderUser q, OptionsList options) throws AuthenticationException;

	UserGroup saveOrUpdate(DtoGroup dto, OptionsList options) throws AuthenticationException;

	void pushToGroup(DtoGroup dto, DtoUser dtoA) throws AuthenticationException;

	void authenticate(DtoAuthentication auth, OptionsList options) throws AuthenticationException;

	Authentication saveOrUpdate(DtoAuthentication auth, OptionsList options) throws AuthenticationException;
}
