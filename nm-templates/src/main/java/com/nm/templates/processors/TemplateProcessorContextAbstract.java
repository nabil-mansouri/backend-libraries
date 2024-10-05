package com.nm.templates.processors;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.utils.dates.DateUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class TemplateProcessorContextAbstract implements TemplateProcessorContext {
	protected abstract Set<TemplateArgsEnum> all(Set<TemplateArgsEnum> empty);

	public int priority() {
		return 0;
	}

	public boolean hasDependencies() {
		return false;
	}

	public boolean accept(TemplateArgsEnum arg) {
		Set<TemplateArgsEnum> a = Sets.newHashSet();
		return all(a).contains(arg);
	}

	public static String date(Date val) {
		if (val == null) {
			return "";
		}
		return DateUtilsExt.format(val, "dd/MM/yyyy");
	}

	public static String val(String val) {
		if (Strings.isNullOrEmpty(StringUtils.trim(val))) {
			return "";
		}
		return val;
	}

	public static String val(Long val) {
		if (val == null) {
			return "0";
		}
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(val);
	}

	public static String val(Integer val) {
		if (val == null) {
			return "0";
		}
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(val);
	}

	//
	protected static String rounds(Double value) {
		return rounds(value, true);
	}

	protected static String rounds(Double percent, boolean toPercent) {
		if (percent == null) {
			return "0";
		}
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setMaximumFractionDigits(1);
		formatter.setDecimalFormatSymbols(symbols);
		if (toPercent) {
			return formatter.format(Math.round(percent * 100));
		} else {
			return formatter.format(Math.round(percent));
		}
	}

	protected String percent(Double percent) {
		if (percent == null) {
			return "0";
		}
		return string(percent * 100);
	}

	protected String percent(Double percent, int nb) {
		if (percent == null) {
			return "0";
		}
		return string(percent * 100, nb);
	}

	protected static String abs(Long val) {
		return val(Math.abs(val));
	}

	protected static String abs(Double val) {
		return string(Math.abs(val));
	}

	protected static String abs(Double val, int index) {
		return string(Math.abs(val), index);
	}

	protected static String string(Double val) {
		if (val == null) {
			return "0";
		}
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setMaximumFractionDigits(1);
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(val);
	}

	protected static String val(Boolean b) {
		return BooleanUtils.toStringTrueFalse(b);
	}

	protected static String string(Double val, int maxDecimal) {
		if (val == null) {
			return "0";
		}
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setMaximumFractionDigits(maxDecimal);
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(val);
	}

}
