package com.nm.plannings.rules;

import java.io.Serializable;
import java.util.Collection;

import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;

/***
 * 
 * @author nabilmansouri
 *
 */
public interface SlotSubstractor extends Serializable {
	public Collection<DtoSlotOccurrence> substract(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2, DtoSlotOccurrenceGroup group);

	public Collection<DtoSlotOccurrence> substract(DtoSlotOccurrence ev1, DtoSlotOccurrence ev2);
}
