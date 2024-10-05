package com.nm.comms.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author nabilmansouri
 * 
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface MessagePartType {

	public enum MessagePartTypeDefault implements MessagePartType {
		Content, Title, Joined;

	}
}