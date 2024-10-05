package com.nm.geo.daos;

import com.nm.geo.models.Address;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DaoAddressImpl extends AbstractGenericDao<Address, Long>implements DaoAddress {

	@Override
	protected Class<Address> getClassName() {
		return Address.class;
	}

}
