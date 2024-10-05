package com.nm.app.job;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.utils.hibernate.NotFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class AppJobDaoImpl extends AbstractGenericDao<AppJob, Long>implements AppJobDao {

	public Long getId(AppJob bean) {
		return bean.getId();
	}

	public Class<AppJob> getClassName() {
		return AppJob.class;
	}

	public AppJob find(AppJobType type) throws NotFoundException {
		DetachedCriteria cri = DetachedCriteria.forClass(AppJob.class);
		cri.add(Restrictions.eq("type", type));
		Collection<AppJob> jobs = findByCriteria(cri);
		if (jobs.isEmpty()) {
			throw new NotFoundException("Jobs are empty");
		} else {
			return jobs.iterator().next();
		}
	}

	public Collection<AppJob> findEnabled() {
		DetachedCriteria cri = DetachedCriteria.forClass(AppJob.class);
		cri.add(Restrictions.eq("enable", true));
		return findByCriteria(cri);
	}
}
