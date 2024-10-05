package com.nm.comms.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.comms.models.CommunicationSubject;
import com.nm.comms.models.CommunicationSubjectAnything;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CommunicationSubjectQueryBuilder extends AbstractQueryBuilder<CommunicationSubjectQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CommunicationSubjectQueryBuilder(Class<? extends CommunicationSubject> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static CommunicationSubjectQueryBuilder get() {
		return new CommunicationSubjectQueryBuilder(CommunicationSubject.class);
	}

	public static CommunicationSubjectQueryBuilder getSubjectAnyThing() {
		return new CommunicationSubjectQueryBuilder(CommunicationSubjectAnything.class);
	}

	public CommunicationSubjectQueryBuilder withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
