package com.nm.social.operations.twitter;

import org.springframework.social.twitter.api.TweetData;
import org.springframework.util.MultiValueMap;

import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoPostData extends TweetData implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DtoPostData(String message) {
		super(message);
	}

	public String getStringMessage() {
		MultiValueMap<String, Object> multi = toRequestParameters();
		String mess = (String) multi.getFirst("status");
		return mess;
		// DO NOT SEND IMAGE BY TEXT
		// if (multi.containsKey("media")) {
		// Resource ress = (Resource) multi.getFirst("media");
		// return String.format("%s \n %s", mess, ress.getURL().toString());
		// } else {
		// return mess;
		// }
	}
}
