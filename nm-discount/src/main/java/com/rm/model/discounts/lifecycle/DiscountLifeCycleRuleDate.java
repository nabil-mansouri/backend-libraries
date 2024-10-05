package com.rm.model.discounts.lifecycle;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import com.rm.utils.dates.DateUtilsMore;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_lifecycle_rule_date")
public class DiscountLifeCycleRuleDate extends DiscountLifeCycleRule implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Date fromDate;
	private Date toDate;
	private boolean exact;

	public boolean isExact() {
		return exact;
	}

	public void setExact(boolean exact) {
		this.exact = exact;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date from) {
		if (from != null) {
			from = DateUtilsMore.startMins(from);
		}
		this.fromDate = from;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date to) {
		if (to != null) {
			to = DateUtilsMore.endMins(to);
		}
		this.toDate = to;
	}

	@AssertTrue
	protected boolean assertNotNull() {
		return this.fromDate != null || this.toDate != null;
	}
}
