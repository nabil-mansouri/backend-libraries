package com.nm.plannings.converters;

import com.nm.plannings.converters.impl.EventDtoConverterExceptionnal;
import com.nm.plannings.converters.impl.EventDtoConverterRecurrent;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.plannings.splitters.impl.DaySplitterStrategyDefault;
import com.nm.plannings.splitters.impl.DaySplitterStrategyKeep;

/**
 * 
 * @author Nabil
 * 
 */
public class EventDtoConverterFactory {
	private static EventDtoConverter exceptionnal = new EventDtoConverterExceptionnal();
	private static EventDtoConverter recurrentSplitAllDay = new EventDtoConverterRecurrent(new DaySplitterStrategyDefault());
	private static EventDtoConverter recurrentKeepAllDay = new EventDtoConverterRecurrent(new DaySplitterStrategyKeep());

	public enum AllDaysStrategy {
		Split, Keep
	}

	public static EventDtoConverter getExceptionnal() {
		return exceptionnal;
	}

	public static EventDtoConverter getRecurrent(AllDaysStrategy strategy) {
		switch (strategy) {
		case Split:
			return recurrentSplitAllDay;
		case Keep:
		default:
			return recurrentKeepAllDay;
		}
	}

	public static EventDtoConverter get(TimeSlot slot, AllDaysStrategy strategy) {
		if (slot instanceof TimeSlotRecurrent) {
			switch (strategy) {
			case Split:
				return recurrentSplitAllDay;
			case Keep:
			default:
				return recurrentKeepAllDay;
			}
		} else {
			return exceptionnal;
		}
	}
}
