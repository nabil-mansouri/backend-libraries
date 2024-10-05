package com.rm.model.discounts.rules.subject;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule_subject_order")
public class DiscountRuleSubjectOrder extends DiscountRuleSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
