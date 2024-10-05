package com.rm.soa.clients.criterias;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.contract.clients.constants.ClientEventType;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class ClientFinderContext {
	private Date relativeTo = new Date();
	private Collection<ClientEventType> currentEvents = new HashSet<ClientEventType>();
	private Boolean isOk;

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public Collection<ClientEventType> getCurrentEvents() {
		return currentEvents;
	}

	public void setCurrentEvents(Collection<ClientEventType> currentEvents) {
		this.currentEvents = currentEvents;
	}

	public Date getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(Date relativeTo) {
		this.relativeTo = relativeTo;
	}

	private Map<ClientCriteriaType, AbstractQueryBuilder<?>> queries = new HashMap<ClientCriteriaType, AbstractQueryBuilder<?>>();

	public Map<ClientCriteriaType, AbstractQueryBuilder<?>> getQueries() {
		return queries;
	}

	public void setQueries(Map<ClientCriteriaType, AbstractQueryBuilder<?>> queries) {
		this.queries = queries;
	}

	public AbstractQueryBuilder<?> put(ClientCriteriaType key, AbstractQueryBuilder<?> value) {
		return queries.put(key, value);
	}

}
