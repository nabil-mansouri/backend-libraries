package com.rm.contract.statistics.constants;

import com.nm.app.stats.DimensionType;
import com.nm.app.stats.DimensionValue;

/**
 * 
 * @author Nabil
 * 
 */
public enum DimensionPeriodTransationValue implements DimensionValue {
	Minute, Hour, Day, Week, Month, Year;
	public String getFullName() {
		return getType() + name();
	}

	public DimensionType getType() {
		return DimensionType.Period;
	}
}
