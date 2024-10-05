package com.rm.soa.statistics.generators.periods;

import java.util.Date;

import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.DimensionGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
public class DimensionWeekGenerator extends AbstractDimensionPeriodGenerator implements DimensionGenerator<Date> {
	public DimensionWeekGenerator(SoaAppConfig soaAppConfig, DateComputer dateComputer) {
		super(soaAppConfig, dateComputer);
	}

	@Override
	protected void increment(MutableDateTime begin) {
		begin.addWeeks(1);
	}

	@Override
	protected void reset(MutableDateTime begin) {
		dateComputer.startWeeks(begin);
	}

	@Override
	protected int diff(MutableDateTime begin, MutableDateTime end) {
		return Weeks.weeksBetween(begin, end).getWeeks();
	}

}
