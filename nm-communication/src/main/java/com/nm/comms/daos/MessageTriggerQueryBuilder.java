package com.nm.comms.daos;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.comms.models.MessageTrigger;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class MessageTriggerQueryBuilder extends AbstractQueryBuilder<MessageTriggerQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MessageTriggerQueryBuilder(Class<? extends MessageTrigger> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static MessageTriggerQueryBuilder get() {
		return new MessageTriggerQueryBuilder(MessageTrigger.class);
	}

	public MessageTriggerQueryBuilder withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public MessageTriggerQueryBuilder withNextLe(Date next) {
		this.criteria.add(Restrictions.le("next", next));
		return this;
	}

	public MessageTriggerQueryBuilder withNextGt(Date next) {
		if (next != null) {
			this.criteria.add(Restrictions.gt("next", next));
		}
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
