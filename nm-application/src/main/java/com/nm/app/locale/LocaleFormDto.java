package com.nm.app.locale;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class LocaleFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String nativeName;
	private String code;
	private boolean selected;
	private String defaut;

	public LocaleFormDto() {
	}

	public LocaleFormDto(String code) {
		setCode(code);
	}

	public String isDefaut() {
		return defaut;
	}

	public LocaleFormDto setDefaut(String defaut) {
		this.defaut = defaut;
		return this;
	}

	public String getName() {
		return name;
	}

	public LocaleFormDto setName(String name) {
		this.name = name;
		return this;
	}

	public String getNativeName() {
		return nativeName;
	}

	public LocaleFormDto setNativeName(String nativeName) {
		this.nativeName = nativeName;
		return this;
	}

	public String getCode() {
		return code;
	}

	public LocaleFormDto setCode(String code) {
		this.code = code;
		return this;
	}

	public boolean isSelected() {
		return selected;
	}

	public LocaleFormDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
