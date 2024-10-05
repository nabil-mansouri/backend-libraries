package com.nm.social.operations.fb;

import org.springframework.social.facebook.api.PostData;

import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoPostData extends PostData implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DtoPostData(String targetFeedId) {
		super(targetFeedId);
	}

	

}
