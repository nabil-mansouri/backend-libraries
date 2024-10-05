package com.nm.plannings.converters.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.MutableDateTime;

import com.nm.plannings.InvalidPlanningDaysException;
import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.PlanningDays;
import com.nm.plannings.converters.EventDtoConverter;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.plannings.splitters.DaySplitterStrategy;
import com.nm.utils.dates.DateUtilsMore;
import com.nm.utils.dates.NoIntervalException;
import com.nm.utils.dates.iterators.DateIteratorFactory;
import com.nm.utils.dates.iterators.DateIteratorListener;

/**
 * 
 * @author Nabil
 * 
 */
public class EventDtoConverterRecurrent implements EventDtoConverter {
	private final DaySplitterStrategy strategy;

	public EventDtoConverterRecurrent(DaySplitterStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	public List<DtoSlotOccurrence> toEvents(Collection<TimeSlot> slots, Date begin, Date end)
			throws NoIntervalException {
		List<DtoSlotOccurrence> events = new ArrayList<DtoSlotOccurrence>();
		for (TimeSlot slot : slots) {
			events.addAll(toEvents(slot, begin, end));
		}
		return events;
	}

	public List<DtoSlotOccurrence> toEvents(TimeSlot slot, Date begin, Date end) throws NoIntervalException {
		final List<DtoSlotOccurrence> events = new ArrayList<DtoSlotOccurrence>();
		//
		final TimeSlotRecurrent rec = (TimeSlotRecurrent) slot;
		//
		if (rec.getDays().equals(PlanningDays.AllDays)) {
			events.addAll(strategy.convert(slot, begin, end));
		} else {
			try {
				// OPTIMIZE
				Interval interval = DateUtilsMore.intersect(slot.toIntervalPlan(), begin, end);
				interval = DateUtilsMore.intersectWithNextDayOfWeek(interval, rec.getDays().getJodaDay());
				//
				DateIteratorFactory.iterateWeeks(interval, new DateIteratorListener() {
					public void clear(MutableDateTime time) {
						time.secondOfMinute().roundFloor();
					}

					public boolean onDate(Date date) {
						MutableDateTime recBeginHoraire = DateUtilsMore.toDatetime(date, rec.getBeginHoraire());
						MutableDateTime recEndHoraire = DateUtilsMore.toDatetime(date, rec.getEndHoraire());
						//
						DtoSlotOccurrence event = new DtoSlotOccurrence().setEventType(SlotRepeatKind.Recurrent)
								.setOriginalStartPlan(rec.getBeginPlan()).setOriginalEndPlan(rec.getEndPlan())
								.setOriginalEndHoraire(rec.getEndHoraire())
								.setOriginalStartHoraire(rec.getBeginHoraire()).setNoEndPlan(rec.isNoEndPlan());
						//
						event.setStart(recBeginHoraire.toDate());
						event.setEnd(recEndHoraire.toDate());
						event.setType(rec.getType());
						if (rec.getId() != null) {
							event.setId(rec.getId().toString());
						}
						events.add(event);
						return true;
					}
				});
			} catch (InvalidPlanningDaysException e) {
				e.printStackTrace();
			} catch (NoIntervalException e) {
				e.printStackTrace();
			}
		}
		return events;
	}

}
