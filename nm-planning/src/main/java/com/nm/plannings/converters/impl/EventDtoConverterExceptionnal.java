package com.nm.plannings.converters.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.converters.EventDtoConverter;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotExceptionnal;

/**
 * 
 * @author Nabil
 * 
 */
public class EventDtoConverterExceptionnal implements EventDtoConverter {

	public List<DtoSlotOccurrence> toEvents(Collection<TimeSlot> slots, Date begin, Date end) {
		List<DtoSlotOccurrence> events = new ArrayList<DtoSlotOccurrence>();
		for (TimeSlot slot : slots) {
			events.addAll(toEvents(slot, end, end));
		}
		return events;
	}

	public List<DtoSlotOccurrence> toEvents(TimeSlot slot, Date begin, Date end) {
		TimeSlotExceptionnal exc = (TimeSlotExceptionnal) slot;
		// DO NOT OPTIMIZE
		DtoSlotOccurrence event = new DtoSlotOccurrence().setEventType(SlotRepeatKind.Exceptionnal)
				.setOriginalStartPlan(exc.getBeginPlan()).setOriginalEndPlan(exc.getEndPlan());
		//
		event.setStart(slot.getBeginPlan());
		event.setEnd(slot.getEndPlan());
		event.setEventType(SlotRepeatKind.Exceptionnal);
		event.setTitle(exc.getCause());
		event.setType(slot.getType());
		if (exc.getId() != null) {
			event.setId(exc.getId().toString());
		}
		//
		return Arrays.asList(event);
	}

}
