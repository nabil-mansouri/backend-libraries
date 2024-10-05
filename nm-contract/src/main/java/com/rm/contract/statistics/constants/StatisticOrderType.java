package com.rm.contract.statistics.constants;

/**
 * 
 * @author Nabil
 * 
 */
public enum StatisticOrderType {
	Minutes, //
	Hours, //
	Days, //
	Weeks, //
	Months, //
	Years, //
	TransactionMinutes, //
	TransactionHours, //
	TransactionDays, //
	TransactionWeeks, //
	TransactionMonths, //
	TransactionYears, //
	OrderTotalPrice, //
	OrderTotalCount, //
	ProductTotalPrice, //
	ProductTotalCount;
	public static StatisticOrderType get(DimensionPeriodValue dim) {
		switch (dim) {
		case Day:
			return Days;
		case Hour:
			return Hours;
		case Minute:
			return Minutes;
		case Month:
			return Months;
		case Week:
			return Weeks;
		case Year:
			return Years;
		}
		return null;
	}

	public static StatisticOrderType get(DimensionPeriodTransationValue dim) {
		switch (dim) {
		case Day:
			return Days;
		case Hour:
			return Hours;
		case Minute:
			return Minutes;
		case Month:
			return Months;
		case Week:
			return Weeks;
		case Year:
			return Years;
		}
		return null;
	}
}
