package com.nm.users.exceptions;

import com.nm.users.dtos.UserGroupForm;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class GroupExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserGroupForm form;

	public GroupExistsException(UserGroupForm form) {
		this.form = form;
	}

	public UserGroupForm getForm() {
		return form;
	}
}
