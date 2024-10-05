package com.rm.soa.statistics.generators.periods;

import java.util.Date;

import org.joda.time.Minutes;
import org.joda.time.MutableDateTime;

import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.DimensionGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
public class DimensionMinutesGenerator extends AbstractDimensionPeriodGenerator implements DimensionGenerator<Date> {
	public DimensionMinutesGenerator(SoaAppConfig soaAppConfig, DateComputer dateComputer) {
		super(soaAppConfig, dateComputer);
	}

	@Override
	protected void increment(MutableDateTime begin) {
		begin.addMinutes(1);
	}

	@Override
	protected void reset(MutableDateTime begin) {
		dateComputer.startMinutes(begin);
	}

	@Override
	protected int diff(MutableDateTime begin, MutableDateTime end) {
		return Minutes.minutesBetween(begin, end).getMinutes();
	}

}
