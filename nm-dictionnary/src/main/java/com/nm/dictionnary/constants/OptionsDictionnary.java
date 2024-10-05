package com.nm.dictionnary.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author nabilmansouri
 * 
 */
public enum OptionsDictionnary implements ModelOptions {
	DictionnarySafe;

	public enum OptionsDictionnaryType implements ModelOptionsType {
		Dictionnary,
	}

	public ModelOptionsType getType() {
		return OptionsDictionnaryType.Dictionnary;
	}
}
