package com.rm.soa.statistics.computers.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.stats.DaoDates;
import com.nm.app.stats.Dates;
import com.nm.app.stats.DimensionType;
import com.nm.app.stats.DimensionValue;
import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.statistics.computers.StatisticComputer;
import com.rm.soa.statistics.generators.DimensionGeneratorFactory;

/**
 * 
 * @author Nabil
 * 
 */
@Qualifier(StatisticComputer.StatisticComputer)
@Component(StatisticComputer.StatisticComputerPreDate)
public class PreStatisticDimensionDateImpl implements StatisticComputer {
	@Autowired
	private DimensionGeneratorFactory generatorFactory;
	@Autowired
	private DaoDates daoDates;

	@Transactional
	public void compute(StatisticContextBean context) throws TooMuchDateException {
		DimensionValue value = context.getFilter().getDimensions().get(DimensionType.Period);
		Collection<Dates> dates = generatorFactory.generateDateModel(value, context.getFilter());
		for (Dates d : dates) {
			daoDates.insert(d);
			context.getIdDates().add(d.getId());
		}
		context.getFilter().getIdDates().addAll(context.getIdDates());
		daoDates.flush();
	}
}
