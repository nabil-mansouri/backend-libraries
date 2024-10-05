package com.nm.permissions.grids;

import java.util.Arrays;
import java.util.Collection;

import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Subject;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridFinderStrategyVoid implements GridFinderStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Collection<PermissionGrid> finder(Subject subject) throws NoDataFoundException {
		return Arrays.asList(subject.getGrid());
	}

}
