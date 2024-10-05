package com.nm.app.locale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class LocaleDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<String> locales = new ArrayList<String>();
	private String defaultLocale;

	protected LocaleDto() {
	}

	public LocaleDto(String defaultLocale) {
		super();
		this.defaultLocale = defaultLocale;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public List<String> getLocales() {
		return locales;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean add(String arg0) {
		return locales.add(arg0);
	}

	public boolean remove(String arg0) {
		return locales.remove(arg0);
	}

}
