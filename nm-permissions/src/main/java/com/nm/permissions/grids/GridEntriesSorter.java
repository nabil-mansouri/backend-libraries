package com.nm.permissions.grids;

import java.io.Serializable;
import java.util.Collection;

import com.nm.permissions.models.PermissionGrid;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface GridEntriesSorter extends Serializable {
	public GridEntriesSorted sort(GridEntriesSorted sort, Collection<PermissionGrid> grids);
}
