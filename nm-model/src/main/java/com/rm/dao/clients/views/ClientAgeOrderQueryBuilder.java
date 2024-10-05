package com.rm.dao.clients.views;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.rm.contract.orders.constants.OrderStateType;
import com.rm.dao.clients.criterias.CriteriaRuleDurationQueryBuilder;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.model.clients.views.ClientAgeOrder;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class ClientAgeOrderQueryBuilder extends AbstractQueryBuilder<ClientAgeOrderQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria;

	public static ClientAgeOrderQueryBuilder get() {
		return new ClientAgeOrderQueryBuilder(ClientAgeOrder.class);
	}

	private ClientAgeOrderQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	public ClientAgeOrderQueryBuilder withDisjunctionExists(CriteriaRuleDurationQueryBuilder q) {
		this.disjunction.add(Subqueries.exists(q.withIdProjection().getQuery()));
		return this;
	}

	public ClientAgeOrderQueryBuilder withJoinClient(ClientQueryBuilder q) {
		this.disjunction.add(Property.forName(wrapMain("idClient")).eqProperty(q.getClientId()));
		return this;
	}

	public ClientAgeOrderQueryBuilder withDisjunctionNotExists(CriteriaRuleDurationQueryBuilder q) {
		this.disjunction.add(Subqueries.notExists(q.withIdProjection().getQuery()));
		return this;
	}

	public ClientAgeOrderQueryBuilder withType(OrderStateType type) {
		this.criteria.add(Restrictions.eq(wrapMain("type"), type));
		return this;
	}

	public ClientAgeOrderQueryBuilder withClientIdProjection() {
		this.criteria.setProjection(Projections.property(wrapMain("idClient")));
		return this;
	}

	public ClientAgeOrderQueryBuilder withDisjunctionDurationGE(Double from) {
		if (from != null) {
			this.disjunction.add(Restrictions.ge(wrapMain("duration"), from));
		}
		return this;
	}

	public ClientAgeOrderQueryBuilder withDisjunctionDurationLE(Double to) {
		if (to != null) {
			this.disjunction.add(Restrictions.le(wrapMain("duration"), to));
		}
		return this;
	}

	public ClientAgeOrderQueryBuilder withDisjunctionDurationBetween(Double from, Double to) {
		if (from != null && to != null) {
			this.disjunction.add(Restrictions.between(wrapMain("duration"), from, to));
		}
		return this;
	}

	public ClientAgeOrderQueryBuilder withDisjunctionDateGE(Date from) {
		if (from != null) {
			this.disjunction.add(Restrictions.ge(wrapMain("lastDate"), from));
		}
		return this;
	}

	public ClientAgeOrderQueryBuilder withDisjunctionDateLE(Date to) {
		if (to != null) {
			this.disjunction.add(Restrictions.le(wrapMain("lastDate"), to));
		}
		return this;
	}

	public ClientAgeOrderQueryBuilder withDisjunctionDateBetween(Date from, Date to) {
		if (from != null && to != null) {
			this.disjunction.add(Restrictions.between(wrapMain("lastDate"), from, to));
		}
		return this;
	}

	public String getDurationName() {
		return wrapMain("duration");
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
