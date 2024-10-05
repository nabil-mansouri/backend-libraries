package com.nm.utils.dates.iterators;

import java.util.Date;

import org.joda.time.MutableDateTime;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface DateIteratorListener {
	public void clear(MutableDateTime time);

	public boolean onDate(Date date);
}
