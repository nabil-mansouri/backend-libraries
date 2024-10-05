package com.nm.plannings.converters;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.model.TimeSlot;
import com.nm.utils.dates.NoIntervalException;

/**
 * 
 * @author Nabil
 * 
 */
public interface EventDtoConverter {

	public List<DtoSlotOccurrence> toEvents(Collection<TimeSlot> slots, Date begin, Date end) throws NoIntervalException;

	public List<DtoSlotOccurrence> toEvents(TimeSlot slot, Date begin, Date end) throws NoIntervalException;
}
