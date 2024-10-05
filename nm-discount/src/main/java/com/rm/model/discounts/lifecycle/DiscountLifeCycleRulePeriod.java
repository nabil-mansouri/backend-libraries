package com.rm.model.discounts.lifecycle;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.rm.utils.dates.PeriodType;
import com.rm.utils.dates.PeriodUtils;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_lifecycle_rule_period")
public class DiscountLifeCycleRulePeriod extends DiscountLifeCycleRule implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PeriodType period;
	@Column(nullable = false)
	private Double duration;
	@Formula("(NOW() - (duration || periodsql)::interval)")
	private Date limit;

	@Access(AccessType.PROPERTY)
	public String getPeriodSql() {
		return PeriodUtils.toPgSQL(period);
	}

	public void setPeriodSql(PeriodType p) {
	}

	public PeriodType getPeriod() {
		return period;
	}

	public void setPeriod(PeriodType period) {
		this.period = period;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

}
