package com.nm.app.job;

import java.util.Collection;

import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface AppJobDao extends IGenericDao<AppJob, Long> {
	public AppJob find(AppJobType type) throws NotFoundException;

	public Collection<AppJob> findEnabled();
}
