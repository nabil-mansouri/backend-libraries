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
public class DaySplitterStrategyKeep implements DaySplitterStrategy {
	private class MyIntervalIteratorListener implements IntervalIteratorListener {
		private Date start;
		private Date end;
		private boolean ok;

		public boolean onInterval(Interval interval) {
			if (!ok) {
				start = interval.getStart().toDate();
			}
			end = interval.getEnd().toDate();
			ok = true;
			return true;
		}

		public void clear(MutableDateTime time) {
			time.secondOfMinute().roundFloor();
		}

	}

	public List<DtoSlotOccurrence> convert(TimeSlot slot, Date begin, Date end) throws NoIntervalException {
		List<DtoSlotOccurrence> events = new ArrayList<DtoSlotOccurrence>();
		TimeSlotRecurrent rec = (TimeSlotRecurrent) slot;
		//
		//
		if (rec.getDays().equals(PlanningDays.AllDays)) {
			try {
				Interval interval1 = DateUtilsMore.intersect(slot.toIntervalPlan(), begin, end);
				MyIntervalIteratorListener listener = new MyIntervalIteratorListener();
				IntervalIteratorFactory.iterateDays(interval1, rec.getBeginHoraire(), rec.getEndHoraire(), listener);
				if (listener.ok) {
					DtoSlotOccurrence event = new DtoSlotOccurrence().setEventType(SlotRepeatKind.Recurrent)
							.setOriginalStartPlan(rec.getBeginPlan()).setOriginalEndPlan(rec.getEndPlan())
							.setOriginalStartHoraire(rec.getBeginHoraire()).setOriginalEndHoraire(rec.getEndHoraire())
							.setNoEndPlan(rec.isNoEndPlan());
					//
					event.setStart(DateUtilsMore.toDatetime(listener.start, rec.getBeginHoraire()).toDate());
					event.setEnd(DateUtilsMore.toDatetime(listener.end, rec.getEndHoraire()).toDate());
					//
					if (rec.getId() != null) {
						event.setId(rec.getId().toString());
					}
					events.add(event);
				}
			} catch (NoIntervalException e) {
				// No overlaps so no events...
			}
		}
		return events;
	}

}
