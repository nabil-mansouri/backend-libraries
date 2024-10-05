package com.rm.soa.statistics.computers;

import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.statistics.exceptions.BadStatisticFilterException;

/**
 * 
 * @author Nabil
 * 
 */
public interface StatisticComputer {
	public static final String StatisticComputer = "StatisticComputer";
	public static final String StatisticComputerLimitDate = "StatisticComputerLimitDate";
	public static final String StatisticComputerPreDate = "StatisticComputerPreDate";
	public static final String StatisticComputerPostDate = "StatisticComputerPostDate";
	public static final String StatisticProductComputer = "StatisticProductComputer";
	public static final String StatisticOrderComputer = "StatisticOrderComputer";

	public void compute(StatisticContextBean context) throws NoDimensionGeneratorException, BadStatisticFilterException, TooMuchDateException;
}
