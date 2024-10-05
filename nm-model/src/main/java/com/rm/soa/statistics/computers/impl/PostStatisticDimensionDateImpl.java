package com.rm.soa.statistics.computers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.stats.DaoDates;
import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.soa.statistics.computers.StatisticComputer;

/**
 * 
 * @author Nabil
 * 
 */
@Qualifier(StatisticComputer.StatisticComputer)
@Component(StatisticComputer.StatisticComputerPostDate)
public class PostStatisticDimensionDateImpl implements StatisticComputer {
	@Autowired
	private DaoDates daoDates;

	@Transactional
	public void compute(StatisticContextBean context) {
		for (Long id : context.getIdDates()) {
			daoDates.delete(daoDates.load(id));
		}
	}
}
