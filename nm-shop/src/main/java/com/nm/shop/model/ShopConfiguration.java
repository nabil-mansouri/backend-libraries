package com.nm.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.shop.constants.ShopConfigType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "RM_SHOP_CONFIGURATION")
public class ShopConfiguration implements Serializable {

	private static final long serialVersionUID = -4629117280465782791L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// TODO configuration for one shop or all shop... (default conf)
	@Id
	@SequenceGenerator(name = "SEQ_SHOP_CONFIG", sequenceName = "SEQ_SHOP_CONFIG", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_SHOP_CONFIG")
	private Long id;
	@Column(nullable = false, updatable = false)
	private Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	private Date updated = new Date();
	@Column(nullable = false)
	private ShopConfigType type;

	@Column(columnDefinition = "TEXT")
	private String value;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShopConfiguration other = (ShopConfiguration) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public ShopConfigType getType() {
		return type;
	}

	public Date getUpdated() {
		return updated;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(ShopConfigType type) {
		this.type = type;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
