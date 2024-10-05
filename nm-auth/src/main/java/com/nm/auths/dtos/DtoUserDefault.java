package com.nm.auths.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoUserDefault implements DtoUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(Long id) {
		this.userId = id;
	}

}
