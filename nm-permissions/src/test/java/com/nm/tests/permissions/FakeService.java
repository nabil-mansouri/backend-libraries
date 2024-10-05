package com.nm.tests.permissions;

import org.springframework.security.access.annotation.Secured;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface FakeService {
	@Secured({ "ROLE_Default.List" })
	public void authorized();

	@Secured({ "ROLE_Default.Export" })
	public void notAuthorized();
}
