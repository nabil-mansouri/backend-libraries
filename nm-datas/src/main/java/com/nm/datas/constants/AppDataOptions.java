package com.nm.datas.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum AppDataOptions implements ModelOptions {
	Content, URL;
	public ModelOptionsType getType() {
		return AppDataOptionsType.AppData;
	}

	public enum AppDataOptionsType implements ModelOptionsType {
		AppData
	}

}