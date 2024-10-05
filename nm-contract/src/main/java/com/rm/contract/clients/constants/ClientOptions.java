package com.rm.contract.clients.constants;

import com.rm.contract.commons.constants.ModelOptions;
import com.rm.contract.commons.constants.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum ClientOptions implements ModelOptions {
	Addresses, LastAddress;

	public ModelOptionsType getType() {
		return ModelOptionsType.Client;
	}

}