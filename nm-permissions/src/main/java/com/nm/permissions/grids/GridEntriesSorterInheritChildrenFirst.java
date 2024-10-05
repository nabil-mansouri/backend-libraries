package com.nm.permissions.grids;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.permissions.constants.Action;
import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Resource;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridEntriesSorterInheritChildrenFirst implements GridEntriesSorter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridEntriesSorted sort(final GridEntriesSorted sort, Collection<PermissionGrid> grids) {
		final Collection<GridMergerEntry> entriesToAdd = Lists.newArrayList();
		for (final PermissionGrid grid : grids) {
			for (final Permission p : grid.getPermissions()) {
				GraphIteratorBuilder.buildDeep().iterate(p.getResource(), new IteratorListenerGraphInfo() {

					public void onFounded(GraphInfo node) {
						Resource resource = (Resource) node.getCurrent();
						Action action = p.getAction();
						Permission sub = new Permission();
						sub.setAction(action);
						sub.setResource(resource);
						sub.setGranted(p.isGranted());
						if (!sort.containsKeyForEntry(resource, action)) {
							entriesToAdd.add(new GridMergerEntry(grid, sub));
						}
					}
				});
			}
		}
		// DO THEN IN ORDER TO PASSS MULTIPLE TIMES FOREACH GRID
		for (GridMergerEntry entry : entriesToAdd) {
			sort.add(entry);
		}
		return sort;
	}

}
