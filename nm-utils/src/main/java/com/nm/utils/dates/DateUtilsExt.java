package com.nm.utils.dates;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Seconds;
import org.springframework.util.Assert;

import com.google.common.collect.Sets;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class DateUtilsExt {
	public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";

	public static List<Date> generateYears(Date reference, int yearsBefore, int yearsAfter) {
		Assert.notNull(reference);
		MutableDateTime first = new MutableDateTime(reference);
		first.addYears(-yearsBefore);
		MutableDateTime last = new MutableDateTime(reference);
		last.addYears(yearsAfter);
		List<Date> periods = new ArrayList<Date>();
		for (MutableDateTime date = first; (date.isBefore(last) || date.isEqual(last)); date.addYears(1)) {
			periods.add(date.toDate());
		}
		if (periods.isEmpty()) {
			periods.add(reference);
		}
		return periods;
	}

	public static Date fromMin(int day, int month, int year) {
		MutableDateTime instant = new MutableDateTime();
		instant.set(DateTimeFieldType.year(), year);
		instant.set(DateTimeFieldType.monthOfYear(), month);
		instant.set(DateTimeFieldType.dayOfMonth(), day);
		return com.nm.utils.dates.DateUtilsExt.minMsOfDay(instant.toDate());
	}

	public static Date fromMax(int day, int month, int year) {
		MutableDateTime instant = new MutableDateTime();
		instant.set(DateTimeFieldType.year(), year);
		instant.set(DateTimeFieldType.monthOfYear(), month);
		instant.set(DateTimeFieldType.dayOfMonth(), day);
		return com.nm.utils.dates.DateUtilsExt.maxMsOfDay(instant.toDate());
	}

	public static Date minMsOfDay(Date d) {
		MutableDateTime m = new MutableDateTime(d);
		m.setMillisOfDay(m.millisOfDay().getMinimumValue());
		return m.toDate();
	}

	public static Date minMsOfYear(Date d) {
		MutableDateTime m = new MutableDateTime(d);
		m.setDayOfYear(m.dayOfYear().getMinimumValue());
		m.setMillisOfDay(m.millisOfDay().getMinimumValue());
		return m.toDate();
	}

	public static Date maxMsOfYear(Date d) {
		MutableDateTime m = new MutableDateTime(d);
		m.setDayOfYear(m.dayOfYear().getMaximumValue());
		m.setMillisOfDay(m.millisOfDay().getMaximumValue());
		return m.toDate();
	}

	public static Set<Long> getYearsFor(Collection<Date> dates) {
		Set<Long> all = Sets.newHashSet();
		for (Date d : dates) {
			all.add(new Long(getYear(d)));
		}
		return all;
	}

	public static Set<Long> getMonthsFor(Collection<Date> dates) {
		Set<Long> all = Sets.newHashSet();
		for (Date d : dates) {
			all.add(new Long(getMonth(d)));
		}
		return all;
	}

	public static Date maxMsOfDay(Date d) {
		MutableDateTime m = new MutableDateTime(d);
		m.setMillisOfDay(m.millisOfDay().getMaximumValue());
		return m.toDate();
	}

	public static Date maxMsOfMonth(Date d) {
		MutableDateTime m = new MutableDateTime(d);
		m.setDayOfMonth(m.dayOfMonth().getMaximumValue());
		m.setMillisOfDay(m.millisOfDay().getMaximumValue());
		return m.toDate();
	}

	public static java.sql.Timestamp toSqlTimestamp(Date d) {
		return new java.sql.Timestamp(d.getTime());
	}

	public static java.sql.Date toSqlDate(Date d) {
		return new java.sql.Date(d.getTime());
	}

	public static Date toTime(Date dateTime) {
		return new Time(dateTime.getTime());
	}

	public static Date from(int month, int year) {
		MutableDateTime instant = new MutableDateTime();
		instant.set(DateTimeFieldType.year(), year);
		instant.set(DateTimeFieldType.monthOfYear(), month);
		instant.dayOfMonth().roundFloor();
		return instant.toDate();
	}

	public static int secondBetweenSafe(Date date1, Date date2) {
		if (date1.after(date2)) {
			return Seconds.secondsBetween(new MutableDateTime(date2), new MutableDateTime(date1)).getSeconds();
		} else {
			return Seconds.secondsBetween(new MutableDateTime(date1), new MutableDateTime(date2)).getSeconds();
		}
	}

	public static int diffDaysRound(Date start, Date end) {
		return Days.daysBetween(new MutableDateTime(start), new MutableDateTime(end)).getDays();
	}

	public static int diffMonthRound(Date start, Date end) {
		return Months.monthsBetween(new MutableDateTime(start), new MutableDateTime(end)).getMonths();
	}

	public static double diffYears(Date start, Date end) {
		return diffMonthRound(start, end) * 1d / 12;
	}

	public static long diffYearsRound(Date start, Date end) {
		return Math.round(diffYears(start, end));
	}

	public static Date from(int day, int month, int year) {
		MutableDateTime instant = new MutableDateTime();
		instant.set(DateTimeFieldType.year(), year);
		instant.set(DateTimeFieldType.monthOfYear(), month);
		instant.set(DateTimeFieldType.dayOfMonth(), day);
		return instant.toDate();
	}

	public static Date from(int ss, int min, int hour, int day, int month, int year) {
		MutableDateTime instant = new MutableDateTime();
		instant.set(DateTimeFieldType.year(), year);
		instant.set(DateTimeFieldType.monthOfYear(), month);
		instant.set(DateTimeFieldType.dayOfMonth(), day);
		instant.set(DateTimeFieldType.hourOfDay(), hour);
		instant.set(DateTimeFieldType.minuteOfHour(), min);
		instant.set(DateTimeFieldType.secondOfMinute(), ss);
		return instant.toDate();
	}

	public static int getDayOfMonth(Date period) {
		MutableDateTime instant = new MutableDateTime(period);
		return instant.get(DateTimeFieldType.dayOfMonth());
	}

	public static int getHourOfDays(Date period) {
		MutableDateTime instant = new MutableDateTime(period);
		return instant.get(DateTimeFieldType.hourOfDay());
	}

	public static int getYear(Date period) {
		MutableDateTime instant = new MutableDateTime(period);
		return instant.get(DateTimeFieldType.year());
	}

	public static String formatDateTimeDefault(Date date, String defaut) {
		if (date == null) {
			return defaut;
		}
		return format(date, DATE_TIME_PATTERN);
	}

	public static String formatDateTime(Date date) {
		return format(date, DATE_TIME_PATTERN);
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static Date parseDate(String date, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(date);
	}

	public static Date parse(String date, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(date);
	}

	public static Date parseUnSafe(String date, String pattern) {
		try {
			return parse(date, pattern);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Date parseSafe(String date, String pattern) {
		try {
			return parse(date, pattern);
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> fromDates(Collection<Date> dates, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		List<String> periods = new ArrayList<String>();
		for (Date p : dates) {
			periods.add(format.format(p));
		}
		return periods;
	}

	public static List<Date> generateMonths(Date from, Date to) {
		Assert.notNull(from);
		Assert.notNull(to);
		MutableDateTime startDate = new MutableDateTime(from);
		startDate.setDayOfMonth(startDate.dayOfMonth().getMinimumValue());
		MutableDateTime endDate = new MutableDateTime(to);
		endDate.setDayOfMonth(endDate.dayOfMonth().getMaximumValue());
		List<Date> periods = new ArrayList<Date>();
		for (MutableDateTime date = startDate; date.isBefore(endDate); date.addMonths(1)) {
			periods.add(date.toDate());
		}
		return periods;
	}

	public static List<String> generateMonths(Date from, Date to, String pattern) {
		Assert.notNull(from);
		Assert.notNull(to);
		MutableDateTime startDate = new MutableDateTime(from);
		startDate.setDayOfMonth(1);
		MutableDateTime endDate = new MutableDateTime(to);
		endDate.setDayOfMonth(20);
		List<String> periods = new ArrayList<String>();
		for (MutableDateTime date = startDate; date.isBefore(endDate); date.addMonths(1)) {
			periods.add(date.toString(pattern));
		}
		return periods;
	}

	public static List<Date> fromString(Collection<String> periods, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		List<Date> dates = new ArrayList<Date>();
		for (String p : periods) {
			dates.add(format.parse(p));
		}
		return dates;
	}

	public static Date addYear(Date from, int i) {
		MutableDateTime d = new MutableDateTime(from);
		d.addYears(i);
		return d.toDate();
	}

	public static Date addDays(Date from, int i) {
		MutableDateTime d = new MutableDateTime(from);
		d.addDays(i);
		return d.toDate();
	}

	public static Date addHours(Date from, int i) {
		MutableDateTime d = new MutableDateTime(from);
		d.addHours(i);
		return d.toDate();
	}

	public static boolean between(Long from, Long to, Long val) {
		if (from == null && to != null) {
			return val <= to;
		} else if (from != null && to == null) {
			return from <= val;
		} else if (from != null && to != null) {
			return from <= val && val <= to;
		}
		return false;
	}

	public static boolean betweenExcludeMax(Long from, Long to, Long val) {
		if (from == null && to != null) {
			return val < to;
		} else if (from != null && to == null) {
			return from <= val;
		} else if (from != null && to != null) {
			return from <= val && val < to;
		}
		return false;
	}

	public static String formatSybaseDate(Date date) {
		return DateUtilsExt.format(date, "yyyy-MM-dd");
	}

	public static Date toDateTime(Date date, Date time) {
		MutableDateTime mut = new MutableDateTime(date);
		mut.setTime(new MutableDateTime(time));
		return mut.toDate();
	}

	public static int getMonth(Date period) {
		MutableDateTime instant = new MutableDateTime(period);
		return instant.get(DateTimeFieldType.monthOfYear());
	}

}
