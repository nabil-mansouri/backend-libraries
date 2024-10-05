package com.rm.soa.clients.criterias.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.contract.orders.constants.OrderStateType;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.dao.orders.impl.OrderQueryBuilder;
import com.rm.dao.orders.impl.OrderStateQueryBuilder;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.model.clients.criterias.CriteriaRule;
import com.rm.model.clients.criterias.CriteriaRuleDuration;
import com.rm.model.clients.criterias.CriteriaRuleRange;
import com.rm.model.clients.criterias.CriteriaRules;
import com.rm.soa.clients.criterias.ClientFinder;
import com.rm.soa.clients.criterias.ClientFinderContext;
import com.rm.utils.dates.PeriodUtils;

/***
 * 
 * @author Nabil
 * 
 */
@Component(ClientFinder.ClientFinderOrderCount)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderCountOrder implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.OrderCount)) {
			CriteriaRules rules = criterias.getRules().get(ClientCriteriaType.OrderCount);
			OrderQueryBuilder q2 = OrderQueryBuilder.get();
			for (CriteriaRule rule : rules.getRules()) {
				if (rule instanceof CriteriaRuleRange) {
					CriteriaRuleRange d = (CriteriaRuleRange) rule;
					q2.withCountGroupByClient(d.getFrom(), d.getTo());
				} else if (rule instanceof CriteriaRuleDuration) {
					CriteriaRuleDuration d = (CriteriaRuleDuration) rule;
					Date fromDate = PeriodUtils.toDate(d.getOriginal(), d.getTo());
					Date toDate = PeriodUtils.toDate(d.getOriginal(), d.getFrom());
					OrderStateQueryBuilder q1 = OrderStateQueryBuilder.get().withType(OrderStateType.Paid);
					q1.withDisjunction();
					q1.withDisjunctionDateBetween(fromDate, toDate);
					q1.withDisjunctionDateGE(fromDate);
					q1.withDisjunctionDateLE(toDate);
					q2.withState(q1);
				}
			}
			//
			ClientQueryBuilder q4 = ClientQueryBuilder.get();
			//
			q4.withDisjunction();
			q4.withDisjunctionIdIn(q2);
			q4.withIdProjection();
			context.put(ClientCriteriaType.OrderCount, q4);
		}

	}
}
