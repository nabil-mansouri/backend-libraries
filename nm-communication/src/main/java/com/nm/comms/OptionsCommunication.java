package com.nm.comms;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author nabilmansouri
 * 
 */
public enum OptionsCommunication implements ModelOptions {
	;

	public static final String ADAPTER_KEY = "OptionsCommunication.ADAPTER_KEY";

	public enum OptionsCommunicationType implements ModelOptionsType {
		Communication
	}

	public ModelOptionsType getType() {
		return OptionsCommunicationType.Communication;
	}
}
