package com.rm.soa.clients.criterias.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.model.clients.criterias.CriteriaRule;
import com.rm.model.clients.criterias.CriteriaRuleId;
import com.rm.model.clients.criterias.CriteriaRules;
import com.rm.soa.clients.criterias.ClientFinder;
import com.rm.soa.clients.criterias.ClientFinderContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(ClientFinder.ClientFinderClientId)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderIds implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.ClientId)) {
			CriteriaRules rules = criterias.getRules().get(ClientCriteriaType.ClientId);
			ClientQueryBuilder q2 = ClientQueryBuilder.get();
			//
			for (CriteriaRule rule : rules.getRules()) {
				if (rule instanceof CriteriaRuleId) {
					CriteriaRuleId d = (CriteriaRuleId) rule;
					q2.withIds(d.getIds());
				}
			}
			q2.withIdProjection();
			context.put(ClientCriteriaType.ClientId, q2);
		}

	}
}
