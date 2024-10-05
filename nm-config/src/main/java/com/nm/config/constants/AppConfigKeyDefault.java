package com.nm.config.constants;

/**
 * 
 * @author Nabil
 * 
 */
public enum AppConfigKeyDefault implements AppConfigKey {
	DefaultParallelThread("15"), StatisticDateMaxGeneration("64"),
	//
	RulesOrderNextStates("classpath:rules/orders/nextstates.drl", true), //
	RulesOrderCurrentState("classpath:rules/orders/currentstate.drl", true), //
	AdminSmtp(""), AdminEmailFrom("");
	private final String defaut;
	private final boolean file;
	private final boolean plain;

	private AppConfigKeyDefault(String defaut) {
		this.defaut = defaut;
		this.file = false;
		this.plain = false;
	}

	private AppConfigKeyDefault(String defaut, boolean file) {
		this.defaut = defaut;
		this.file = file;
		this.plain = false;
	}

	private AppConfigKeyDefault(String defaut, boolean file, boolean plain) {
		this.defaut = defaut;
		this.file = file;
		this.plain = plain;
	}

	public boolean isPlainText() {
		return plain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nm.app.config.AppConfigKey#getDefaut()
	 */
	public String getDefaut() {
		return defaut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nm.app.config.AppConfigKey#isFile()
	 */
	public boolean isFile() {
		return file;
	}
}
