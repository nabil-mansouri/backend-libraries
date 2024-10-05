package com.nm.app.currency;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class CurrencyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String symbol;
	private String name;
	private String symbol_native;
	private String decimal_digits;
	private String rounding;
	private String code;
	private String name_plural;
	private boolean selected;
	private String defaut;

	public CurrencyDto() {
	}

	public CurrencyDto(String c) {
		setCode(c);
	}

	public String getDefaut() {
		return defaut;
	}

	public CurrencyDto setDefaut(String defaut) {
		this.defaut = defaut;
		return this;
	}

	public String getSymbol() {
		return symbol;
	}

	public CurrencyDto setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public String getName() {
		return name;
	}

	public CurrencyDto setName(String name) {
		this.name = name;
		return this;
	}

	public String getSymbol_native() {
		return symbol_native;
	}

	public CurrencyDto setSymbol_native(String symbol_native) {
		this.symbol_native = symbol_native;
		return this;
	}

	public String getDecimal_digits() {
		return decimal_digits;
	}

	public CurrencyDto setDecimal_digits(String decimal_digits) {
		this.decimal_digits = decimal_digits;
		return this;
	}

	public String getRounding() {
		return rounding;
	}

	public CurrencyDto setRounding(String rounding) {
		this.rounding = rounding;
		return this;
	}

	public String getCode() {
		return code;
	}

	public CurrencyDto setCode(String code) {
		this.code = code;
		return this;
	}

	public String getName_plural() {
		return name_plural;
	}

	public CurrencyDto setName_plural(String name_plural) {
		this.name_plural = name_plural;
		return this;
	}

	public boolean isSelected() {
		return selected;
	}

	public CurrencyDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
