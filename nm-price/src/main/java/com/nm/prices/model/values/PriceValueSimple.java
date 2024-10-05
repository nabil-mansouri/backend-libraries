package com.nm.prices.model.values;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nm.prices.dtos.constants.PriceValueEnum;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_value_simple")
public class PriceValueSimple extends PriceValue implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	protected Double value;

	public PriceValueSimple() {
		setType(PriceValueEnum.Simple);
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
