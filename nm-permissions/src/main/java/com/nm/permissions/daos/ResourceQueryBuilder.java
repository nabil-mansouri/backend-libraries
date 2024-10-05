package com.nm.permissions.daos;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.models.Resource;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class ResourceQueryBuilder extends AbstractQueryBuilder<ResourceQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final DetachedCriteria criteria;

	public static ResourceQueryBuilder get() {
		return new ResourceQueryBuilder(Resource.class);
	}

	public ResourceQueryBuilder(Class<? extends Resource> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz);
	}

	@Override
	public DetachedCriteria getQuery() {
		return criteria;
	}

	public ResourceQueryBuilder withType(ResourceType type) {
		criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public ResourceQueryBuilder withType(Collection<ResourceType> type) {
		criteria.add(Restrictions.in("type", type));
		return this;
	}
}
