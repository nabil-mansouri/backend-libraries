package com.nm.comms.history;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.app.history.QueryBuilderHistorySubject;
import com.nm.comms.constants.MessageBoxType;
import com.nm.comms.daos.MessageQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderHistorySubjectComm extends QueryBuilderHistorySubject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueryBuilderHistorySubjectComm(Class<HistorySubjectComm> class1) {
		super(class1);
	}

	public static QueryBuilderHistorySubjectComm getComm() {
		return new QueryBuilderHistorySubjectComm(HistorySubjectComm.class);
	}

	public QueryBuilderHistorySubjectComm withActor(MessageQueryBuilder query) {
		createAlias("message", "message");
		getQuery().add(Subqueries.propertyIn("message.id", query.withIdProjection().getQuery()));
		return this;
	}

	public QueryBuilderHistorySubjectComm withType(MessageBoxType type) {
		getQuery().add(Restrictions.eq("type", type));
		return this;
	}
}
