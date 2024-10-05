package com.nm.utils.dates.iterators;

import org.joda.time.Interval;
import org.joda.time.MutableDateTime;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface IntervalIteratorListener {
	public void clear(MutableDateTime time);

	public boolean onInterval(Interval interval);
}
