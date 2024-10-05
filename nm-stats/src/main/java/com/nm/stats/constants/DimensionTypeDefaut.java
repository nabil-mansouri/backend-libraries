package com.nm.stats.constants;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public enum DimensionTypeDefaut implements DimensionType {
	Period;

	public String getSqlName() {
		return "period";
	}
}
