package com.nm.utils.jdbc.orm;

import java.util.List;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface JdbcOrmAssociationProcessorListener {
	public boolean isOk(JdbcOrmAssociationContext context);

	public List<Object> filter(List<Object> original);

	public void notAttached(Object o, JdbcOrmAssociationContext context);

	public void notPersisted(Object o, JdbcOrmAssociationContext context);
}
