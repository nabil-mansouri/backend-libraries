package com.nm.dictionnary.daos;

import java.util.Arrays;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.dictionnary.constants.EnumDictionnaryState;
import com.nm.dictionnary.models.DictionnaryEntry;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderDictionnaryEntry extends AbstractQueryBuilder<QueryBuilderDictionnaryEntry> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderDictionnaryEntry(Class<? extends DictionnaryEntry> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderDictionnaryEntry get() {
		return new QueryBuilderDictionnaryEntry(DictionnaryEntry.class);
	}

	public QueryBuilderDictionnaryEntry withKey(String key) {
		this.criteria.add(Restrictions.eq("key", key));
		return this;
	}

	public QueryBuilderDictionnaryEntry withKey(Collection<String> key) {
		this.criteria.add(Restrictions.in("key", key));
		return this;
	}

	public QueryBuilderDictionnaryEntry withKey(String... key) {
		this.criteria.add(Restrictions.in("key", Arrays.asList(key)));
		return this;
	}

	public QueryBuilderDictionnaryEntry withState(EnumDictionnaryState state) {
		this.criteria.add(Restrictions.eq("state", state));
		return this;
	}

	public QueryBuilderDictionnaryEntry withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public QueryBuilderDictionnaryEntry withDictionary(QueryBuilderDictionnary query) {
		createAlias("dictionnary", "dictionnary");
		this.criteria.add(Subqueries.propertyIn("dictionnary.id", query.withIdProjection().getQuery()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
