package com.nm.app.log;

import java.util.List;

import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface GeneralLogDao extends IGenericDao<GeneralLog, Long> {
	public void deleteAll(List<Long> criteria);
}
