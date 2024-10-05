package com.rm.soa.statistics.computers.impl;

import org.joda.time.MutableDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nm.app.stats.DimensionType;
import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.contract.statistics.constants.DimensionPeriodTransationValue;
import com.rm.contract.statistics.constants.DimensionPeriodValue;
import com.rm.soa.statistics.computers.StatisticComputer;
import com.rm.soa.statistics.exceptions.BadStatisticFilterException;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
@Qualifier(StatisticComputer.StatisticComputer)
@Component(StatisticComputer.StatisticComputerLimitDate)
public class PreStatisticLimitDateImpl implements StatisticComputer {
	@Autowired
	private DateComputer dateSetter;

	public void compute(StatisticContextBean context) throws BadStatisticFilterException {
		boolean hasBegin = context.getFilter().getFrom() != null;
		boolean hasEnd = context.getFilter().getTo() != null;
		if (context.getFilter().getDimensions().containsKey(DimensionType.Period)) {
			if (hasBegin && hasEnd) {
				MutableDateTime from = new MutableDateTime(context.getFilter().getFrom());
				MutableDateTime to = new MutableDateTime(context.getFilter().getTo());
				DimensionPeriodValue val = (DimensionPeriodValue) context.getFilter().getDimensions().get(DimensionType.Period);
				switch (val) {
				case Minute:
					dateSetter.startMinutes(from);
					dateSetter.endMinutes(to);
					break;
				case Hour:
					dateSetter.startHours(from);
					dateSetter.endHours(to);
					break;
				case Day:
					dateSetter.startDays(from);
					dateSetter.endDays(to);
					break;
				case Month:
					dateSetter.startMonths(from);
					dateSetter.endMonths(to);
					break;
				case Week:
					dateSetter.startWeeks(from);
					dateSetter.endWeeks(to);
					break;
				case Year:
					dateSetter.startYears(from);
					dateSetter.endYears(to);
					break;
				}
				context.getFilter().setFrom(from.toDate());
				context.getFilter().setTo(to.toDate());
			} else {
				// Must limit in time because of performance
				throw new BadStatisticFilterException("Need filter date");
			}
		}
		//
		if (context.getFilter().getDimensions().containsKey(DimensionType.PeriodTransaction)) {
			if (hasBegin) {
				MutableDateTime from = new MutableDateTime(context.getFilter().getFrom());
				DimensionPeriodTransationValue val = (DimensionPeriodTransationValue) context.getFilter().getDimensions()
						.get(DimensionType.PeriodTransaction);
				switch (val) {
				case Minute:
					dateSetter.startMinutes(from);
					break;
				case Hour:
					dateSetter.startHours(from);
					break;
				case Day:
					dateSetter.startDays(from);
					break;
				case Month:
					dateSetter.startMonths(from);
					break;
				case Week:
					dateSetter.startWeeks(from);
					break;
				case Year:
					dateSetter.startYears(from);
					break;
				}
				context.getFilter().setFrom(from.toDate());
			}
			if (hasEnd) {
				MutableDateTime to = new MutableDateTime(context.getFilter().getTo());
				DimensionPeriodTransationValue val = (DimensionPeriodTransationValue) context.getFilter().getDimensions()
						.get(DimensionType.PeriodTransaction);
				switch (val) {
				case Minute:
					dateSetter.endMinutes(to);
					break;
				case Hour:
					dateSetter.endHours(to);
					break;
				case Day:
					dateSetter.endDays(to);
					break;
				case Month:
					dateSetter.endMonths(to);
					break;
				case Week:
					dateSetter.endWeeks(to);
					break;
				case Year:
					dateSetter.endYears(to);
					break;
				}
				context.getFilter().setTo(to.toDate());
			}
		}
	}
}
