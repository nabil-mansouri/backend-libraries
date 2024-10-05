package com.nm.prices.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.prices.dtos.constants.PriceFilterEnum;
import com.nm.prices.model.filter.PriceFilter;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.model.values.PriceValue;
import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_price")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Price implements Serializable, IGraph {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_price", sequenceName = "seq_price", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_price")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	//
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	protected PriceSubject subject;
	@Size(min = 1, message = "price.prices.empty")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	protected Collection<PriceValue> values = new ArrayList<PriceValue>();
	@MapKey(name = "type")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "price", orphanRemoval = true)
	private Map<PriceFilterEnum, PriceFilter> filter = new HashMap<PriceFilterEnum, PriceFilter>();
	@Column(nullable = false)
	private boolean root;

	public boolean isRoot() {
		return root;
	}

	public Price setRoot(boolean root) {
		this.root = root;
		return this;
	}

	public boolean root() {
		return root;
	}

	public Map<PriceFilterEnum, PriceFilter> getFilter() {
		return filter;
	}

	public void setFilter(Map<PriceFilterEnum, PriceFilter> filter) {
		this.filter = filter;
	}

	public PriceFilter put(PriceFilterEnum key, PriceFilter value) {
		value.setType(key);
		value.setPrice(this);
		return filter.put(key, value);
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

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public PriceSubject getSubject() {
		return subject;
	}

	public void setSubject(PriceSubject subject) {
		this.subject = subject;
	}

	public Collection<PriceValue> getValues() {
		return values;
	}

	public void setValues(Collection<PriceValue> values) {
		this.values = values;
	}
	public String uuid() {
		return getClass().getSimpleName() + "/" + getId();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean add(PriceValue e) {
		e.setPrice(this);
		return values.add(e);
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", created=" + created + ", updated=" + updated + ", subject=" + subject
				+ ", values=" + values + ", filter=" + filter + "]";
	}

}
