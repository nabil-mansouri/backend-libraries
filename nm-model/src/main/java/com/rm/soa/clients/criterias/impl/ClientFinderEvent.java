package com.rm.soa.clients.criterias.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.model.clients.criterias.CriteriaRule;
import com.rm.model.clients.criterias.CriteriaRuleEvent;
import com.rm.model.clients.criterias.CriteriaRules;
import com.rm.soa.clients.criterias.ClientFinder;
import com.rm.soa.clients.criterias.ClientFinderContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(ClientFinder.ClientFinderEvent)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderEvent implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.Events)) {
			CriteriaRules rule = criterias.getRules().get(ClientCriteriaType.Events);
			boolean isOk = false;
			for (CriteriaRule r : rule.getRules()) {
				if (r instanceof CriteriaRuleEvent && context.getCurrentEvents().contains(((CriteriaRuleEvent) r).getType())) {
					isOk = true;
				}
			}
			context.setIsOk(isOk);
		}

	}
}
