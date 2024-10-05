package com.nm.comms.daos;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.comms.models.Message;
import com.nm.comms.models.CommunicationSubject;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class MessageQueryBuilder extends AbstractQueryBuilder<MessageQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MessageQueryBuilder(Class<? extends Message> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static MessageQueryBuilder get() {
		return new MessageQueryBuilder(Message.class);
	}

	public MessageQueryBuilder withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public MessageQueryBuilder withTime(MessageTriggerQueryBuilder query) {
		createAlias("time", "time");
		this.criteria.add(Subqueries.propertyIn("time.id", query.withIdProjection().getQuery()));
		return this;
	}

	public MessageQueryBuilder withConcerned(CommunicationSubjectQueryBuilder query) {
		createAlias("concerned", "concerned");
		this.criteria.add(Subqueries.propertyIn("concerned.id", query.withIdProjection().getQuery()));
		return this;
	}

	public MessageQueryBuilder withConcerned(CommunicationSubject concerned) {
		this.criteria.add(Restrictions.eq("concerned", concerned));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
