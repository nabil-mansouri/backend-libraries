package com.nm.utils.dates.iterators;

import java.sql.Time;

import org.joda.time.Interval;
import org.joda.time.MutableDateTime;

import com.nm.utils.dates.DateUtilsMore;
import com.nm.utils.dates.NoIntervalException;

/**
 * 
 * @author nabilmansouri
 *
 */
public class IntervalIteratorFactory {

	public static void iterateDays(final Interval reference, final Time beginTime, final Time endTime,
			IntervalIteratorListener listener) throws NoIntervalException {
		IntervalIterator it = new IntervalIterator() {
			private MutableDateTime start = new MutableDateTime(reference.getStart());
			private MutableDateTime end = new MutableDateTime(reference.getEnd());

			public void iterate(IntervalIteratorListener listener) {
				MutableDateTime current = this.start.copy();
				listener.clear(current);
				listener.clear(end);
				//
				while (current.isBefore(end) || current.isEqual(end)) {
					Interval interval1 = DateUtilsMore.toInterval(current.toDateTime(), beginTime, endTime);
					Interval interval2 = reference.overlap(interval1);
					if (interval2 != null && interval2.toDurationMillis() > 0) {
						listener.onInterval(interval2);
					}
					current.addDays(1);
				}
			}
		};
		it.iterate(listener);
	}
}
