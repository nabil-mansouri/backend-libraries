package com.nm.auths.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface DtoAuthentication extends DtoUser {

	Long getId();

	String getFullName();

	void setId(Long id);

}