package com.nm.permissions.daos;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.auths.models.User;
import com.nm.auths.models.UserGroup;
import com.nm.permissions.models.Subject;
import com.nm.permissions.models.SubjectGroup;
import com.nm.permissions.models.SubjectUser;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class SubjectQueryBuilder extends AbstractQueryBuilder<SubjectQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final DetachedCriteria criteria;

	public static SubjectQueryBuilder getSubject() {
		return new SubjectQueryBuilder(Subject.class);
	}

	public static SubjectQueryBuilder getSubjectGroups() {
		return new SubjectQueryBuilder(SubjectGroup.class);
	}

	public static SubjectQueryBuilder getSubjectUser() {
		return new SubjectQueryBuilder(SubjectUser.class);
	}

	public SubjectQueryBuilder(Class<? extends Subject> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz);
	}

	public DetachedCriteria getCriteria() {
		return criteria;
	}

	public SubjectQueryBuilder withGroup(Long group) {
		createAlias("group", "group");
		criteria.add(Restrictions.eq("group.id", group));
		return this;
	}

	public SubjectQueryBuilder withGroup(UserGroup group) {
		criteria.add(Restrictions.eq("group", group));
		return this;
	}

	public SubjectQueryBuilder withGroups(Collection<UserGroup> group) {
		criteria.add(Restrictions.in("group", group));
		return this;
	}

	public SubjectQueryBuilder withUser(User user) {
		criteria.add(Restrictions.eq("user", user));
		return this;
	}

	public SubjectQueryBuilder withUser(Long user) {
		createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id", user));
		return this;
	}

	@Override
	public DetachedCriteria getQuery() {
		return criteria;
	}
}
