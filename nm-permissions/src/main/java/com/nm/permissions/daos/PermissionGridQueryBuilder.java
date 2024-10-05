package com.nm.permissions.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Subject;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class PermissionGridQueryBuilder extends AbstractQueryBuilder<PermissionGridQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final DetachedCriteria criteria;

	public static PermissionGridQueryBuilder get() {
		return new PermissionGridQueryBuilder(PermissionGrid.class);
	}

	public PermissionGridQueryBuilder(Class<? extends PermissionGrid> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz);
	}

	@Override
	public DetachedCriteria getQuery() {
		return criteria;
	}

	public PermissionGridQueryBuilder withPermissions(PermissionQueryBuilder query) {
		createAlias("permissions", "permissions");
		criteria.add(Subqueries.propertyIn("permissions.id", query.withIdProjection().getQuery()));
		return this;
	}

	public PermissionGridQueryBuilder withSubject(SubjectQueryBuilder group) {
		criteria.createAlias("subject", "subject");
		criteria.add(Subqueries.propertyIn("subject.id", group.withIdProjection().getCriteria()));
		return this;
	}

	public PermissionGridQueryBuilder withSubjectDisjunction(SubjectQueryBuilder... group) {
		criteria.createAlias("subject", "subject");
		Disjunction disjunction = Restrictions.or();
		for (SubjectQueryBuilder g : group) {
			disjunction.add(Subqueries.propertyIn("subject.id", g.withIdProjection().getCriteria()));
		}
		criteria.add(disjunction);
		return this;
	}

	public PermissionGridQueryBuilder withSubject(Subject subject) {
		criteria.add(Restrictions.eq("subject", subject));
		return this;
	}
}
