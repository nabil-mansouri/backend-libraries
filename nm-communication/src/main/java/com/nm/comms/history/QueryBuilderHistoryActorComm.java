package com.nm.comms.history;

import org.hibernate.criterion.Subqueries;

import com.nm.app.history.QueryBuilderHistoryActor;
import com.nm.comms.daos.CommunicationActorQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderHistoryActorComm extends QueryBuilderHistoryActor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueryBuilderHistoryActorComm(Class<HistoryActorComm> class1) {
		super(class1);
	}

	public static QueryBuilderHistoryActorComm getComm() {
		return new QueryBuilderHistoryActorComm(HistoryActorComm.class);
	}

	public QueryBuilderHistoryActorComm withActor(CommunicationActorQueryBuilder query) {
		createAlias("actor", "actor");
		getQuery().add(Subqueries.propertyIn("actor.id", query.withIdProjection().getQuery()));
		return this;
	}
}
