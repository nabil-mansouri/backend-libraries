package com.nm.plannings.sorters;

import java.util.Comparator;

import org.joda.time.MutableDateTime;

import com.nm.plannings.dtos.DtoSlotOccurrence;

/**
 * 
 * @author Nabil
 * 
 */
public class EventComparator implements Comparator<DtoSlotOccurrence> {

	public int compare(DtoSlotOccurrence o1, DtoSlotOccurrence o2) {
		MutableDateTime start1 = new MutableDateTime(o1.getStart());
		MutableDateTime start2 = new MutableDateTime(o2.getStart());
		return start1.compareTo(start2);
	}

}
