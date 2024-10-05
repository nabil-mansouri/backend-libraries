package com.rm.soa.statistics;

import java.util.Collection;

import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.statistics.exceptions.BadStatisticFilterException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaStatistics {

	public Collection<StatisticResultNodeBean> productStat(StatisticsFilterBean filter) throws NoDimensionGeneratorException,
			BadStatisticFilterException, TooMuchDateException;

	public Collection<StatisticResultNodeBean> orderStat(StatisticsFilterBean filter) throws NoDimensionGeneratorException,
			BadStatisticFilterException, TooMuchDateException;
}
