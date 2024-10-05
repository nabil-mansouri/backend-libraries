package com.nm.auths.oauth;

import java.io.Serializable;

import com.nm.auths.dtos.DtoAuthenticationOAuth;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface TokenProcessor extends Serializable {

	public void hydrate(DtoAuthenticationOAuth dto) throws Exception;
}
