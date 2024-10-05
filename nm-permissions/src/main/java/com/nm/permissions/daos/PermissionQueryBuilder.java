package com.nm.permissions.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.models.Permission;
import com.nm.permissions.models.Resource;
import com.nm.permissions.models.Subject;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class PermissionQueryBuilder extends AbstractQueryBuilder<PermissionQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final DetachedCriteria criteria;

	public static PermissionQueryBuilder getPermission() {
		return new PermissionQueryBuilder(Permission.class);
	}

	public PermissionQueryBuilder(Class<? extends Permission> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz);
	}

	@Override
	public DetachedCriteria getQuery() {
		return criteria;
	}

	public PermissionQueryBuilder withSubject(SubjectQueryBuilder group) {
		DetachedCriteria sub = group.getCriteria();
		sub.setProjection(Projections.id());
		criteria.createAlias("grid", "grid");
		criteria.createAlias("grid.subject", "subject");
		criteria.add(Subqueries.propertyIn("subject.id", sub));
		return this;
	}

	public PermissionQueryBuilder withAction(Action action) {
		criteria.add(Restrictions.eq("action", action));
		return this;
	}

	public PermissionQueryBuilder withResource(Resource resource) {
		criteria.createAlias("resource", "resource");
		criteria.createAlias("resource.allNodes", "resourceChild");
		Disjunction disjunction = Restrictions.or();
		disjunction.add(Restrictions.eq("resource.uuid", resource.getUuid()));
		disjunction.add(Restrictions.eq("resourceChild.uuid", resource));
		criteria.add(disjunction);
		return this;
	}

	public PermissionQueryBuilder withResource(ResourceType type) {
		criteria.createAlias("resource", "resource");
		criteria.add(Restrictions.eq("resource.type", type));
		return this;
	}

	public PermissionQueryBuilder withSubject(Subject subject) {
		criteria.createAlias("grid", "grid");
		criteria.createAlias("grid.subject", "subject");
		criteria.add(Restrictions.eq("subject", subject));
		return this;
	}
}
