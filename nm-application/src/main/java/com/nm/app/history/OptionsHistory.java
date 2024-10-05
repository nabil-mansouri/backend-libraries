package com.nm.app.history;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author nabilmansouri
 * 
 */
public enum OptionsHistory implements ModelOptions {
	;

	public static final String ADAPTER_KEY = "OptionsHistory.ADAPTER_KEY";

	public enum OptionsHistoryType implements ModelOptionsType {
		History
	}

	public ModelOptionsType getType() {
		return OptionsHistoryType.History;
	}
}
