package com.nm.comms.daos;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;

import com.nm.comms.constants.CommunicationType;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorAnonymous;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.models.CommunicationMedium;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoCommunicationImpl extends AbstractGenericDao<Communication, Long>implements DaoCommunication {

	@Override
	protected Class<Communication> getClassName() {
		return Communication.class;
	}

	public CommunicationActorMail getOrCreateMail(String mail) {
		CommunicationActorMail any = new CommunicationActorMail();
		any.setEmail(mail);
		//
		CommunicationActorQueryBuilder query = CommunicationActorQueryBuilder.getMail().withMail(mail);
		@SuppressWarnings("unchecked")
		Collection<CommunicationActorMail> all = (Collection<CommunicationActorMail>) getHibernateTemplate()
				.findByCriteria(query.getQuery());
		if (all.isEmpty()) {
			getHibernateTemplate().save(any);
			return any;
		} else {
			return all.iterator().next();
		}
	}

	public CommunicationMedium getOrCreateMedium(CommunicationType type) {
		CommunicationMediumQueryBuilder query = CommunicationMediumQueryBuilder.get().withType(type);
		@SuppressWarnings("unchecked")
		Collection<CommunicationMedium> all = (Collection<CommunicationMedium>) getHibernateTemplate()
				.findByCriteria(query.getQuery());
		if (all.isEmpty()) {
			CommunicationMedium medium = new CommunicationMedium();
			medium.setType(type);
			getHibernateTemplate().save(medium);
			return medium;
		} else {
			return all.iterator().next();
		}
	}

	public CommunicationActor getOrCreateAny() {
		CommunicationActorAnonymous any = new CommunicationActorAnonymous();
		@SuppressWarnings("unchecked")
		Collection<CommunicationActorAnonymous> all = (Collection<CommunicationActorAnonymous>) getHibernateTemplate()
				.findByCriteria(DetachedCriteria.forClass(CommunicationActorAnonymous.class));
		if (all.isEmpty()) {
			getHibernateTemplate().save(any);
			return any;
		} else {
			return all.iterator().next();
		}
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
