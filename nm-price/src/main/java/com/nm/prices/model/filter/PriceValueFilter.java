package com.nm.prices.model.filter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.prices.dtos.constants.PriceFilterValueEnum;
import com.nm.prices.model.values.PriceValue;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_value_filter")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PriceValueFilter implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_price_value_filter", sequenceName = "seq_price_value_filter", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_price_value_filter")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	// @Convert(converter = EnumHibernateType.class)
	@Type(type = EnumHibernateType.EE)
	private PriceFilterValueEnum type;
	@ManyToOne(optional = false)
	private PriceValue value;

	public PriceValue getValue() {
		return value;
	}

	public void setValue(PriceValue value) {
		this.value = value;
	}

	public PriceFilterValueEnum getType() {
		return (this.type);
	}

	public void setType(PriceFilterValueEnum type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
