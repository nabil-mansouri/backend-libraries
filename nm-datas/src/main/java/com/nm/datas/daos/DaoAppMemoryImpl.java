package com.nm.datas.daos;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.datas.dtos.MemoryKeyDto;
import com.nm.datas.models.AppMemory;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class DaoAppMemoryImpl extends AbstractGenericDao<AppMemory, Long>implements DaoAppMemory {

	public Long getId(AppMemory bean) {
		return bean.getId();
	}

	public Class<AppMemory> getClassName() {
		return AppMemory.class;
	}

	public Collection<AppMemory> findByKey(MemoryKeyDto key) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppMemory.class);
		criteria.add(Restrictions.eq("category", key.getCategory()));
		criteria.add(Restrictions.eq("key", key.getKey()));
		criteria.add(Restrictions.eq("scope", key.getScope()));
		return findByCriteria(criteria);
	}

}
