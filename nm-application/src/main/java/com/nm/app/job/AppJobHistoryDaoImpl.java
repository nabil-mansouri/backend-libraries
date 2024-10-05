package com.nm.app.job;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class AppJobHistoryDaoImpl extends AbstractGenericDao<AppJobHistory, Long>implements AppJobHistoryDao {

	public Long getId(AppJobHistory bean) {
		return bean.getId();
	}

	public Class<AppJobHistory> getClassName() {
		return AppJobHistory.class;
	}

}
