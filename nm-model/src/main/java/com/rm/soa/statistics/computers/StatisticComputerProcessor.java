package com.rm.soa.statistics.computers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nm.app.stats.DimensionType;
import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.statistics.exceptions.BadStatisticFilterException;

/**
 * 
 * @author Nabil
 * 
 */
@Component()
public class StatisticComputerProcessor {

	@Autowired
	@Qualifier(StatisticComputer.StatisticComputer)
	private Map<String, StatisticComputer> strategies = new HashMap<String, StatisticComputer>();

	public StatisticContextBean processProduct(StatisticsFilterBean filter) throws NoDimensionGeneratorException, BadStatisticFilterException, TooMuchDateException {
		return process(filter, StatisticComputer.StatisticProductComputer);
	}

	public StatisticContextBean processOrder(StatisticsFilterBean filter) throws NoDimensionGeneratorException, BadStatisticFilterException, TooMuchDateException {
		return process(filter, StatisticComputer.StatisticOrderComputer);
	}

	/**
	 * No transaction here (inside processors)
	 * 
	 * @param filter
	 * @return
	 * @throws NoDimensionGeneratorException
	 * @throws BadStatisticFilterException
	 * @throws TooMuchDateException 
	 */
	protected StatisticContextBean process(StatisticsFilterBean filter, String main) throws NoDimensionGeneratorException,
			BadStatisticFilterException, TooMuchDateException {
		Collection<String> strategies = new ArrayList<String>();
		StatisticContextBean context = new StatisticContextBean(filter);
		if (filter.getDimensions().containsKey(DimensionType.Period)) {
			strategies.add(StatisticComputer.StatisticComputerPreDate);
		}
		if (filter.getDimensions().containsKey(DimensionType.Period) || filter.getDimensions().containsKey(DimensionType.PeriodTransaction)) {
			strategies.add(StatisticComputer.StatisticComputerLimitDate);
		}
		strategies.add(main);
		if (filter.getDimensions().containsKey(DimensionType.Period)) {
			strategies.add(StatisticComputer.StatisticComputerPostDate);
		}
		for (String st : strategies) {
			this.strategies.get(st).compute(context);
		}
		return context;
	}
}
