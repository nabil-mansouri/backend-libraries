package com.rm.soa.statistics.builders;

import java.util.Map;

import com.nm.app.stats.DimensionValue;
import com.nm.app.stats.MeasureType;
import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;

/**
 * 
 * @author Nabil
 * 
 */
public interface StatisticResultBuilder {
	public StatisticContextBean prepare( StatisticsFilterBean filter) throws NoDimensionGeneratorException, TooMuchDateException;

	public StatisticResultNodeBean pushMeasure(StatisticContextBean context, Map<DimensionValue, Object> dimensions, Map<MeasureType, Object> measures);
}
