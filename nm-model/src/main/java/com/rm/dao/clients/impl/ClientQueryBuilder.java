package com.rm.dao.clients.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.joda.time.MutableDateTime;

import com.google.common.base.Strings;
import com.rm.contract.clients.beans.ClientFilterBean;
import com.rm.dao.commons.impl.AddressQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.model.clients.Client;
import com.rm.utils.dao.impl.AbstractQueryBuilder;
import com.rm.utils.dates.DateUtilsMore;

/**
 * 
 * @author Nabil
 * 
 */
public class ClientQueryBuilder extends AbstractQueryBuilder<ClientQueryBuilder> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientQueryBuilder() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(Client.class, getMainAlias());

	public static ClientQueryBuilder get() {
		return new ClientQueryBuilder();
	}

	public String getClientId() {
		return wrapMain("id");
	}

	public ClientQueryBuilder withIdIn(AbstractQueryBuilder<?> dc) {
		this.criteria.add(Subqueries.propertyIn(wrapMain("id"), dc.getQuery()));
		return this;
	}

	public ClientQueryBuilder withDisjunctionIdIn(AbstractQueryBuilder<?> dc) {
		this.disjunction.add(Subqueries.propertyIn(wrapMain("id"), dc.getQuery()));
		return this;
	}

	public ClientQueryBuilder withDisjunctionNotExists(AbstractQueryBuilder<?> dc) {
		this.disjunction.add(Subqueries.notExists(dc.withIdProjection().getQuery()));
		return this;
	}

	public ClientQueryBuilder withBirthdayMonth(Date date) {
		int begin = new MutableDateTime(date).getMonthOfYear();
		this.criteria.add(Restrictions.eq(wrapMain("birthdayMonth"), begin));
		return this;
	}

	public ClientQueryBuilder withBirthdayDay(Date date) {
		int begin = new MutableDateTime(date).getDayOfMonth();
		this.criteria.add(Restrictions.eq(wrapMain("birthdayDay"), begin));
		return this;
	}

	public ClientQueryBuilder withSubscribedMonth(Date date) {
		int begin = new MutableDateTime(date).getMonthOfYear();
		this.criteria.add(Restrictions.eq(wrapMain("subscribedMonth"), begin));
		return this;
	}

	public ClientQueryBuilder withAddress(AddressQueryBuilder query) {
		createAlias(wrapMain("adresses"), "adresses");
		this.criteria.add(Subqueries.propertyIn("adresses.id", query.withIdProjection().getQuery()));
		return this;
	}

	public ClientQueryBuilder withSubscribedDay(Date date) {
		int begin = new MutableDateTime(date).getDayOfMonth();
		this.criteria.add(Restrictions.eq(wrapMain("subscribedDay"), begin));
		return this;
	}

	public ClientQueryBuilder withSubscribedEqDay(Date date) {
		Date begin = DateUtilsMore.startDays(date);
		Date end = DateUtilsMore.endDays(date);
		this.criteria.add(Restrictions.between(wrapMain("subscribed"), begin, end));
		return this;
	}

	public ClientQueryBuilder withNotInDiscount(DiscountTrackingQueryBuilder query) {
		criteria.add(Subqueries.propertyNotIn(wrapMain("id"), query.withProjectionClientId().getQuery()));
		return this;
	}

	public ClientQueryBuilder withText(String text) {
		criteria.add(Restrictions.like(wrapMain("search"), text, MatchMode.ANYWHERE));
		return this;
	}

	public ClientQueryBuilder withReference(String ref) {
		criteria.add(Restrictions.eq(wrapMain("reference"), ref));
		return this;
	}

	public ClientQueryBuilder withIds(Collection<Long> ids) {
		if (!ids.isEmpty()) {
			criteria.add(Restrictions.in(wrapMain("id"), ids));
		}
		return this;
	}

	public ClientQueryBuilder withFilter(ClientFilterBean request) {
		if (!Strings.isNullOrEmpty(request.getText())) {
			this.withText(request.getText());
		}
		if (!Strings.isNullOrEmpty(request.getReference())) {
			this.withReference(request.getReference());
		}
		withRange(request);
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
