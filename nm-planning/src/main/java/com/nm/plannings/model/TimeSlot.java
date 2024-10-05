package com.nm.plannings.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import org.hibernate.annotations.UpdateTimestamp;
import org.joda.time.Interval;

import com.google.common.collect.Sets;
import com.nm.plannings.constants.SlotType;
import com.nm.utils.json.EnumHibernateType;
import com.nm.utils.node.ModelNode;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_planning_timeslot", schema = "mod_planning")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TimeSlot implements Serializable {

	private static final long serialVersionUID = -5259645752344689165L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_planning_timeslot", sequenceName = "seq_planning_timeslot", allocationSize = 1, schema = "mod_planning")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_planning_timeslot")
	private Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Convert(converter = EnumHibernateType.class)
	private SlotType type;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Planning planning;
	@Column(updatable = false, nullable = false)
	private Date createdAt = new Date();
	@UpdateTimestamp
	@Column(nullable = false)
	private Date updatedAt = new Date();
	@Column(nullable = false)
	private Date beginPlan = new Date();
	@Column(nullable = false)
	private Date endPlan;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(schema = "mod_planning", name = "nm_planning_timeslot_subscribers")
	private Collection<ModelNode> subscribers = Sets.newHashSet();

	public Collection<ModelNode> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Collection<ModelNode> subscribers) {
		this.subscribers = subscribers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSlot other = (TimeSlot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getBeginPlan() {
		return beginPlan;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getEndPlan() {
		return endPlan;
	}

	public Long getId() {
		return id;
	}

	public Planning getPlanning() {
		return planning;
	}

	public SlotType getType() {
		return type;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public TimeSlot setBeginPlan(Date beginPlan) {
		this.beginPlan = beginPlan;
		return this;
	}

	public TimeSlot setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public TimeSlot setEndPlan(Date endPlan) {
		this.endPlan = endPlan;
		return this;
	}

	public TimeSlot setId(Long id) {
		this.id = id;
		return this;
	}

	public TimeSlot setPlanning(Planning planning) {
		this.planning = planning;
		return this;
	}

	public TimeSlot setType(SlotType type) {
		this.type = type;
		return this;
	}

	public TimeSlot setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	@Override
	public String toString() {
		return "TimeSlot [id=" + id + ", type=" + type + ", planning=" + planning + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", beginPlan=" + beginPlan + ", endPlan=" + endPlan + "]";
	}

	@AssertTrue(message = "slot.plan.range")
	protected boolean isValidPlanRange() {
		if (beginPlan == null || endPlan == null) {
			return false;
		}
		Long begin = beginPlan.getTime();
		Long end = endPlan.getTime();
		return begin <= end;
	}

	public abstract TimeSlot clone();

	public Interval toIntervalPlan() {
		return new Interval(getBeginPlan().getTime(), getEndPlan().getTime());
	}
}
