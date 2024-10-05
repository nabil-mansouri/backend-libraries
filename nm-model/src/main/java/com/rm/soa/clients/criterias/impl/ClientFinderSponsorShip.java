package com.rm.soa.clients.criterias.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.impl.SponsorshipQueryBuilder;
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
@Component(ClientFinder.ClientFinderFilleulCount)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderSponsorShip implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.FilleulCount)) {
			CriteriaRules rules = criterias.getRules().get(ClientCriteriaType.FilleulCount);
			SponsorshipQueryBuilder q2 = SponsorshipQueryBuilder.get();
			for (CriteriaRule rule : rules.getRules()) {
				if (rule instanceof CriteriaRuleRange) {
					CriteriaRuleRange d = (CriteriaRuleRange) rule;
					q2.withCountGroupBySponsor(d.getFrom(), d.getTo());
				} else if (rule instanceof CriteriaRuleDuration) {
					CriteriaRuleDuration d = (CriteriaRuleDuration) rule;
					Date fromDate = PeriodUtils.toDate(d.getOriginal(), d.getTo());
					Date toDate = PeriodUtils.toDate(d.getOriginal(), d.getFrom());
					q2.withDisjunction();
					q2.withDisjunctionDateBetween(fromDate, toDate);
					q2.withDisjunctionDateGE(fromDate);
					q2.withDisjunctionDateLE(toDate);
				}
			}
			//
			context.put(ClientCriteriaType.FilleulCount, q2);
		}

	}
}
