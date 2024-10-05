package com.nm.comms.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.comms.constants.CommunicationType;
import com.nm.comms.models.CommunicationMedium;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CommunicationMediumQueryBuilder extends AbstractQueryBuilder<CommunicationMediumQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CommunicationMediumQueryBuilder(Class<? extends CommunicationMedium> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static CommunicationMediumQueryBuilder get() {
		return new CommunicationMediumQueryBuilder(CommunicationMedium.class);
	}

	public CommunicationMediumQueryBuilder withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public CommunicationMediumQueryBuilder withType(CommunicationType type) {
		this.criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
