package com.rm.soa.clients;

import java.util.Collection;

import com.rm.contract.clients.beans.ClientCriteriaRulesBean;
import com.rm.contract.clients.beans.ClientFilterBean;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.model.clients.Client;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaClient {

	public ClientForm save(ClientForm client) throws NoDataFoundException;

	public ClientCriteriaRulesBean save(ClientCriteriaRulesBean rule) throws NoDataFoundException;

	public ClientForm edit(Long id) throws NoDataFoundException;

	public Client delete(Long id) throws NoDataFoundException;

	public Collection<ClientForm> fetch(ClientFilterBean request);

}
