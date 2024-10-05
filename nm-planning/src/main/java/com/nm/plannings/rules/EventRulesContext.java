package com.nm.plannings.rules;

import java.util.Collection;
import java.util.HashSet;

import com.nm.plannings.constants.SlotType;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningQuery.Filter;

/**
 * 
 * @author nabilmansouri
 *
 */
public class EventRulesContext {

	private Collection<SlotType> strongers = new HashSet<SlotType>();
	private DtoPlanningQuery.Filter filter;

	public Filter getFilter() {
		return filter;
	}

	public EventRulesContext setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}

	public Collection<SlotType> getStrongers() {
		return strongers;
	}

	public EventRulesContext withStrongers(SlotType strongers) {
		this.strongers.add(strongers);
		return this;
	}
}
