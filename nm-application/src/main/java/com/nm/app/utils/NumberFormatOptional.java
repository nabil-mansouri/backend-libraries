package com.nm.app.utils;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class NumberFormatOptional extends NumberFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NumberFormat wrappedFormat;
	private String nullValue;

	public NumberFormatOptional(NumberFormat wrappedFormat) {
		this(wrappedFormat, "");
	}

	public NumberFormatOptional(NumberFormat wrappedFormat, String nullValue) {
		this.wrappedFormat = wrappedFormat;
		this.nullValue = nullValue;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj == null) {
			return toAppendTo.append(nullValue);
		}
		return wrappedFormat.format(obj, toAppendTo, pos);
	}

	@Override
	public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
		return wrappedFormat.format(number, toAppendTo, pos);
	}

	@Override
	public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
		return wrappedFormat.format(number, toAppendTo, pos);
	}

	@Override
	public Number parse(String source, ParsePosition parsePosition) {
		if (source == null || nullValue.equals(source)) {
			return null;
		}
		return wrappedFormat.parse(source, parsePosition);
	}

}
