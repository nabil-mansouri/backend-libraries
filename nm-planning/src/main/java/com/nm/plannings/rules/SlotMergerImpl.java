package com.nm.plannings.rules;

import org.joda.time.Interval;

import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;
import com.nm.utils.dates.DateUtilsMore;

/**
 * 
 * @author nabilmansouri
 *
 */
public class SlotMergerImpl implements SlotMerger {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final EventRulesContext context;

	public SlotMergerImpl(EventRulesContext context) {
		this.context = context;
	}

	public DtoSlotOccurrence merge(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2, DtoSlotOccurrenceGroup group) {
		DtoSlotOccurrence res = merge(ev1, ev2);
		group.remove(ev1);
		group.remove(ev2);
		group.add(res);
		return res;
	}

	public DtoSlotOccurrence merge(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2) {
		Interval interval = DateUtilsMore.union(ev1.toInterval(), ev2.toInterval());
		DtoSlotOccurrence clone = ev1.clone();
		clone.getIdsMerged().addAll(ev2.getIdsMerged());
		clone.getIdsMerged().add(ev2.getId());
		clone.setStart(interval.getStart().toDate());
		clone.setEnd(interval.getEnd().toDate());
		return clone;
	}

}
