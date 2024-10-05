package com.nm.permissions.grids;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridMergerStrategyDisjunction extends GridMergerStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean isGranted(List<GridMergerEntry> entries) {
		boolean[] or = new boolean[0];
		for (GridMergerEntry e : entries) {
			or = ArrayUtils.add(or, e.getPermission().isGranted());
		}
		return BooleanUtils.or(or);
	}

}
