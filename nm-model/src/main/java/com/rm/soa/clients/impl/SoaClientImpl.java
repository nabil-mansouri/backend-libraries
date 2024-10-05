package com.rm.soa.clients.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.contract.clients.beans.ClientCriteriaRulesBean;
import com.rm.contract.clients.beans.ClientFilterBean;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.constants.ClientOptions;
import com.rm.contract.commons.constants.ModelOptions;
import com.rm.dao.clients.DaoClient;
import com.rm.dao.clients.DaoClientCriterias;
import com.rm.dao.clients.impl.ClientQueryBuilder;
import com.rm.model.clients.Client;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.soa.clients.SoaClient;
import com.rm.soa.clients.converters.ClientConverter;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Service
public class SoaClientImpl implements SoaClient {

	@Autowired
	private DaoClient daoClient;
	@Autowired
	private DaoClientCriterias daoClientCriterias;
	@Autowired
	private ClientConverter clientConverter;

	@Transactional()
	public ClientCriteriaRulesBean save(ClientCriteriaRulesBean bean) throws NoDataFoundException {
		ClientCriterias client = new ClientCriterias();
		if (bean.getId() != null) {
			client = daoClientCriterias.loadById(bean.getId());
		}
		client = clientConverter.convert(client, bean);
		daoClientCriterias.saveOrUpdate(client);
		bean.setId(client.getId());
		return bean;
	}

	@Transactional()
	public ClientForm save(ClientForm bean) throws NoDataFoundException {
		Client client = new Client();
		if (bean.getId() != null) {
			client = daoClient.loadById(bean.getId());
		} else {
			client.setReference(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		}
		client.resetSearch();
		//
		client = clientConverter.convert(client, bean);
		daoClient.saveOrUpdate(client);
		//
		bean.setId(client.getId());
		return bean;
	}

	@Transactional(readOnly = true)
	public ClientForm edit(Long id) throws NoDataFoundException {
		Client client = daoClient.loadById(id);
		Collection<ModelOptions> options = new ArrayList<ModelOptions>();
		options.addAll(Arrays.asList(ClientOptions.values()));
		return clientConverter.convert(client, options);
	}

	@Transactional()
	public Client delete(Long id) throws NoDataFoundException {
		Client discount = daoClient.loadById(id);
		// Delete
		daoClient.delete(discount);
		return discount;
	}

	@Transactional(readOnly = true)
	public Collection<ClientForm> fetch(ClientFilterBean request) {
		ClientQueryBuilder query = ClientQueryBuilder.get().withFilter(request);
		Collection<Client> discounts = daoClient.find(query);
		Collection<ClientForm> beans = new ArrayList<ClientForm>();
		for (Client dis : discounts) {
			beans.add(clientConverter.convert(dis, request.getOptions()));
		}
		return beans;
	}

}
