package com.rm.model.taxs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.contract.prices.constants.TaxeApplicability;
import com.rm.contract.prices.constants.TaxeEvents;
import com.rm.contract.prices.constants.TaxeType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_price_tax_definition")
public class TaxDefinition implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_price_tax_definition", sequenceName = "seq_price_tax_definition", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_price_tax_definition")
	protected Long id;

	@Column(nullable = false, updatable = false)
	protected Date created = new Date();

	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	protected TaxeType taxeType;

	@Column(nullable = false)
	protected double nominateur;

	@Column(nullable = false)
	protected double denominateur = 1;
	@Column(nullable = false)
	protected String name;

	@ElementCollection
	@CollectionTable(name = "nm_price_tax_definition_events", joinColumns = @JoinColumn(name = "id"))
	@OrderColumn
	private Collection<TaxeEvents> events = new HashSet<TaxeEvents>(0);

	@ElementCollection
	@CollectionTable(name = "nm_price_tax_definition_applications", joinColumns = @JoinColumn(name = "id"))
	@OrderColumn
	@Size(min = 1)
	private Collection<TaxeApplicability> applicabilities = new HashSet<TaxeApplicability>(0);

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxDefinition other = (TaxDefinition) obj;
		if (applicabilities == null) {
			if (other.applicabilities != null)
				return false;
		} else if (!applicabilities.equals(other.applicabilities))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (Double.doubleToLongBits(denominateur) != Double.doubleToLongBits(other.denominateur))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(nominateur) != Double.doubleToLongBits(other.nominateur))
			return false;
		if (taxeType != other.taxeType)
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}

	public Collection<TaxeApplicability> getApplicabilities() {
		return applicabilities;
	}

	public Date getCreated() {
		return created;
	}

	public double getDenominateur() {
		return denominateur;
	}

	public Collection<TaxeEvents> getEvents() {
		return events;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getNominateur() {
		return nominateur;
	}

	public TaxeType getTaxeType() {
		return taxeType;
	}

	public Date getUpdated() {
		return updated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicabilities == null) ? 0 : applicabilities.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		long temp;
		temp = Double.doubleToLongBits(denominateur);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(nominateur);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((taxeType == null) ? 0 : taxeType.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		return result;
	}

	@AssertTrue(message = "taxes.nominateur.required")
	public boolean hasNominateur() {
		return nominateur != 0;
	}

	@AssertTrue(message = "taxes.denominateur.required")
	public boolean hasUnNominateur() {
		return denominateur != 0;
	}

	@PrePersist
	@PreUpdate
	protected void onBeforeSave() {
		if (this.taxeType != null) {
			switch (this.taxeType) {
			case Pourcentage:
				this.denominateur = 100;
				break;
			case Fixe:
			case Proportionnel:
				this.denominateur = 1;
				break;
			default:
			}
		}
	}

	public void setApplicabilities(Collection<TaxeApplicability> applicabilities) {
		this.applicabilities = applicabilities;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setDenominateur(double denominateur) {
		this.denominateur = denominateur;
	}

	public void setEvents(Collection<TaxeEvents> events) {
		this.events = events;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNominateur(double nominateur) {
		this.nominateur = nominateur;
	}

	public void setTaxeType(TaxeType taxeType) {
		this.taxeType = taxeType;
		onBeforeSave();
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "TaxeDefinition [id=" + id + ", created=" + created + ", updated=" + updated + ", taxeType=" + taxeType + ", nominateur=" + nominateur
				+ ", denominateur=" + denominateur + ", applicabilities=" + applicabilities + "]";
	}

}
