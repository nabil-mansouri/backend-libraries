package com.nm.datas.constants;

import com.nm.config.constants.ModuleConfigKey;

/**
 * 
 * @author Nabil
 * 
 */
public enum ModuleConfigKeyData implements ModuleConfigKey {
	BaseDir("/");
	private final String defaut;
	private final boolean file;
	private final boolean plain;

	private ModuleConfigKeyData(String defaut) {
		this.defaut = defaut;
		this.file = false;
		this.plain = false;
	}

	private ModuleConfigKeyData(String defaut, boolean file) {
		this.defaut = defaut;
		this.file = file;
		this.plain = false;
	}

	private ModuleConfigKeyData(String defaut, boolean file, boolean plain) {
		this.defaut = defaut;
		this.file = file;
		this.plain = plain;
	}

	public boolean isPlainText() {
		return plain;
	}

	public boolean isFile() {
		return file;
	}

	public String getDefaut() {
		return defaut;
	}
}
