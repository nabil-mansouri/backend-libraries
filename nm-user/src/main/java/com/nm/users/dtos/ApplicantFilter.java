package com.nm.users.dtos;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class ApplicantFilter {
	private Boolean validated;
	private Boolean hasNotToken;

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public Boolean getHasNotToken() {
		return hasNotToken;
	}

	public void setHasNotToken(Boolean hasNotToken) {
		this.hasNotToken = hasNotToken;
	}

}
