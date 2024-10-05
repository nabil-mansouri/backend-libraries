package com.nm.utils.jdbc.orm.operations;

import com.nm.utils.jdbc.orm.JdbcOrmAssociationContext;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface AssociationTypeFilterStrategy {
	public boolean filter(JdbcOrmAssociationContext a) throws Exception;
}
