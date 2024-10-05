package com.nm.datas.daos;

import com.nm.datas.models.AppData;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface DaoAppData extends IGenericDao<AppData, Long> {
	public AppData findByHash(String md5) throws NoDataFoundException;
}
