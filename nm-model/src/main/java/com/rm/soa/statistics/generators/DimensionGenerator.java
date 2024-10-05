package com.rm.soa.statistics.generators;

import java.util.Collection;

import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.TooMuchDateException;

/**
 * 
 * @author Nabil
 * 
 */
public interface DimensionGenerator<T> {

	public Collection<T> generate(StatisticsFilterBean filter) throws TooMuchDateException;

	public Collection<T> generateLarge(StatisticsFilterBean filter) throws TooMuchDateException;
}
