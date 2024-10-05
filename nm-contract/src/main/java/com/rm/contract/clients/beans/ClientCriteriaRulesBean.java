package com.rm.contract.clients.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rm.contract.clients.constants.ClientCriteriaType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCriteriaRulesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
	private Map<ClientCriteriaType, ClientCriteriaRuleBean> rules = new HashMap<ClientCriteriaType, ClientCriteriaRuleBean>();

	public Map<ClientCriteriaType, ClientCriteriaRuleBean> getRules() {
		return rules;
	}

	public ClientCriteriaRulesBean put(ClientCriteriaType key, ClientCriteriaRuleBean value) {
		rules.put(key, value);
		return this;
	}

	public void setRules(Map<ClientCriteriaType, ClientCriteriaRuleBean> rules) {
		this.rules = rules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
