package com.nm.utils.dates.iterators;

import org.joda.time.Interval;
import org.joda.time.MutableDateTime;

/**
 * 
 * @author nabilmansouri
 *
 */
public class DateIteratorFactory {
	public static void iterateWeeks(final Interval interval, DateIteratorListener listener) {
		DateIterator it = new DateIterator() {
			private MutableDateTime start = new MutableDateTime(interval.getStart());
			private MutableDateTime end = new MutableDateTime(interval.getEnd());

			public void iterate(DateIteratorListener listener) {
				MutableDateTime current = this.start.copy();
				listener.clear(current);
				listener.clear(end);
				//
				while (current.isBefore(end) || current.isEqual(end)) {
					listener.onDate(current.toDate());
					current.addWeeks(1);
				}
			}
		};
		it.iterate(listener);
	}

	public static void iterateDays(final Interval interval, DateIteratorListener listener) {
		DateIterator it = new DateIterator() {
			private MutableDateTime start = new MutableDateTime(interval.getStart());
			private MutableDateTime end = new MutableDateTime(interval.getEnd());

			public void iterate(DateIteratorListener listener) {
				MutableDateTime current = this.start.copy();
				listener.clear(current);
				listener.clear(end);
				//
				while (current.isBefore(end) || current.isEqual(end)) {
					listener.onDate(current.toDate());
					current.addDays(1);
				}
			}
		};
		it.iterate(listener);
	}
}
