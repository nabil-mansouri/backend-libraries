package com.nm.permissions.grids;

import java.io.Serializable;
import java.util.Collection;

import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Subject;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface GridFinderStrategy extends Serializable {
	public Collection<PermissionGrid> finder(Subject subject) throws NoDataFoundException;
}
