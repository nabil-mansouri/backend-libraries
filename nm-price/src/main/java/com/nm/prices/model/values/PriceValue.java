package com.nm.prices.model.values;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nm.prices.dtos.constants.PriceFilterValueEnum;
import com.nm.prices.dtos.constants.PriceValueEnum;
import com.nm.prices.model.Price;
import com.nm.prices.model.filter.PriceValueFilter;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_value")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PriceValue implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_price_value", sequenceName = "seq_price_value", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_price_value")
	protected Long id;
	@MapKey(name = "type")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "value", orphanRemoval = true)
	private Map<PriceFilterValueEnum, PriceValueFilter> filter = new HashMap<PriceFilterValueEnum, PriceValueFilter>();

	@ManyToOne(optional = false)
	private Price price;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PriceValueEnum type;

	public PriceValueEnum getType() {
		return type;
	}

	public void setType(PriceValueEnum type) {
		this.type = type;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public PriceValue() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PriceValueFilter put(PriceFilterValueEnum key, PriceValueFilter value) {
		value.setType(key);
		value.setValue(this);
		return filter.put(key, value);
	}

	public Map<PriceFilterValueEnum, PriceValueFilter> getFilter() {
		return filter;
	}

	public void setFilter(Map<PriceFilterValueEnum, PriceValueFilter> filter) {
		this.filter = filter;
	}

	@Override
	public String toString() {
		return "PriceValue [id=" + id + ", filter=" + filter + ", montant=" + ", price=" + price + "]";
	}

}
