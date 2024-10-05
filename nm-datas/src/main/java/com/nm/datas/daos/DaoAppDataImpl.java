package com.nm.datas.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.datas.models.AppData;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class DaoAppDataImpl extends AbstractGenericDao<AppData, Long>implements DaoAppData {

	public Long getId(AppData bean) {
		return bean.getId();
	}

	public AppData findByHash(String hash) throws NoDataFoundException {
		DetachedCriteria cri = DetachedCriteria.forClass(AppData.class);
		cri.add(Restrictions.eq("hash", hash));
		return findFirst(cri);
	}

	public Class<AppData> getClassName() {
		return AppData.class;
	}

}
