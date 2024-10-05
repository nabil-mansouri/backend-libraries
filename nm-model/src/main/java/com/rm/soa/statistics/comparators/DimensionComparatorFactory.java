package com.rm.soa.statistics.comparators;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.nm.app.stats.DimensionType;
import com.rm.contract.statistics.exceptions.NoDimensionComparatorException;
import com.rm.soa.statistics.comparators.periods.DimensionDateComparator;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DimensionComparatorFactory {
	public boolean generate(DimensionType type, Object sqlVal, Object javaVal) throws NoDimensionComparatorException {
		switch (type) {
		case Period: {
			return buildDateComparator().equals((Timestamp) sqlVal, (Date) javaVal);
		}
		case PeriodTransaction:
			break;
		case Product:
			break;
		default:
			break;
		}
		throw new NoDimensionComparatorException();
	}

	public DimensionComparator<Timestamp, Date> buildDateComparator() {
		return new DimensionDateComparator();
	}

}
