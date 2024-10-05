package com.nm.cms.dao;

import java.math.BigInteger;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.cms.models.CmsContents;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderCmsContent extends AbstractQueryBuilder<QueryBuilderCmsContent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderCmsContent(Class<? extends CmsContents> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderCmsContent get() {
		return new QueryBuilderCmsContent(CmsContents.class);
	}

	public QueryBuilderCmsContent withAboutId(Collection<BigInteger> ids) {
		createAlias("cms", "cms");
		createAlias("cms.subject", "subject");
		this.criteria.add(Restrictions.in("subject.id", ids));
		return this;
	}

	public QueryBuilderCmsContent withCms(QueryBuilderCms query) {
		this.criteria.add(Subqueries.propertyIn("id", query.withContentIdProjection().getQuery()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
