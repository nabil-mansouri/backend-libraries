package com.rm.soa.statistics.utils;

import java.util.Date;

import org.joda.time.MutableDateTime;

import com.rm.contract.statistics.constants.DimensionPeriodTransationValue;
import com.rm.contract.statistics.constants.DimensionPeriodValue;
import com.rm.contract.statistics.exceptions.BadPeriodException;

/**
 * 
 * @author Nabil
 * 
 */
public interface DateComputer {
	public void startMinutes(MutableDateTime m);

	public void startHours(MutableDateTime m);

	public void startDays(MutableDateTime m);

	public void startWeeks(MutableDateTime m);

	public void startMonths(MutableDateTime m);

	public void startYears(MutableDateTime m);

	public void endMinutes(MutableDateTime m);

	public void endHours(MutableDateTime m);

	public void endDays(MutableDateTime m);

	public void endWeeks(MutableDateTime m);

	public void endMonths(MutableDateTime m);

	public void endYears(MutableDateTime m);

	public Date add(Date from, DimensionPeriodValue m, int nb) throws BadPeriodException;

	public Date add(Date from, DimensionPeriodTransationValue m, int nb) throws BadPeriodException;

	public Date minus(Date from, DimensionPeriodValue m, int nb) throws BadPeriodException;

	public Date minus(Date from, DimensionPeriodTransationValue m, int nb) throws BadPeriodException;
}
