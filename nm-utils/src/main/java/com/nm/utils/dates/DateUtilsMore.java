package com.nm.utils.dates;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;

/**
 * 
 * @author Nabil
 * 
 */
public class DateUtilsMore {

	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String DEFAULT_DATETIME_FORMAT = DEFAULT_DATE_FORMAT + " HH:mm:ss";

	public static Date fromJSLong(Long s) {
		return new Date(s);
	}

	public static Date currentDate() {
		return getCurrentLocaleDateTime();
	}

	public static String currentDateString() {
		return format(getCurrentLocaleDateTime(), DEFAULT_DATE_FORMAT);
	}

	public static String currentDateTime() {
		return currentDateTime(DEFAULT_DATETIME_FORMAT);
	}

	public static String currentDateTime(String pattern) {
		return format(getCurrentLocaleDateTime(), pattern);
	}

	public static Date endDays(Date m) {
		return endDays(new MutableDateTime(m)).toDate();
	}

	public static MutableDateTime endDays(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMaximumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMaximumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMaximumValue());
		m.setHourOfDay(m.hourOfDay().getMaximumValue());
		return m;
	}

	public static Date endMins(Date m) {
		return endMins(new MutableDateTime(m)).toDate();
	}

	public static MutableDateTime endMins(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMaximumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMaximumValue());
		return m;
	}

	public static Collection<Interval> difference(Interval i1, Interval i2) {
		Collection<Interval> results = new ArrayList<Interval>();
		Interval intersect = i1.overlap(i2);
		if (intersect == null) {
			results.add(i1);
		} else {
			Interval all = union(i1, i2);
			results.add(new Interval(all.getStart(), i2.getStart()));
			results.add(new Interval(i2.getEnd(), all.getEnd()));
		}
		return results;
	}

	public static Interval union(Interval firstInterval, Interval secondInterval) {
		// Purpose: Produce a new Interval instance from the outer limits of any
		// pair of Intervals.
		// Take the earliest of both starting date-times.
		DateTime start = firstInterval.getStart().isBefore(secondInterval.getStart()) ? firstInterval.getStart()
				: secondInterval.getStart();
		// Take the latest of both ending date-times.
		DateTime end = firstInterval.getEnd().isAfter(secondInterval.getEnd()) ? firstInterval.getEnd()
				: secondInterval.getEnd();
		// Instantiate a new Interval from the pair of DateTime instances.
		Interval unionInterval = new Interval(start, end);
		return unionInterval;
	}

	public static String format(Date date, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	public static String formatDateTime(Date date) {
		return formatDateTime(date, DEFAULT_DATETIME_FORMAT);
	}

	public static String formatDateTime(Date date, String pattern) {
		return format(date, pattern);
	}

	private static Date getCurrentLocaleDateTime() {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		return calendar.getTime();
	}

	public static Interval intersect(Date begin1, Date end1, Date begin2, Date end2) throws NoIntervalException {
		Interval interval1 = new Interval(begin1.getTime(), end1.getTime());
		Interval interval2 = new Interval(begin2.getTime(), end2.getTime());
		Interval result = interval1.overlap(interval2);
		if (result == null) {
			throw new NoIntervalException();
		}
		return result;
	}

	public static Interval intersect(Interval interval1, Date begin2, Date end2) throws NoIntervalException {
		Interval interval2 = new Interval(begin2.getTime(), end2.getTime());
		Interval result = interval1.overlap(interval2);
		if (result == null) {
			throw new NoIntervalException();
		}
		return result;
	}

	public static Interval intersect(Interval interval1, Time begin, Time end) throws NoIntervalException {
		MutableDateTime beginDate = toDatetime(interval1.getStart(), begin);
		MutableDateTime endDate = toDatetime(interval1.getEnd(), end);
		Interval interval2 = new Interval(beginDate, endDate);
		Interval result = interval1.overlap(interval2);
		if (result == null) {
			throw new NoIntervalException();
		}
		return result;
	}

	public static Interval intersectWithNextDayOfWeek(Interval interval1, int dayOfWeekStart)
			throws NoIntervalException {
		MutableDateTime begin = new MutableDateTime(interval1.getStart());
		begin.setDayOfWeek(dayOfWeekStart);
		if (begin.isAfter(interval1.getEnd())) {
			throw new NoIntervalException("There is no next day in this interval");
		} else if (begin.isBefore(interval1.getStart())) {
			//TRY 1 WEEK LATER
			begin.addWeeks(1);
			begin.setDayOfWeek(dayOfWeekStart);
			if (begin.isAfter(interval1.getEnd())) {
				throw new NoIntervalException("There is no next day in this interval");
			} else if (begin.isBefore(interval1.getStart())) {
				throw new NoIntervalException("Should not possible to have 1 week more and begin before interval");
			} else {
				//COMPUTING INTERVAL BEGINING ON NEXT DAY
				Interval interval2 = new Interval(begin, interval1.getEnd());
				Interval result = interval1.overlap(interval2);
				return result;
			}
		} else {
			//COMPUTING INTERVAL BEGINING ON NEXT DAY
			Interval interval2 = new Interval(begin, interval1.getEnd());
			Interval result = interval1.overlap(interval2);
			return result;
		}
	}

	public static Date startDays(Date m) {
		return startDays(new MutableDateTime(m)).toDate();
	}

	public static MutableDateTime startDays(MutableDateTime m) {
		m.dayOfWeek().roundFloor();
		return m;
	}

	public static Date startMins(Date m) {
		return startMins(new MutableDateTime(m)).toDate();
	}

	public static MutableDateTime startMins(MutableDateTime m) {
		m.minuteOfHour().roundFloor();
		return m;
	}

	public static Date startYears(Date m) {
		return startYears(new MutableDateTime(m)).toDate();
	}

	public static MutableDateTime startYears(MutableDateTime m) {
		m.year().roundFloor();
		return m;
	}

	public static MutableDateTime toDatetime(Date date, Time hor) {
		MutableDateTime mut = new MutableDateTime(date);
		return toDatetime(mut, hor);
	}

	public static MutableDateTime toDatetime(DateTime date, Time hor) {
		MutableDateTime mut = new MutableDateTime(date);
		return toDatetime(mut, hor);
	}

	public static MutableDateTime toDatetime(LocalDateTime date, Time hor) {
		MutableDateTime mut = new MutableDateTime(date.toDateTime());
		return toDatetime(mut, hor);
	}

	public static MutableDateTime toDatetime(MutableDateTime date, Time hor) {
		LocalTime horaire = new LocalTime(hor);
		// MINUTES MUST BE BEFORE
		date = new MutableDateTime(date);
		date.setMinuteOfDay(horaire.getMinuteOfHour());
		date.setHourOfDay(horaire.getHourOfDay());
		return date;
	}

	public static Interval toInterval(DateTime date, Time beginH, Time endH) {
		MutableDateTime begin = toDatetime(date, beginH);
		MutableDateTime end = toDatetime(date, endH);
		return new Interval(begin.toInstant(), end.toInstant());
	}

	private DateUtilsMore() {

	}

	public static Time parseTime(String date, String pattern) throws ParseException {
		return new Time(DateUtils.parseDate(date, pattern).getTime());
	}

	public static Date parseDate(String date, String pattern) throws ParseException {
		return DateUtils.parseDate(date, pattern);
	}
}
