package com.nm.auths.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum AuthenticationOptions implements ModelOptions {
	Authenticate, RedirectFromProvider;

	public ModelOptionsType getType() {
		return AuthenticationOptionsType.Auth;
	}

	public enum AuthenticationOptionsType implements ModelOptionsType {
		Auth
	}
}