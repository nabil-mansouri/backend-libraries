package com.nm.dictionnary.daos;

import com.nm.dictionnary.constants.EnumDictionnaryDomain;
import com.nm.dictionnary.models.Dictionnary;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DaoDictionnaryImpl extends AbstractGenericDao<Dictionnary, Long>implements DaoDictionnary {

	public Dictionnary getOrCreate(EnumDictionnaryDomain domain) {
		try {
			return findFirst(QueryBuilderDictionnary.get().withDomain(domain));
		} catch (NoDataFoundException e) {
			Dictionnary dictionnary = new Dictionnary();
			dictionnary.setDomain(domain);
			save(dictionnary);
			return dictionnary;
		}
	}

	@Override
	protected Class<Dictionnary> getClassName() {
		return Dictionnary.class;
	}

}
