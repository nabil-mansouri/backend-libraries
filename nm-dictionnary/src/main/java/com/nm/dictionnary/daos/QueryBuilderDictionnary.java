package com.nm.dictionnary.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.dictionnary.constants.EnumDictionnaryDomain;
import com.nm.dictionnary.models.Dictionnary;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderDictionnary extends AbstractQueryBuilder<QueryBuilderDictionnary> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderDictionnary(Class<? extends Dictionnary> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderDictionnary get() {
		return new QueryBuilderDictionnary(Dictionnary.class);
	}

	public QueryBuilderDictionnary withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public QueryBuilderDictionnary withDomain(EnumDictionnaryDomain domain) {
		this.criteria.add(Restrictions.eq("domain", domain));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
