package com.nm.social.operations.args;

import java.util.Collection;

import com.nm.social.models.SocialNetwork;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SocialMessageInfo {
	boolean postIntoWall();

	boolean sendDirectMessage();

	Collection<SocialNetwork> networks();
}
