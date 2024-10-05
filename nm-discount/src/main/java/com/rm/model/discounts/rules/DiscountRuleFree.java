package com.rm.model.discounts.rules;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule_free")
public class DiscountRuleFree extends DiscountRule {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
