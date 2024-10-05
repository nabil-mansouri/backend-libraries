package com.rm.soa.clients.converters;

import java.util.Collection;

import com.rm.contract.clients.beans.ClientCriteriaRulesBean;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.commons.constants.ModelOptions;
import com.rm.model.clients.Client;
import com.rm.model.clients.criterias.ClientCriterias;

/**
 * 
 * @author Nabil
 * 
 */
public interface ClientConverter {
	public ClientCriterias convert(ClientCriterias client, ClientCriteriaRulesBean bean);

	public Client convert(Client client, ClientForm bean);

	public ClientForm convert(Client client, Collection<ModelOptions> options);

}