package com.rm.soa.statistics.generators.periods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.MutableDateTime;

import com.rm.contract.commons.constants.AppConfigKey;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.DimensionGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
public abstract class AbstractDimensionPeriodGenerator implements DimensionGenerator<Date> {

	protected final SoaAppConfig soaAppConfig;
	protected final DateComputer dateComputer;

	public AbstractDimensionPeriodGenerator(SoaAppConfig soaAppConfig, DateComputer dateComputer) {
		super();
		this.soaAppConfig = soaAppConfig;
		this.dateComputer = dateComputer;
	}

	public final Collection<Date> generate(StatisticsFilterBean filter) throws TooMuchDateException {
		Collection<Date> dates = new ArrayList<Date>();
		if (filter.getFrom() != null && filter.getTo() != null) {
			MutableDateTime begin = new MutableDateTime(filter.getFrom());
			MutableDateTime end = new MutableDateTime(filter.getTo());
			int diff = diff(begin, end);
			if (diff > soaAppConfig.getInt(AppConfigKey.StatisticDateMaxGeneration)) {
				throw new TooMuchDateException();
			}
			while (!begin.isAfter(end)) {
				reset(begin);
				dates.add(begin.toDate());
				increment(begin);
			}
		}
		return dates;
	}

	public final Collection<Date> generateLarge(StatisticsFilterBean filter) throws TooMuchDateException {
		Collection<Date> dates = new ArrayList<Date>();
		if (filter.getFrom() != null && filter.getTo() != null) {
			MutableDateTime begin = new MutableDateTime(filter.getFrom());
			MutableDateTime end = new MutableDateTime(filter.getTo());
			//
			begin.addDays(-1);
			end.addDays(1);
			int diff = diff(begin, end);
			if (diff > soaAppConfig.getInt(AppConfigKey.StatisticDateMaxGeneration)) {
				throw new TooMuchDateException();
			}
			while (!begin.isAfter(end)) {
				reset(begin);
				dates.add(begin.toDate());
				increment(begin);
			}
		}
		return dates;
	}

	protected abstract void increment(MutableDateTime date);

	protected abstract void reset(MutableDateTime date);

	protected abstract int diff(MutableDateTime begin, MutableDateTime end);
}
