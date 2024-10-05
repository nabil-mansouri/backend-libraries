package com.nm.utils.hibernate.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

import com.nm.utils.hibernate.IQueryBuilder;
import com.nm.utils.hibernate.IQueryRange;

/**
 * 
 * @author Nabil
 * 
 */
public abstract class AbstractQueryBuilder<T extends AbstractQueryBuilder<T>> implements IQueryBuilder, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long first;
	protected Long limit;
	protected final Set<String> aliases = new HashSet<String>();
	protected String mainAlias = getClass().getSimpleName();
	protected Log log = LogFactory.getLog(getClass());
	protected Disjunction disjunction;
	protected Conjunction conjunction;

	public T withConjunctionOfDisjunction() {
		if (this.disjunction == null) {
			this.withDisjunction();
		}
		this.conjunction = Restrictions.and();
		this.disjunction.add(conjunction);
		return getThis();
	}

	public T withCreatedLe(Date next) {
		this.getQuery().add(Restrictions.le("createdAt", next));
		return getThis();
	}

	public T withCreatedGt(Date next) {
		if (next != null) {
			this.getQuery().add(Restrictions.gt("createdAt", next));
		}
		return getThis();
	}

	public String getMainAlias() {
		return mainAlias;
	}

	protected String wrapMain(String property) {
		return String.format("%s.%s", getMainAlias(), property);
	}

	@SuppressWarnings("unchecked")
	public final T setLimit(Long limit) {
		this.limit = limit;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T setFirst(Long first) {
		this.first = first;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final T setOffset(Long first) {
		this.first = first;
		return (T) this;
	}

	/**
	 * 
	 * @param property
	 * @return
	 * @see CriteriaQueryTranslator.generateSQLAlias() for underscore
	 */
	protected String wrapMainNative(String property) {
		return String.format("%s_.%s", getMainAlias(), property);
	}

	@SuppressWarnings("unchecked")
	protected T getThis() {
		return (T) this;
	}

	public T withMainAlias(String mainAlias) {
		this.mainAlias = mainAlias;
		return getThis();
	}

	public final T createAlias(String assoc, String alias) {
		if (!aliases.contains(alias)) {
			getQuery().createAlias(assoc, alias);
			aliases.add(alias);
		}
		return getThis();
	}

	public final T createAlias(String assoc, String alias, JoinType type) {
		if (!aliases.contains(assoc)) {
			getQuery().createAlias(assoc, alias, type);
			aliases.add(assoc);
		}
		return getThis();
	}

	public Long getFirst(boolean force) {
		if (force && first == null) {
			return 0l;
		}
		return first;
	}

	public Long getLimit() {
		return limit;
	}

	public T withRange(IQueryRange range) {
		this.withFirst(range.getFirst());
		this.withLimit(range.getCount());
		return getThis();
	}

	public T withLimit(Long limit) {
		this.limit = limit;
		return getThis();
	}

	public T withFirst(Long first) {
		this.first = limit;
		return getThis();
	}

	public T withResultTransformer(ResultTransformer trans) {
		this.getQuery().setResultTransformer(trans);
		return getThis();
	}

	public boolean hasLimit() {
		return limit != null;
	}

	public abstract DetachedCriteria getQuery();

	public T withDateProjection() {
		this.getQuery().setProjection(Projections.property("date"));
		return getThis();
	}

	public final T withDisjunction() {
		this.disjunction = Restrictions.or();
		this.getQuery().add(disjunction);
		return getThis();
	}

	public T withId(Long id) {
		this.getQuery().add(Restrictions.idEq(id));
		return getThis();
	}

	public T withDisjunctionId(Long id) {
		disjunction.add(Restrictions.idEq(id));
		return getThis();
	}

	public final T withNotDisjunction() {
		this.disjunction = Restrictions.or();
		this.getQuery().add(Restrictions.not(disjunction));
		return getThis();
	}

	public T withNodeIdProjection() {
		this.getQuery().setProjection(Projections.property("nodeId"));
		return getThis();
	}

	public T withNodeIds(Collection<BigInteger> ids) {
		this.getQuery().add(Restrictions.in("nodeId", ids));
		return getThis();
	}

	public T withIdProjection() {
		this.getQuery().setProjection(Projections.id());
		return getThis();
	}

	@SuppressWarnings("unchecked")
	public T clone() {
		return (T) SerializationUtils.clone(this);
	}

	public T withNotId(Long id) {
		this.getQuery().add(Restrictions.not(Restrictions.idEq(id)));
		return getThis();
	}

}
