package com.rm.soa.statistics.utils;

import java.util.Date;

import org.joda.time.MutableDateTime;

import com.rm.contract.statistics.constants.DimensionPeriodTransationValue;
import com.rm.contract.statistics.constants.DimensionPeriodValue;
import com.rm.contract.statistics.exceptions.BadPeriodException;
import com.rm.utils.dates.DateUtilsMore;

/**
 * 
 * @author Nabil
 * 
 */
@org.springframework.stereotype.Component
public class DateComputerImpl implements DateComputer {

	public void startMinutes(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMinimumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMinimumValue());
	}

	public void startHours(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMinimumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMinimumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMinimumValue());
	}

	public void startDays(MutableDateTime m) {
		DateUtilsMore.startDays(m);
	}

	public void startWeeks(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMinimumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMinimumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMinimumValue());
		m.setHourOfDay(m.hourOfDay().getMinimumValue());
		m.setDayOfWeek(m.dayOfWeek().getMinimumValue());
	}

	public void startMonths(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMinimumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMinimumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMinimumValue());
		m.setHourOfDay(m.hourOfDay().getMinimumValue());
		m.setDayOfMonth(m.dayOfMonth().getMinimumValue());
	}

	public void startYears(MutableDateTime m) {
		DateUtilsMore.startYears(m);
	}

	public void endMinutes(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMaximumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMaximumValue());
	}

	public void endHours(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMaximumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMaximumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMaximumValue());
	}

	public void endDays(MutableDateTime m) {
		DateUtilsMore.endDays(m);
	}

	public void endWeeks(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMaximumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMaximumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMaximumValue());
		m.setHourOfDay(m.hourOfDay().getMaximumValue());
		m.setDayOfWeek(m.dayOfWeek().getMaximumValue());
	}

	public void endMonths(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMaximumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMaximumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMaximumValue());
		m.setHourOfDay(m.hourOfDay().getMaximumValue());
		m.setDayOfMonth(m.dayOfMonth().getMaximumValue());
	}

	public void endYears(MutableDateTime m) {
		m.setMillisOfSecond(m.millisOfSecond().getMaximumValue());
		m.setSecondOfMinute(m.secondOfMinute().getMaximumValue());
		m.setMinuteOfHour(m.minuteOfHour().getMaximumValue());
		m.setHourOfDay(m.hourOfDay().getMaximumValue());
		m.setDayOfMonth(m.dayOfMonth().getMaximumValue());
		m.setMonthOfYear(m.monthOfYear().getMaximumValue());
	}

	public Date add(Date from, DimensionPeriodTransationValue m, int nb) throws BadPeriodException {
		switch (m) {
		case Day: {
			MutableDateTime to = new MutableDateTime(from);
			to.addDays(nb);
			return to.toDate();
		}
		case Hour: {
			MutableDateTime to = new MutableDateTime(from);
			to.addHours(nb);
			return to.toDate();
		}
		case Minute: {
			MutableDateTime to = new MutableDateTime(from);
			to.addMinutes(nb);
			return to.toDate();
		}
		case Month: {
			MutableDateTime to = new MutableDateTime(from);
			to.addMonths(nb);
			return to.toDate();
		}
		case Week: {
			MutableDateTime to = new MutableDateTime(from);
			to.addWeeks(nb);
			return to.toDate();
		}
		case Year: {
			MutableDateTime to = new MutableDateTime(from);
			to.addYears(nb);
			return to.toDate();
		}
		default:
			throw new BadPeriodException();
		}
	}

	public Date add(Date from, DimensionPeriodValue m, int nb) throws BadPeriodException {
		switch (m) {
		case Day: {
			MutableDateTime to = new MutableDateTime(from);
			to.addDays(nb);
			return to.toDate();
		}
		case Hour: {
			MutableDateTime to = new MutableDateTime(from);
			to.addHours(nb);
			return to.toDate();
		}
		case Minute: {
			MutableDateTime to = new MutableDateTime(from);
			to.addMinutes(nb);
			return to.toDate();
		}
		case Month: {
			MutableDateTime to = new MutableDateTime(from);
			to.addMonths(nb);
			return to.toDate();
		}
		case Week: {
			MutableDateTime to = new MutableDateTime(from);
			to.addWeeks(nb);
			return to.toDate();
		}
		case Year: {
			MutableDateTime to = new MutableDateTime(from);
			to.addYears(nb);
			return to.toDate();
		}
		default:
			throw new BadPeriodException();
		}
	}

	public Date minus(Date from, DimensionPeriodTransationValue m, int nb) throws BadPeriodException {
		return add(from, m, -nb);
	}

	public Date minus(Date from, DimensionPeriodValue m, int nb) throws BadPeriodException {
		return add(from, m, -nb);
	}
}
