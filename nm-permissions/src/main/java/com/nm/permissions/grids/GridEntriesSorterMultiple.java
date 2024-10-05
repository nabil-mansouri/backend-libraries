package com.nm.permissions.grids;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.permissions.models.PermissionGrid;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridEntriesSorterMultiple implements GridEntriesSorter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<GridEntriesSorter> sorters = Lists.newArrayList();

	public GridEntriesSorterMultiple add(GridEntriesSorter s) {
		this.sorters.add(s);
		return this;
	}

	public GridEntriesSorted sort(final GridEntriesSorted sort, Collection<PermissionGrid> grids) {
		for (GridEntriesSorter s : sorters) {
			s.sort(sort, grids);
		}
		return sort;
	}

}
