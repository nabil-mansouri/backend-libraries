package com.rm.contract.discounts.beans;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountLifeCycleRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private boolean enable;
	//
	private Date from;
	//
	private boolean isDateLimited;
	private boolean isCountLimited;
	private boolean isPeriodLimited;
	//
	private boolean hasToDate;
	private boolean isExactDay;
	private Date to;
	private PeriodType period;
	private double periodNumber;
	private long countMax;

	//

	public boolean isEnable() {
		return enable;
	}

	public boolean isCountLimited() {
		return isCountLimited;
	}

	public boolean isDateLimited() {
		return isDateLimited;
	}

	public boolean isExactDay() {
		return isExactDay;
	}

	public DiscountLifeCycleRuleBean setExactDay(boolean isExactDay) {
		this.isExactDay = isExactDay;
		return this;
	}

	public long getCountMax() {
		return countMax;
	}

	public boolean isPeriodLimited() {
		return isPeriodLimited;
	}

	public DiscountLifeCycleRuleBean setPeriodLimited(boolean isPeriodLimited) {
		this.isPeriodLimited = isPeriodLimited;
		return this;
	}

	public DiscountLifeCycleRuleBean setCountMax(long countMax) {
		this.countMax = countMax;
		return this;
	}

	public DiscountLifeCycleRuleBean setEnable(boolean enable) {
		this.enable = enable;
		return this;
	}

	public Date getFrom() {
		return from;
	}

	public DiscountLifeCycleRuleBean setFrom(Date from) {
		this.from = from;
		return this;
	}

	public DiscountLifeCycleRuleBean setCountLimited(boolean isCountLimited) {
		this.isCountLimited = isCountLimited;
		return this;
	}

	public DiscountLifeCycleRuleBean setDateLimited(boolean isDateLimited) {
		this.isDateLimited = isDateLimited;
		return this;
	}

	public boolean isHasToDate() {
		return hasToDate;
	}

	public DiscountLifeCycleRuleBean setHasToDate(boolean hasToDate) {
		this.hasToDate = hasToDate;
		return this;
	}

	public Date getTo() {
		return to;
	}

	public DiscountLifeCycleRuleBean setTo(Date to) {
		this.to = to;
		return this;
	}

	public PeriodType getPeriod() {
		return period;
	}

	public DiscountLifeCycleRuleBean setPeriod(PeriodType period) {
		this.period = period;
		return this;
	}

	public double getPeriodNumber() {
		return periodNumber;
	}

	public DiscountLifeCycleRuleBean setPeriodNumber(double periodNumber) {
		this.periodNumber = periodNumber;
		return this;
	}

}
