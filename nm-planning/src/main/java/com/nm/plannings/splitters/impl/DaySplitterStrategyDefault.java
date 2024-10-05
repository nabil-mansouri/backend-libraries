package com.nm.plannings.splitters.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.MutableDateTime;

import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.PlanningDays;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.plannings.splitters.DaySplitterStrategy;
import com.nm.utils.dates.DateUtilsMore;
import com.nm.utils.dates.NoIntervalException;
import com.nm.utils.dates.iterators.IntervalIteratorFactory;
import com.nm.utils.dates.iterators.IntervalIteratorListener;

/**
 * 
 * @author Nabil
 * 
 */
public class DaySplitterStrategyDefault implements DaySplitterStrategy {

	public List<DtoSlotOccurrence> convert(TimeSlot slot, Date begin, Date end) {
		final List<DtoSlotOccurrence> events = new ArrayList<DtoSlotOccurrence>();
		final TimeSlotRecurrent rec = (TimeSlotRecurrent) slot;
		//
		if (rec.getDays().equals(PlanningDays.AllDays)) {
			// OPTIMIZE
			try {
				Interval interval1 = DateUtilsMore.intersect(slot.toIntervalPlan(), begin, end);
				IntervalIteratorFactory.iterateDays(interval1, rec.getBeginHoraire(), rec.getEndHoraire(),
						new IntervalIteratorListener() {

							public boolean onInterval(Interval interval) {
								DtoSlotOccurrence event = new DtoSlotOccurrence().setEventType(SlotRepeatKind.Recurrent)
										.setOriginalStartPlan(rec.getBeginPlan()).setOriginalEndPlan(rec.getEndPlan())
										.setOriginalStartHoraire(rec.getBeginHoraire())
										.setOriginalEndHoraire(rec.getEndHoraire()).setNoEndPlan(rec.isNoEndPlan());
								//
								event.setStart(interval.getStart().toDate());
								event.setEnd(interval.getEnd().toDate());
								event.setType(rec.getType());
								//
								if (rec.getId() != null) {
									event.setId(rec.getId().toString());
								}
								events.add(event);
								return true;
							}

							public void clear(MutableDateTime time) {
								time.secondOfMinute().roundFloor();
							}
						});
			} catch (NoIntervalException e) {
				// No overlaps so no events...
			}

		}
		return events;
	}

}
