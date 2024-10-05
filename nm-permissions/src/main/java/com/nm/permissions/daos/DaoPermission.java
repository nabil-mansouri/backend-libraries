package com.nm.permissions.daos;

import java.util.Collection;

import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Resource;
import com.nm.permissions.models.Subject;
import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface DaoPermission extends IGenericDao<PermissionGrid, Long> {
	PermissionGrid getOrCreateGrid(Subject subject, Collection<Resource> resources);

	Collection<Resource> getOrCreateResources(Collection<ResourceType> types);
}
