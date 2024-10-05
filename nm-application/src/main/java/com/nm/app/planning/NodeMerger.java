package com.nm.app.planning;

import java.io.Serializable;
import java.util.Collection;

import org.joda.time.Interval;

import com.nm.utils.dates.DateUtilsMore;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class NodeMerger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DtoNodeMerge merge(DtoNodeMerge ev1, DtoNodeMerge ev2, Collection<DtoNodeMerge> group) {
		DtoNodeMerge res = merge(ev1, ev2);
		group.remove(ev1);
		group.remove(ev2);
		group.add(res);
		return res;
	}

	public DtoNodeMerge merge(DtoNodeMerge ev1, DtoNodeMerge ev2) {
		Interval interval = DateUtilsMore.union(ev1.toInterval(), ev2.toInterval());
		DtoNodeMerge clone = ev1.clone();
		clone.pushMerged(ev1);
		clone.pushMerged(ev2);
		clone.setStart(interval.getStart().toDate());
		clone.setEnd(interval.getEnd().toDate());
		return clone;
	}
}
