package com.rm.soa.clients.criterias.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.dao.commons.impl.AddressQueryBuilder;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.model.clients.criterias.CriteriaRule;
import com.rm.model.clients.criterias.CriteriaRulePosition;
import com.rm.model.clients.criterias.CriteriaRules;
import com.rm.soa.clients.criterias.ClientFinder;
import com.rm.soa.clients.criterias.ClientFinderContext;
import com.rm.utils.geo.GeoLocation;

/***
 * 
 * @author Nabil
 * 
 */
@Component(ClientFinder.ClientFinderClientPosition)
@Qualifier(ClientFinder.ClientFinder)
public class ClientFinderPosition implements ClientFinder {

	public void find(ClientCriterias criterias, ClientFinderContext context) {
		if (criterias.getRules().containsKey(ClientCriteriaType.ClientPosition)) {
			CriteriaRules rules = criterias.getRules().get(ClientCriteriaType.ClientPosition);
			ClientQueryBuilder q2 = ClientQueryBuilder.get();
			//
			for (CriteriaRule rule : rules.getRules()) {
				if (rule instanceof CriteriaRulePosition) {
					AddressQueryBuilder q3 = AddressQueryBuilder.get();
					CriteriaRulePosition d = (CriteriaRulePosition) rule;
					GeoLocation location = GeoLocation.fromDegrees(d.getLatitude(), d.getLongitude());
					q3.withClosestInDegree(location, d.getRadius());
					q2.withAddress(q3);
				}
			}
			q2.withIdProjection();
			context.put(ClientCriteriaType.ClientPosition, q2);
		}
	}
}
