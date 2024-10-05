package com.nm.tests.plannings;

import org.joda.time.Interval;

import com.nm.app.planning.DtoNodeMerge;

/**
 * 
 * @author MANSOURI
 *
 */
public class MergeNodeDtoTest extends DtoNodeMerge {
	private static final long serialVersionUID = 1L;
	String type;

	public MergeNodeDtoTest(String type) {
		super();
		this.type = type;
	}

	@Override
	public boolean abuts(DtoNodeMerge other) {
		Interval i1 = toInterval();
		Interval i2 = other.toInterval();
		return i1.gap(i2).toDuration().getStandardDays() <= 1;
	}

	@Override
	public String getType() {
		return type;
	}

}