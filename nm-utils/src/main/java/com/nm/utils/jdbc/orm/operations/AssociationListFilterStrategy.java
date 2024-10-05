package com.nm.utils.jdbc.orm.operations;

import java.util.List;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface AssociationListFilterStrategy {
	public List<Object> filter(List<Object> all) throws Exception;
}
