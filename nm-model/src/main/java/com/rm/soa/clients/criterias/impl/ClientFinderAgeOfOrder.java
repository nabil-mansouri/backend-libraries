package com.rm.soa.clients.criterias.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.contract.orders.constants.OrderStateType;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.dao.clients.views.ClientAgeOrderQueryBuilder;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.model.clients.criterias.CriteriaRule;
import com.rm.model.clients.criterias.CriteriaRuleDuration;
import com.rm.model.clients.criterias.CriteriaRules;
import com.rm.soa.clients.criterias.ClientFinder;
import com.rm.soa.clients.criterias.ClientFinderContext;
import com.rm.utils.dates.PeriodUtils;

/***
 * 
 * @author Nabil
 * 
 */
@Component(ClientFinder.ClientFinderAgeOfOrder)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderAgeOfOrder implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.AgeOfOrder)) {
			CriteriaRules rules = criterias.getRules().get(ClientCriteriaType.AgeOfOrder);
			ClientAgeOrderQueryBuilder q2 = ClientAgeOrderQueryBuilder.get().withType(OrderStateType.Paid);
			//
			q2.withDisjunction();
			for (CriteriaRule rule : rules.getRules()) {
				if (rule instanceof CriteriaRuleDuration) {
					CriteriaRuleDuration d = (CriteriaRuleDuration) rule;
					Date fromDate = PeriodUtils.toDate(d.getOriginal(), d.getTo());
					Date toDate = PeriodUtils.toDate(d.getOriginal(), d.getFrom());
					q2.withDisjunctionDateBetween(fromDate, toDate);
					q2.withDisjunctionDateGE(fromDate);
					q2.withDisjunctionDateLE(toDate);
				}
			}
			q2.withClientIdProjection();
			//
			ClientQueryBuilder q4 = ClientQueryBuilder.get();
			//
			q4.withDisjunction();
			q4.withDisjunctionIdIn(q2);
			q4.withIdProjection();
			context.put(ClientCriteriaType.AgeOfOrder, q4);
		}

	}
}
