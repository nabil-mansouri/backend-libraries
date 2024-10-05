package com.rm.soa.clients.criterias.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.soa.clients.criterias.ClientFinder;
import com.rm.soa.clients.criterias.ClientFinderContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(ClientFinder.ClientFinderBirthday)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderBirthday implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.BirthDay)) {
			ClientQueryBuilder q1 = ClientQueryBuilder.get();
			q1.withBirthdayDay(context.getRelativeTo());
			q1.withBirthdayMonth(context.getRelativeTo());
			//
			q1.withIdProjection();
			context.put(ClientCriteriaType.BirthDay, q1);
		}

	}
}
