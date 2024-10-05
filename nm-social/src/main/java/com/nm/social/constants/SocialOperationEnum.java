package com.nm.social.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface SocialOperationEnum {
	public enum SocialOperationEnumDefault implements SocialOperationEnum {
		LoadMe, LoadFriend, LoadNetworks, PublishPost, LoadEvents,PublishEvents
	}
}
