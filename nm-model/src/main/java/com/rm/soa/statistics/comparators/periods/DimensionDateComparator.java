package com.rm.soa.statistics.comparators.periods;

import java.sql.Timestamp;
import java.util.Date;

import com.rm.soa.statistics.comparators.DimensionComparator;

/**
 * 
 * @author Nabil
 * 
 */
public class DimensionDateComparator implements DimensionComparator<Timestamp, Date> {

	public boolean equals(Timestamp type1, Date type2) {
		return type1 != null && type2 != null && type1.getTime() == (type2.getTime());
	}

}
