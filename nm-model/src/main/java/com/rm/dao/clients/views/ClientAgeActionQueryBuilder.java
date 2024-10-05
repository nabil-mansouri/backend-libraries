package com.rm.dao.clients.views;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.rm.contract.clients.constants.ClientActionType;
import com.rm.dao.clients.criterias.CriteriaRuleDurationQueryBuilder;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.model.clients.views.ClientAgeAction;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class ClientAgeActionQueryBuilder extends AbstractQueryBuilder<ClientAgeActionQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria;

	public static ClientAgeActionQueryBuilder get() {
		return new ClientAgeActionQueryBuilder(ClientAgeAction.class);
	}

	private ClientAgeActionQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	public ClientAgeActionQueryBuilder withDisjunctionDurationGE(Double from) {
		if (from != null) {
			this.disjunction.add(Restrictions.ge(wrapMain("duration"), from));
		}
		return this;
	}

	public ClientAgeActionQueryBuilder withDisjunctionDurationLE(Double to) {
		if (to != null) {
			this.disjunction.add(Restrictions.le(wrapMain("duration"), to));
		}
		return this;
	}

	public ClientAgeActionQueryBuilder withDisjunctionDurationBetween(Double from, Double to) {
		if (from != null && to != null) {
			this.disjunction.add(Restrictions.between(wrapMain("duration"), from, to));
		}
		return this;
	}

	public ClientAgeActionQueryBuilder withDisjunctionDateGE(Date from) {
		if (from != null) {
			this.disjunction.add(Restrictions.ge(wrapMain("actionDate"), from));
		}
		return this;
	}

	public ClientAgeActionQueryBuilder withDisjunctionDateLE(Date to) {
		if (to != null) {
			this.disjunction.add(Restrictions.le(wrapMain("actionDate"), to));
		}
		return this;
	}

	public ClientAgeActionQueryBuilder withDisjunctionDateBetween(Date from, Date to) {
		if (from != null && to != null) {
			this.disjunction.add(Restrictions.between(wrapMain("actionDate"), from, to));
		}
		return this;
	}
	public ClientAgeActionQueryBuilder withDisjunctionExists(CriteriaRuleDurationQueryBuilder q) {
		this.disjunction.add(Subqueries.exists(q.withIdProjection().getQuery()));
		return this;
	}

	public ClientAgeActionQueryBuilder withJoinClient(ClientQueryBuilder q) {
		this.disjunction.add(Property.forName(wrapMain("idClient")).eqProperty(q.getClientId()));
		return this;
	}

	public ClientAgeActionQueryBuilder withDisjunctionNotExists(CriteriaRuleDurationQueryBuilder q) {
		this.disjunction.add(Subqueries.notExists(q.withIdProjection().getQuery()));
		return this;
	}

	public ClientAgeActionQueryBuilder withType(ClientActionType type) {
		this.criteria.add(Restrictions.eq(wrapMain("type"), type));
		return this;
	}

	public ClientAgeActionQueryBuilder withClientIdProjection() {
		this.criteria.setProjection(Projections.property(wrapMain("idClient")));
		return this;
	}

	public String getDurationName() {
		return wrapMain("duration");
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
