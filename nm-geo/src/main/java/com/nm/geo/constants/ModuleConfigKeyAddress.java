package com.nm.geo.constants;

import com.nm.config.constants.ModuleConfigKey;

/**
 * 
 * @author Nabil
 * 
 */
public enum ModuleConfigKeyAddress implements ModuleConfigKey {
	GoogleKey("AIzaSyDDwjhfiHivgNfuxkCCAJnCOykewrYCjJU");
	private final String defaut;

	private ModuleConfigKeyAddress(String defaut) {
		this.defaut = defaut;
	}

	public boolean isPlainText() {
		return true;
	}

	public boolean isFile() {
		return false;
	}

	public String getDefaut() {
		return defaut;
	}
}
