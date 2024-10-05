package com.rm.soa.statistics.generators.periods;

import java.util.Date;

import org.joda.time.Hours;
import org.joda.time.MutableDateTime;

import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.DimensionGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
public class DimensionHourGenerator extends AbstractDimensionPeriodGenerator implements DimensionGenerator<Date> {
	public DimensionHourGenerator(SoaAppConfig soaAppConfig, DateComputer dateComputer) {
		super(soaAppConfig, dateComputer);
	}

	@Override
	protected void increment(MutableDateTime begin) {
		begin.addHours(1);
	}

	@Override
	protected void reset(MutableDateTime begin) {
		dateComputer.startHours(begin);
	}

	@Override
	protected int diff(MutableDateTime begin, MutableDateTime end) {
		return Hours.hoursBetween(begin, end).getHours();
	}

}
