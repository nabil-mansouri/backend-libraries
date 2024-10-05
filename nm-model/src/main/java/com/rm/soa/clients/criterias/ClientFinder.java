package com.rm.soa.clients.criterias;

import com.rm.model.clients.criterias.ClientCriterias;

/**
 * 
 * @author Nabil
 * 
 */
public interface ClientFinder {
	public static final String ClientFinder = "ClientFinder";
	public static final String ClientFinderBirthday = "ClientFinder.ClientFinderBirthday";
	public static final String ClientFinderAgeOfVisit = "ClientFinder.ClientFinderAgeOfVisit";
	public static final String ClientFinderAmountOrder = "ClientFinder.ClientFinderAmountOrder";
	public static final String ClientFinderAgeOfOrder = "ClientFinder.ClientFinderAgeOfOrder";
	public static final String ClientFinderOrderCount = "ClientFinder.ClientFinderOrderCount";
	public static final String ClientFinderFilleulCount = "ClientFinder.ClientFinderFilleulCount";
	public static final String ClientFinderClientPosition = "ClientFinder.ClientFinderClientPosition";
	public static final String ClientFinderClientId = "ClientFinder.ClientFinderClientId";
	public static final String ClientFinderEvent = "ClientFinder.ClientFinderEvent";

	public void find(ClientCriterias criterias, ClientFinderContext context);

}
