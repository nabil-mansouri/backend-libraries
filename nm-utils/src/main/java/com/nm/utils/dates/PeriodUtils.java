package com.nm.utils.dates;

import java.util.Date;

import org.joda.time.MutableDateTime;

/**
 * 
 * @author Nabil
 * 
 */
public class PeriodUtils {

	public static Date toDate(PeriodType period, Double count) {
		if (period == null || count == null) {
			return null;
		}
		MutableDateTime end = new MutableDateTime();
		int countMinus = -count.intValue();
		switch (period) {
		case Hour:
			end.addHours(countMinus);
			break;
		case Day:
			end.addDays(countMinus);
			break;
		case Week:
			end.addWeeks(countMinus);
			break;
		case Month:
			end.addMonths(countMinus);
			break;
		case Year:
			end.addYears(countMinus);
			break;
		}
		return end.toDate();
	}

	public static String toPgSQL(PeriodType period) {
		if (period == null) {
			return null;
		}
		switch (period) {
		case Hour:
			return "hour";
		case Day:
			return "day";
		case Week:
			return "week";
		case Month:
			return "month";
		case Year:
			return "year";
		}
		return null;
	}

	public static Date toDate(Date from, PeriodType period, Double count) {
		if (from == null || period == null || count == null) {
			return null;
		}
		MutableDateTime end = new MutableDateTime(from);
		int countMinus = count.intValue();
		switch (period) {
		case Hour:
			end.addHours(countMinus);
			break;
		case Day:
			end.addDays(countMinus);
			break;
		case Week:
			end.addWeeks(countMinus);
			break;
		case Month:
			end.addMonths(countMinus);
			break;
		case Year:
			end.addYears(countMinus);
			break;
		}
		return end.toDate();
	}
}
