package com.rm.soa.statistics.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nm.app.stats.Dates;
import com.nm.app.stats.DimensionType;
import com.nm.app.stats.DimensionValue;
import com.rm.contract.statistics.beans.DimensionValueBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.constants.DimensionPeriodValue;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.commons.SoaAppConfig;
import com.rm.soa.statistics.generators.periods.DimensionDayGenerator;
import com.rm.soa.statistics.generators.periods.DimensionHourGenerator;
import com.rm.soa.statistics.generators.periods.DimensionMinutesGenerator;
import com.rm.soa.statistics.generators.periods.DimensionMonthGenerator;
import com.rm.soa.statistics.generators.periods.DimensionWeekGenerator;
import com.rm.soa.statistics.generators.periods.DimensionYearGenerator;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DimensionGeneratorFactory {
	@Autowired
	private SoaAppConfig soaAppConfig;
	@Autowired
	private DateComputer dateComputer;

	public Collection<?> generate(DimensionType type, DimensionValue value, StatisticsFilterBean filter) throws NoDimensionGeneratorException,
			TooMuchDateException {
		switch (type) {
		case Period: {
			DimensionPeriodValue valueP = (DimensionPeriodValue) value;
			switch (valueP) {
			case Minute:
				return buildMinutesGenerator().generate(filter);
			case Hour:
				return buildHoursGenerator().generate(filter);
			case Day:
				return buildDaysGenerator().generate(filter);
			case Month:
				return buildMonthsGenerator().generate(filter);
			case Week:
				return buildWeeksGenerator().generate(filter);
			case Year:
				return buildYearsGenerator().generate(filter);
			}
			break;
		}
		case PeriodTransaction:
			break;
		case Product:
			break;
		default:
			break;
		}
		throw new NoDimensionGeneratorException();
	}

	public Collection<Date> generateDate(DimensionValue value, StatisticsFilterBean filter) throws NoDimensionGeneratorException,
			TooMuchDateException {
		if (!(value instanceof DimensionPeriodValue)) {
			throw new NoDimensionGeneratorException();
		}
		DimensionPeriodValue valueP = (DimensionPeriodValue) value;
		switch (valueP) {
		case Minute:
			return buildMinutesGenerator().generate(filter);
		case Hour:
			return buildHoursGenerator().generate(filter);
		case Day:
			return buildDaysGenerator().generate(filter);
		case Month:
			return buildMonthsGenerator().generate(filter);
		case Week:
			return buildWeeksGenerator().generate(filter);
		case Year:
			return buildYearsGenerator().generate(filter);
		}
		throw new NoDimensionGeneratorException();
	}

	public List<DimensionValueBean> generateWithWrapper(DimensionType type, DimensionValue value, StatisticsFilterBean filter)
			throws NoDimensionGeneratorException, TooMuchDateException {
		List<DimensionValueBean> beans = new ArrayList<DimensionValueBean>();
		Collection<?> values = generate(type, value, filter);
		for (Object val : values) {
			DimensionValueBean bean = new DimensionValueBean();
			bean.setDimensionValue(value);
			bean.setType(type);
			bean.setValue(val);
			beans.add(bean);
		}
		return beans;
	}

	public Set<DimensionValueBean> generateOrderedSetWithWrapper(DimensionType type, DimensionValue value, StatisticsFilterBean filter)
			throws NoDimensionGeneratorException, TooMuchDateException {
		Set<DimensionValueBean> beans = new LinkedHashSet<DimensionValueBean>();
		Collection<?> values = generate(type, value, filter);
		for (Object val : values) {
			DimensionValueBean bean = new DimensionValueBean();
			bean.setDimensionValue(value);
			bean.setType(type);
			bean.setValue(val);
			beans.add(bean);
		}
		return beans;
	}

	public Collection<Dates> generateDateModel(DimensionValue value, StatisticsFilterBean filter) throws TooMuchDateException {
		Collection<Dates> dates = new ArrayList<Dates>();
		try {
			Collection<Date> values = generateDate(value, filter);
			for (Date d : values) {
				Dates date = new Dates();
				date.setDate(d);
				dates.add(date);
			}
		} catch (NoDimensionGeneratorException e) {
			e.printStackTrace();
		}

		return dates;
	}

	public DimensionGenerator<Date> buildMinutesGenerator() {
		return new DimensionMinutesGenerator(soaAppConfig, dateComputer);
	}

	public DimensionGenerator<Date> buildHoursGenerator() {
		return new DimensionHourGenerator(soaAppConfig, dateComputer);
	}

	public DimensionGenerator<Date> buildDaysGenerator() {
		return new DimensionDayGenerator(soaAppConfig, dateComputer);
	}

	public DimensionGenerator<Date> buildWeeksGenerator() {
		return new DimensionWeekGenerator(soaAppConfig, dateComputer);
	}

	public DimensionGenerator<Date> buildMonthsGenerator() {
		return new DimensionMonthGenerator(soaAppConfig, dateComputer);
	}

	public DimensionGenerator<Date> buildYearsGenerator() {
		return new DimensionYearGenerator(soaAppConfig, dateComputer);
	}
}
