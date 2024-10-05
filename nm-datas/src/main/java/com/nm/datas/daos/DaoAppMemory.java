package com.nm.datas.daos;

import java.util.Collection;

import com.nm.datas.dtos.MemoryKeyDto;
import com.nm.datas.models.AppMemory;
import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface DaoAppMemory extends IGenericDao<AppMemory, Long> {

	Collection<AppMemory> findByKey(MemoryKeyDto key);
}
