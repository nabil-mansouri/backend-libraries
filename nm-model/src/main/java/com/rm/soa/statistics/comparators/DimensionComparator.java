package com.rm.soa.statistics.comparators;


/**
 * 
 * @author Nabil
 * 
 */
public interface DimensionComparator<SQL,JAVA> {

	public boolean equals(SQL type1,JAVA type2);
}
