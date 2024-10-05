package com.nm.dictionnary.daos;

import java.math.BigInteger;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.google.common.base.Strings;
import com.nm.dictionnary.dtos.DtoDictionnaryFilter;
import com.nm.dictionnary.models.DictionnaryEntity;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderDictionnaryEntity extends AbstractQueryBuilder<QueryBuilderDictionnaryEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderDictionnaryEntity(Class<? extends DictionnaryEntity> clazz) {
		criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderDictionnaryEntity get() {
		return new QueryBuilderDictionnaryEntity(DictionnaryEntity.class);
	}

	public QueryBuilderDictionnaryEntity withNodeIdProjection() {
		this.criteria.setProjection(Projections.property("nodeId"));
		return this;
	}

	public QueryBuilderDictionnaryEntity withAboutIdProjection() {
		createAlias("about", "about");
		this.criteria.setProjection(Projections.property("about.id"));
		return this;
	}

	public QueryBuilderDictionnaryEntity withAboutId(BigInteger ids) {
		createAlias("about", "about");
		this.criteria.add(Restrictions.eq("about.id", ids));
		return this;
	}

	public QueryBuilderDictionnaryEntity withFilter(DtoDictionnaryFilter filter) {
		int cpt = 0;
		QueryBuilderDictionnaryValue sub = QueryBuilderDictionnaryValue.get().withDisjunction();
		for (Entry<String, Integer> entry : filter.getNumbers().entrySet()) {
			if (entry.getValue() != null) {
				sub.withDisNormalizedEq(entry.getKey(), entry.getValue());
				cpt++;
			}
		}
		for (Entry<String, String> entry : filter.getTexts().entrySet()) {
			if (!Strings.isNullOrEmpty(entry.getValue())) {
				sub.withDisNormalizedEq(entry.getKey(), entry.getValue());
				cpt++;
			}
		}
		if (cpt > 0) {
			withValueHaving(cpt, sub);
		}
		return this;
	}

	public QueryBuilderDictionnaryEntity withValue(QueryBuilderDictionnaryValue query) {
		createAlias("values", "values");
		this.criteria.add(Subqueries.propertyIn("values.id", query.withIdProjection().getQuery()));
		return this;
	}

	public String getEntityId() {
		return String.format("%s.id", getMainAlias());
	}

	public QueryBuilderDictionnaryEntity withValueHaving(int nbTagUsed, QueryBuilderDictionnaryValue value) {
		createAlias("values", "values");
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		this.criteria.add(Subqueries.eq(new Long(nbTagUsed), value.withJoinByEntity(this).withCountByRow().getQuery()));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
