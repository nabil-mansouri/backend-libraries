package com.nm.plannings.rules;

import java.io.Serializable;

import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;

/***
 * 
 * @author nabilmansouri
 *
 */
public interface SlotMerger extends Serializable {
	public DtoSlotOccurrence merge(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2, DtoSlotOccurrenceGroup group);

	public DtoSlotOccurrence merge(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2);
}
