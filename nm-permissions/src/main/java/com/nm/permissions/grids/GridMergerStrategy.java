package com.nm.permissions.grids;

import java.io.Serializable;
import java.util.List;

import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class GridMergerStrategy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final PermissionGrid merge(GridEntriesSorted sorted) {
		PermissionGrid merged = new PermissionGrid();
		for (String key : sorted.keySet()) {
			List<GridMergerEntry> all = sorted.get(key);
			Permission example = all.iterator().next().getPermission();
			//
			Permission perm = new Permission();
			perm.setAction(example.getAction());
			perm.setResource(example.getResource());
			perm.setGranted(isGranted(all));
			merged.add(perm);
		}
		return merged;
	}

	protected abstract boolean isGranted(List<GridMergerEntry> entries);
}
