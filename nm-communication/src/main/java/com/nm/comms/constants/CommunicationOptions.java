package com.nm.comms.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author nabilmansouri
 *
 */
public enum CommunicationOptions implements ModelOptions {
	;

	public static final String ADAPTER_KEY = "CommunicationOptions.ADAPTER_KEY";

	public enum CommunicationOptionsType implements ModelOptionsType {
		Communication
	}

	public ModelOptionsType getType() {
		return CommunicationOptionsType.Communication;
	}
}
