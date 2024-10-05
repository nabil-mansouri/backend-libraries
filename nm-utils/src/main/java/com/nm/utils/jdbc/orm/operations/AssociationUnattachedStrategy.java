package com.nm.utils.jdbc.orm.operations;

import com.nm.utils.jdbc.orm.JdbcOrmAssociationContext;

/**
 * 
 * @author mansoun
 *
 */
public interface AssociationUnattachedStrategy {
	void notAttach(Object o, JdbcOrmAssociationContext context) throws Exception;
}
