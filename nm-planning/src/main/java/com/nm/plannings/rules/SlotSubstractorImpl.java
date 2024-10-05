package com.nm.plannings.rules;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.Interval;

import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;
import com.nm.utils.dates.DateUtilsMore;

/**
 * 
 * @author nabilmansouri
 *
 */
public class SlotSubstractorImpl implements SlotSubstractor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EventRulesContext context;

	public SlotSubstractorImpl(EventRulesContext context) {
		this.context = context;
	}

	public Collection<DtoSlotOccurrence> substract(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2, DtoSlotOccurrenceGroup group) {
		group.remove(ev1);
		group.remove(ev2);
		//
		Collection<DtoSlotOccurrence> event = substract(ev1, ev2);
		group.addAll(event);
		return event;
	}

	public Collection<DtoSlotOccurrence> substract(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2) {
		DtoSlotOccurrence stronger = null, other = null;
		if (context.getStrongers().contains(ev1.getType())) {
			stronger = ev1;
			other = ev2;
		} else if (context.getStrongers().contains(ev2.getType())) {
			stronger = ev2;
			other = ev1;
		}
		//
		Collection<Interval> diff = DateUtilsMore.difference(other.toInterval(), stronger.toInterval());
		//
		Collection<DtoSlotOccurrence> events = new ArrayList<DtoSlotOccurrence>();
		events.add(stronger);
		for (Interval d : diff) {
			DtoSlotOccurrence clone = other.clone().setStart(d.getStart().toDate()).setEnd(d.getEnd().toDate());
			events.add(clone);
		}
		return events;
	}

}
