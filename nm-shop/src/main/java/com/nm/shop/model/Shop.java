package com.nm.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.cms.models.CmsContentsComposed;
import com.nm.geo.models.Address;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_shop_shop")
public class Shop implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_shop_shop", sequenceName = "seq_shop_shop", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_shop_shop")
	protected Long id;

	@Column(nullable = false, updatable = false)
	protected Date created = new Date();

	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private CmsContentsComposed content = new CmsContentsComposed();

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private Address address = new Address();

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public CmsContentsComposed getContent() {
		return content;
	}

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public Date getUpdated() {
		return updated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setContent(CmsContentsComposed content) {
		this.content = content;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", created=" + created + ", updated=" + updated + ", content=" + content
				+ ", image=" + ", address=" + address + "]";
	}
}
