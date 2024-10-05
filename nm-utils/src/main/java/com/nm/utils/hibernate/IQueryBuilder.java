package com.nm.utils.hibernate;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 
 * @author Nabil
 */
public interface IQueryBuilder {
	public Long getFirst(boolean force);

	public Long getLimit();

	public boolean hasLimit();

	public IQueryBuilder withIdProjection();

	public DetachedCriteria getQuery();
}
