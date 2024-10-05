package com.nm.config.constants;

/**
 * 
 * @author Nabil
 * 
 */
public enum ModuleConfigKeyDefault implements ModuleConfigKey {
	Langs("['fr']"), DefaultLang("fr"), //
	DefaultDevise("EUR"), //
	DefaultTaxs("[]"), //
	OrderTypes("[]"), //
	PriceSelector("");
	private final String defaut;
	private final boolean file;
	private final boolean plain;

	private ModuleConfigKeyDefault(String defaut) {
		this.defaut = defaut;
		this.file = false;
		this.plain = false;
	}

	private ModuleConfigKeyDefault(String defaut, boolean file) {
		this.defaut = defaut;
		this.file = file;
		this.plain = false;
	}

	private ModuleConfigKeyDefault(String defaut, boolean file, boolean plain) {
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
