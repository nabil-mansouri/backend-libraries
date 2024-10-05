package com.nm.dictionnary.daos;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.nm.dictionnary.constants.EnumDictionnaryState;
import com.nm.dictionnary.models.DictionnaryValue;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderDictionnaryValue extends AbstractQueryBuilder<QueryBuilderDictionnaryValue> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderDictionnaryValue(Class<? extends DictionnaryValue> clazz) {
		criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderDictionnaryValue get() {
		return new QueryBuilderDictionnaryValue(DictionnaryValue.class);
	}

	public QueryBuilderDictionnaryValue withState(EnumDictionnaryState state) {
		this.criteria.add(Restrictions.eq("state", state));
		return this;
	}

	public QueryBuilderDictionnaryValue withValueStart(String start) {
		this.criteria.add(Restrictions.ilike("value", start, MatchMode.START));
		return this;
	}

	public QueryBuilderDictionnaryValue withValueStartNormalize(String start) {
		this.criteria.add(Restrictions.ilike("normalizeValue", start, MatchMode.START));
		return this;
	}

	public QueryBuilderDictionnaryValue withValueEq(int start) {
		this.criteria.add(Restrictions.eq("valueInt", start));
		return this;
	}

	public QueryBuilderDictionnaryValue withDictionaryEntry(QueryBuilderDictionnaryEntry query) {
		createAlias("entry", "entry");
		this.criteria.add(Subqueries.propertyIn("entry.id", query.withIdProjection().getQuery()));
		return this;
	}

	public QueryBuilderDictionnaryValue withCountByRow() {
		createAlias("entities", "entity");
		//
		String[] alias = new String[] { "nb" };
		Type[] types = new Type[] { StandardBasicTypes.LONG };
		this.criteria.setProjection(Projections.sqlGroupProjection("count(*)", "entities_id", alias, types));
		return this;
	}

	public QueryBuilderDictionnaryValue withJoinByEntity(QueryBuilderDictionnaryEntity query) {
		createAlias("entities", "entity");
		this.criteria.add(Restrictions.eqProperty("entity.id", query.getEntityId()));
		return this;
	}

	public QueryBuilderDictionnaryValue withEntityIdIn(AbstractQueryBuilder<?> query) {
		createAlias("entities", "entity");
		this.criteria.add(Subqueries.propertyIn("entity.id", query.getQuery()));
		return this;
	}

	public QueryBuilderDictionnaryValue withDisNormalizedEq(String key, Integer value) {
		createAlias("entry", "entry");
		//
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.eq("valueInt", value));
		conjunction.add(Restrictions.eq("entry.key", key));
		disjunction.add(conjunction);
		return this;
	}

	public QueryBuilderDictionnaryValue withDisNormalizedEq(String key, String value) {
		createAlias("entry", "entry");
		DictionnaryValue v = new DictionnaryValue();
		v.setValue(value);
		//
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.eq("normalizeValue", v.getNormalizeValue()));
		conjunction.add(Restrictions.eq("entry.key", key));
		disjunction.add(conjunction);
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
