package com.rm.model.discounts.rules;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule_replace_price")
public class DiscountRuleReplacePrice extends DiscountRule {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Price> prices = new ArrayList<Price>();

	public Collection<Price> getPrices() {
		return prices;
	}

	public void setPrices(Collection<Price> prices) {
		this.prices = prices;
	}

}
