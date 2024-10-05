package com.nm.social.operations;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.constants.SocialOperationEnum;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SocialOperation {

	public boolean accept(SocialOperationEnum en);

	public boolean accept(AuthenticationProvider en);

}
