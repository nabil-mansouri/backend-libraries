package com.nm.plannings.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.utils.node.ModelNode;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_planning", schema = "mod_planning")
public class Planning implements Serializable {

	private static final long serialVersionUID = -5259645752344689165L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_planning", sequenceName = "seq_planning", allocationSize = 1, schema = "mod_planning")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_planning")
	private Long id;

	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();
	@UpdateTimestamp
	@Column(nullable = false)
	private Date updatedAt = new Date();
	@Valid
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planning", orphanRemoval = true)
	private Collection<TimeSlot> slots = new HashSet<TimeSlot>(0);
	@ManyToOne(optional = true)
	private ModelNode about;

	public ModelNode getAbout() {
		return about;
	}

	public void setAbout(ModelNode about) {
		this.about = about;
	}

	public boolean add(TimeSlot e) {
		e.setPlanning(this);
		return slots.add(e);
	}

	public void clear() {
		slots.clear();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planning other = (Planning) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	public Collection<TimeSlot> getSlots() {
		return slots;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	// do not include slots in hashset and equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@PreUpdate
	public void onUpdate() {
		this.setUpdatedAt(new Date());
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSlots(Collection<TimeSlot> slots) {
		this.slots = slots;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int size() {
		return slots.size();
	}

	@Override
	public String toString() {
		return "Planning [id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", slots=" + slots
				+ "]";
	}

}
