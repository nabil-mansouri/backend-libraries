package com.nm.permissions.grids;

import java.util.Collection;

import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridEntriesSorterDefault implements GridEntriesSorter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridEntriesSorted sort(GridEntriesSorted sort, Collection<PermissionGrid> grids) {
		for (PermissionGrid g : grids) {
			for (Permission p : g.getPermissions()) {
				sort.add(new GridMergerEntry(g, p));
			}
		}
		return sort;
	}

}
