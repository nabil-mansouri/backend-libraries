package com.rm.soa.statistics.generators.periods;

import java.util.Date;

import org.joda.time.MutableDateTime;
import org.joda.time.Years;

import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.DimensionGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
public class DimensionYearGenerator extends AbstractDimensionPeriodGenerator implements DimensionGenerator<Date> {
	public DimensionYearGenerator(SoaAppConfig soaAppConfig, DateComputer dateComputer) {
		super(soaAppConfig, dateComputer);
	}

	@Override
	protected void increment(MutableDateTime begin) {
		begin.addYears(1);
	}

	@Override
	protected void reset(MutableDateTime begin) {
		dateComputer.startYears(begin);
	}

	@Override
	protected int diff(MutableDateTime begin, MutableDateTime end) {
		return Years.yearsBetween(begin, end).getYears();
	}

}
