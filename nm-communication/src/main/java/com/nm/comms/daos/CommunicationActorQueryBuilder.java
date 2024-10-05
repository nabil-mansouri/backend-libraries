package com.nm.comms.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorAnonymous;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CommunicationActorQueryBuilder extends AbstractQueryBuilder<CommunicationActorQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CommunicationActorQueryBuilder(Class<? extends CommunicationActor> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static CommunicationActorQueryBuilder get() {
		return new CommunicationActorQueryBuilder(CommunicationActor.class);
	}

	public static CommunicationActorQueryBuilder getAnonymous() {
		return new CommunicationActorQueryBuilder(CommunicationActorAnonymous.class);
	}

	public static CommunicationActorQueryBuilder getMail() {
		return new CommunicationActorQueryBuilder(CommunicationActorMail.class);
	}

	public CommunicationActorQueryBuilder withMail(String mail) {
		this.criteria.add(Restrictions.eq("email", mail));
		return this;
	}

	public CommunicationActorQueryBuilder withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
