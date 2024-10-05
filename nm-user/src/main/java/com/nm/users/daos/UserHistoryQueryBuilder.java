package com.nm.users.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.nm.users.models.UserHistory;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserHistoryQueryBuilder extends AbstractQueryBuilder<UserHistoryQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria = DetachedCriteria.forClass(UserHistory.class);

	public static UserHistoryQueryBuilder get() {
		return new UserHistoryQueryBuilder();
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public UserHistoryQueryBuilder withUser(Long user) {
		createAlias("user", "user");
		this.criteria.add(Restrictions.eq("user.id", user));
		return this;
	}

	public UserHistoryQueryBuilder withArticleProjection() {
		createAlias("articles", "articles");
		createAlias("articles.article", "article");
		this.criteria.setProjection(Projections.property("article.id"));
		return this;
	}
}
