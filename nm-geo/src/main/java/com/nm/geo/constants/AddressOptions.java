package com.nm.geo.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum AddressOptions implements ModelOptions {
	;

	public ModelOptionsType getType() {
		return AddressOptionsType.Address;
	}

	public enum AddressOptionsType implements ModelOptionsType {
		Address
	}
}