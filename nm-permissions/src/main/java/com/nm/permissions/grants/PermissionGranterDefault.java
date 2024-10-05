package com.nm.permissions.grants;

import com.nm.permissions.grids.GridEntriesSorter;
import com.nm.permissions.grids.GridEntriesSorterDefault;
import com.nm.permissions.grids.GridEntriesSorterInheritChildrenFirst;
import com.nm.permissions.grids.GridEntriesSorterMultiple;
import com.nm.permissions.grids.GridFinderStrategy;
import com.nm.permissions.grids.GridFinderStrategyDefault;
import com.nm.permissions.grids.GridMergerStrategy;
import com.nm.permissions.grids.GridMergerStrategyDisjunction;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class PermissionGranterDefault extends PermissionAuthorityGranter {

	@Override
	protected GridMergerStrategy merger() {
		return new GridMergerStrategyDisjunction();
	}

	@Override
	protected GridEntriesSorter sorter() {
		GridEntriesSorterMultiple multiple = new GridEntriesSorterMultiple();
		multiple.add(new GridEntriesSorterDefault());
		multiple.add(new GridEntriesSorterInheritChildrenFirst());
		return multiple;
	}

	@Override
	protected GridFinderStrategy finder() {
		return new GridFinderStrategyDefault();
	}

}
