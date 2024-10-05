package com.rm.soa.clients.criterias.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientActionType;
import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.dao.clients.views.ClientAgeActionQueryBuilder;
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
@Component(ClientFinder.ClientFinderAgeOfVisit)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderAgeOfVisit implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.AgeOfVisit)) {
			CriteriaRules rules = criterias.getRules().get(ClientCriteriaType.AgeOfVisit);
			ClientAgeActionQueryBuilder q2 = ClientAgeActionQueryBuilder.get().withType(ClientActionType.Logged);
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
			//
			q2.withClientIdProjection();
			//
			ClientQueryBuilder q4 = ClientQueryBuilder.get();
			//
			ClientAgeActionQueryBuilder q3 = ClientAgeActionQueryBuilder.get().withType(ClientActionType.Logged);
			q3.withDisjunction();
			q3.withJoinClient(q4);
			//
			q4.withDisjunction();
			q4.withDisjunctionIdIn(q2);
			q4.withDisjunctionNotExists(q3);
			q4.withIdProjection();
			context.put(ClientCriteriaType.AgeOfVisit, q4);
		}

	}
}
