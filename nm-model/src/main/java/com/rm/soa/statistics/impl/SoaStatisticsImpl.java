package com.rm.soa.statistics.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.statistics.SoaStatistics;
import com.rm.soa.statistics.computers.StatisticComputerProcessor;
import com.rm.soa.statistics.exceptions.BadStatisticFilterException;

/**
 * 
 * @author Nabil
 * 
 */
@Service
public class SoaStatisticsImpl implements SoaStatistics {
	@Autowired
	private StatisticComputerProcessor processor;
	/**
	 * * No transaction here (inside processors)
	 * @throws BadStatisticFilterException 
	 * @throws TooMuchDateException 
	 */
	public Collection<StatisticResultNodeBean> productStat(StatisticsFilterBean filter) throws NoDimensionGeneratorException, BadStatisticFilterException, TooMuchDateException {
		return processor.processProduct(filter).getResults();
	}
	
	/**
	 * * No transaction here (inside processors)
	 * @throws BadStatisticFilterException 
	 * @throws TooMuchDateException 
	 */
	public Collection<StatisticResultNodeBean> orderStat(StatisticsFilterBean filter) throws NoDimensionGeneratorException, BadStatisticFilterException, TooMuchDateException {
		return processor.processOrder(filter).getResults();
	}

}
