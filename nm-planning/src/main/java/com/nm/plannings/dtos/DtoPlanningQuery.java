package com.nm.plannings.dtos;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.nm.plannings.constants.SlotType;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DtoPlanningQuery {
	public enum OperationType {
		ComputeEdit, ComputeView, ComputeDelete, AssertSlotType, ComputeOccurrence
	}

	public enum Filter {
		Strong, Weak
	}

	private DtoPlanning planning;
	private OperationType type;
	private Date from;
	private Date to;
	private SlotType toEdit;
	private DtoSlotFilter slotFilter;
	// FILTER
	private Filter filter;
	private Collection<SlotType> strongers = new HashSet<SlotType>();
	//
	private boolean substract;

	public DtoSlotFilter getSlotFilter() {
		return slotFilter;
	}

	public DtoPlanningQuery setSlotFilter(DtoSlotFilter slotFilter) {
		this.slotFilter = slotFilter;
		return this;
	}

	public DtoPlanningQuery withStronger(SlotType type) {
		this.strongers.add(type);
		return this;
	}

	public DtoPlanning getPlanning() {
		return planning;
	}

	public DtoPlanningQuery setPlanning(DtoPlanning planning) {
		this.planning = planning;
		return this;
	}

	public OperationType getType() {
		return type;
	}

	public DtoPlanningQuery setType(OperationType type) {
		this.type = type;
		return this;
	}

	public Filter getFilter() {
		return filter;
	}

	public DtoPlanningQuery setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}

	public Collection<SlotType> getStrongers() {
		return strongers;
	}

	public DtoPlanningQuery withStrongers(SlotType strongers) {
		this.strongers.add(strongers);
		return this;
	}

	public Date getFrom() {
		return from;
	}

	public DtoPlanningQuery setFrom(Date from) {
		this.from = from;
		return this;
	}

	public Date getTo() {
		return to;
	}

	public DtoPlanningQuery setTo(Date to) {
		this.to = to;
		return this;
	}

	public SlotType getToEdit() {
		return toEdit;
	}

	public DtoPlanningQuery setToEdit(SlotType toEdit) {
		this.toEdit = toEdit;
		return this;
	}

	public boolean isSubstract() {
		return substract;
	}

	public DtoPlanningQuery setSubstract(boolean substract) {
		this.substract = substract;
		return this;
	}

	public DtoPlanningQuery setStrongers(Collection<SlotType> strongers) {
		this.strongers = strongers;
		return this;
	}
}
