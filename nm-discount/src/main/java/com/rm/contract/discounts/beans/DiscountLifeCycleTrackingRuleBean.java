package com.rm.contract.discounts.beans;

import java.io.Serializable;

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
public class DiscountLifeCycleTrackingRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private boolean enable;
	//
	private boolean isLimitedPeriod;
	private boolean isLimitedCount;
	//
	private PeriodType period;
	private double periodNumber;
	private long countMax;
	//
	private boolean isPeriodic;
	private PeriodType everyPeriod;
	private double everyPeriodNumber;

	//
	public PeriodType getEveryPeriod() {
		return everyPeriod;
	}

	public DiscountLifeCycleTrackingRuleBean setEveryPeriod(PeriodType everyPeriod) {
		this.everyPeriod = everyPeriod;
		return this;
	}

	public double getEveryPeriodNumber() {
		return everyPeriodNumber;
	}

	public DiscountLifeCycleTrackingRuleBean setEveryPeriodNumber(double everyPeriodNumber) {
		this.everyPeriodNumber = everyPeriodNumber;
		return this;
	}

	public boolean isPeriodic() {
		return isPeriodic;
	}

	public DiscountLifeCycleTrackingRuleBean setPeriodic(boolean isPeriodic) {
		this.isPeriodic = isPeriodic;
		return this;
	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isLimitedPeriod() {
		return isLimitedPeriod;
	}

	public DiscountLifeCycleTrackingRuleBean setLimitedPeriod(boolean isLimitedPeriod) {
		this.isLimitedPeriod = isLimitedPeriod;
		return this;
	}

	public boolean isLimitedCount() {
		return isLimitedCount;
	}

	public DiscountLifeCycleTrackingRuleBean setLimitedCount(boolean isLimitedCount) {
		this.isLimitedCount = isLimitedCount;
		return this;
	}

	public PeriodType getPeriod() {
		return period;
	}

	public DiscountLifeCycleTrackingRuleBean setPeriod(PeriodType period) {
		this.period = period;
		return this;
	}

	public double getPeriodNumber() {
		return periodNumber;
	}

	public DiscountLifeCycleTrackingRuleBean setPeriodNumber(double periodNumber) {
		this.periodNumber = periodNumber;
		return this;
	}

	public long getCountMax() {
		return countMax;
	}

	public DiscountLifeCycleTrackingRuleBean setCountMax(long countMax) {
		this.countMax = countMax;
		return this;
	}

	public DiscountLifeCycleTrackingRuleBean setEnable(boolean enable) {
		this.enable = enable;
		return this;
	}

}
