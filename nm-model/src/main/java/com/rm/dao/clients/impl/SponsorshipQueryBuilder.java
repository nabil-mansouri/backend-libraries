package com.rm.dao.clients.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.rm.model.clients.Sponsorship;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class SponsorshipQueryBuilder extends AbstractQueryBuilder<SponsorshipQueryBuilder> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SponsorshipQueryBuilder() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(Sponsorship.class, getMainAlias());

	public static SponsorshipQueryBuilder get() {
		return new SponsorshipQueryBuilder();
	}

	public SponsorshipQueryBuilder withCountGroupBySponsor(Double from, Double to) {
		if (from != null || to != null) {
			String groupBy = "";
			if (from == null) {
				groupBy = String.format("sponsor_id having count(*) <= %s", to);
			} else if (to == null) {
				groupBy = String.format("sponsor_id having count(*) >= %s", from);
			} else {
				groupBy = String.format("sponsor_id having count(*) BETWEEN %s AND %s", from, to);
			}
			String[] alias = new String[] { "sponsor_id" };
			Type[] types = new Type[] { StandardBasicTypes.LONG };
			this.criteria.setProjection(Projections.sqlGroupProjection("sponsor_id", groupBy, alias, types));
		}
		return this;
	}

	public SponsorshipQueryBuilder withDisjunctionDateBetween(Date from, Date to) {
		if (from != null && to != null) {
			this.disjunction.add(Restrictions.between(wrapMain("created"), from, to));
		}
		return this;
	}

	public SponsorshipQueryBuilder withDisjunctionDateGE(Date from) {
		if (from != null) {
			this.disjunction.add(Restrictions.ge(wrapMain("created"), from));
		}
		return this;
	}

	public SponsorshipQueryBuilder withDisjunctionDateLE(Date to) {
		if (to != null) {
			this.disjunction.add(Restrictions.le(wrapMain("created"), to));
		}
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
