package com.nm.utils.hibernate.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.utils.dates.UUIDUtils;

/**
 * 
 * @author Nabil
 * 
 */
@MappedSuperclass
public abstract class ModelTimeable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	private String uniqueId = UUIDUtils.UUID();
	@Column(nullable = false, updatable = false)
	protected Date createdAt = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updatedAt = new Date();
	@Column(insertable = true, updatable = false, name = "node_id", nullable = false, unique = true)
	private BigInteger nodeId;

	public boolean autoDeleteNode() {
		return true;
	}

	public BigInteger getNodeId() {
		return nodeId;
	}

	public void setNodeId(BigInteger nodeId) {
		this.nodeId = nodeId;
	}

	//
	@Id
	public abstract Long getId();

	public final Date getCreatedAt() {
		return createdAt;
	}

	public final void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public final Date getUpdatedAt() {
		return updatedAt;
	}

	public final void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		// IF ID IS NULL TAKE CARE OF UUID
		if (getId() == null) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
			return result;
		} else {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
			return result;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelTimeable other = (ModelTimeable) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
			else {
				// IF BOTH ID ARE NUL TAKE CARE OF UUID
				if (uniqueId == null) {
					if (other.uniqueId != null)
						return false;
				} else if (!uniqueId.equals(other.uniqueId))
					return false;
			}
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	public void uniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
