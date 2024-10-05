package com.rm.soa.clients.criterias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.DaoClient;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.model.clients.criterias.ClientCriterias;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class ClientFinderProcessor {

	@Autowired
	@Qualifier(ClientFinder.ClientFinder)
	private Map<String, ClientFinder> strategies = new HashMap<String, ClientFinder>();
	@Autowired
	private DaoClient daoClient;

	protected Collection<String> getStrategies() {
		return Arrays.asList(ClientFinder.ClientFinderAgeOfVisit,//
				ClientFinder.ClientFinderAgeOfOrder,//
				ClientFinder.ClientFinderAmountOrder,//
				ClientFinder.ClientFinderOrderCount,//
				ClientFinder.ClientFinderFilleulCount,//
				ClientFinder.ClientFinderClientId,//
				ClientFinder.ClientFinderClientPosition,//
				ClientFinder.ClientFinderEvent
				);
	}

	public Collection<Long> find(ClientCriterias criterias) {
		ClientFinderContext context = new ClientFinderContext();
		return find(criterias, context);
	}

	public Collection<Long> find(ClientCriterias criterias, ClientFinderContext context) {
		for (String s : getStrategies()) {
			strategies.get(s).find(criterias, context);
		}
		if (context.getIsOk() != null && !context.getIsOk()) {
			return new ArrayList<Long>();
		}
		//
		ClientQueryBuilder query = ClientQueryBuilder.get();
		for (ClientCriteriaType type : context.getQueries().keySet()) {
			query.withIdIn(context.getQueries().get(type));
		}
		return daoClient.findIds(query);
	}

}
