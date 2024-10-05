package com.nm.plannings.constants;

import org.joda.time.DateTimeConstants;

import com.nm.plannings.InvalidPlanningDaysException;

/**
 * 
 * @author Nabil
 *
 */
public enum PlanningDays {
	AllDays() {
		@Override
		public int getJodaDay() throws InvalidPlanningDaysException {
			throw new InvalidPlanningDaysException("Cannot convert all days to jodatimeconstant");
		}
	},
	Monday() {
		@Override
		public int getJodaDay() {
			return DateTimeConstants.MONDAY;
		}
	},
	Tuesday() {
		@Override
		public int getJodaDay() {
			return DateTimeConstants.TUESDAY;
		}
	},
	Wednesday() {
		@Override
		public int getJodaDay() {
			return DateTimeConstants.WEDNESDAY;
		}
	},
	Thirsday() {
		@Override
		public int getJodaDay() {
			return DateTimeConstants.THURSDAY;
		}
	},
	Friday() {
		@Override
		public int getJodaDay() {
			return DateTimeConstants.FRIDAY;
		}
	},
	Saturday() {
		@Override
		public int getJodaDay() {
			return DateTimeConstants.SATURDAY;
		}
	},
	Sunday() {
		@Override
		public int getJodaDay() {
			return DateTimeConstants.SUNDAY;
		}
	};
	public abstract int getJodaDay() throws InvalidPlanningDaysException;

}
