package com.rm.soa.statistics.generators.periods;

import java.util.Date;

import org.joda.time.Months;
import org.joda.time.MutableDateTime;

import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.DimensionGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
public class DimensionMonthGenerator extends AbstractDimensionPeriodGenerator implements DimensionGenerator<Date> {
	public DimensionMonthGenerator(SoaAppConfig soaAppConfig, DateComputer dateComputer) {
		super(soaAppConfig, dateComputer);
	}

	@Override
	protected void increment(MutableDateTime begin) {
		begin.addMonths(1);
	}

	@Override
	protected void reset(MutableDateTime begin) {
		dateComputer.startMonths(begin);
	}

	@Override
	protected int diff(MutableDateTime begin, MutableDateTime end) {
		return Months.monthsBetween(begin, end).getMonths();
	}

}
