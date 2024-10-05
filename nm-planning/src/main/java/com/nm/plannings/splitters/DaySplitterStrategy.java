package com.nm.plannings.splitters;

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
public interface DaySplitterStrategy {

	public List<DtoSlotOccurrence> convert(TimeSlot slot,Date begin, Date end) throws NoIntervalException;
}
