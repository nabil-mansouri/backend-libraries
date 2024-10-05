package com.rm.soa.statistics.generators.periods;

import java.util.Date;

import org.joda.time.Days;
import org.joda.time.MutableDateTime;

import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.DimensionGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
public class DimensionDayGenerator extends AbstractDimensionPeriodGenerator implements DimensionGenerator<Date> {

	public DimensionDayGenerator(SoaAppConfig soaAppConfig, DateComputer dateComputer) {
		super(soaAppConfig, dateComputer);
	}

	@Override
	protected void increment(MutableDateTime begin) {
		begin.addDays(1);
	}

	@Override
	protected void reset(MutableDateTime begin) {
		dateComputer.startDays(begin);
	}

	@Override
	protected int diff(MutableDateTime begin, MutableDateTime end) {
		return Days.daysBetween(begin, end).getDays();
	}

}
