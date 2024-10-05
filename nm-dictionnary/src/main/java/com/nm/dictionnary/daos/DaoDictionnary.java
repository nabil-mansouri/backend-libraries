package com.nm.dictionnary.daos;

import com.nm.dictionnary.constants.EnumDictionnaryDomain;
import com.nm.dictionnary.models.Dictionnary;
import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface DaoDictionnary extends IGenericDao<Dictionnary, Long> {
	public Dictionnary getOrCreate(EnumDictionnaryDomain domain);
}
