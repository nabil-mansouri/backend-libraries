package com.rm.soa.clients.converters;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.rm.app.geo.AddressFormDto;
import com.rm.contract.clients.beans.ClientCriteriaRuleBean;
import com.rm.contract.clients.beans.ClientCriteriaRulesBean;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.contract.clients.constants.ClientEventType;
import com.rm.contract.clients.constants.ClientOptions;
import com.rm.contract.commons.beans.OptionsList;
import com.rm.contract.commons.constants.ModelOptions;
import com.rm.model.clients.Client;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.model.clients.criterias.CriteriaRuleDuration;
import com.rm.model.clients.criterias.CriteriaRuleEvent;
import com.rm.model.clients.criterias.CriteriaRuleId;
import com.rm.model.clients.criterias.CriteriaRulePosition;
import com.rm.model.clients.criterias.CriteriaRuleRange;
import com.rm.model.clients.criterias.CriteriaRuleVoid;
import com.rm.model.clients.criterias.CriteriaRules;
import com.rm.model.commons.Address;
import com.rm.soa.commons.converters.AddressConverter;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class ClientConverterImpl implements ClientConverter {

	@Autowired
	private AddressConverter addressConverter;

	protected CriteriaRuleDuration buildDuration(ClientCriteriaRuleBean ruleBean) {
		CriteriaRuleDuration rule = new CriteriaRuleDuration();
		rule.setOriginal(ruleBean.getPeriodDuration());
		if (ruleBean.isHasDurationCountFrom()) {
			rule.setFrom(ruleBean.getDurationCountFrom());
		}
		if (ruleBean.isHasDurationCountTo()) {
			rule.setTo(ruleBean.getDurationCountTo());
		}
		return rule;
	}

	protected CriteriaRuleRange buildRange(ClientCriteriaRuleBean ruleBean) {
		CriteriaRuleRange rule = new CriteriaRuleRange();
		if (ruleBean.isHasRangeFrom()) {
			rule.setFrom(ruleBean.getRangeFrom());
		}
		if (ruleBean.isHasRangeTo()) {
			rule.setTo(ruleBean.getRangeTo());
		}
		return rule;
	}

	protected CriteriaRuleId buildIds(ClientCriteriaRuleBean ruleBean) {
		CriteriaRuleId rule = new CriteriaRuleId();
		rule.setIds(ruleBean.getClientIds());
		return rule;
	}

	@Transactional(readOnly = true)
	public ClientCriterias convert(ClientCriterias client, ClientCriteriaRulesBean bean) {
		client.getRules().clear();
		for (ClientCriteriaType t : bean.getRules().keySet()) {
			ClientCriteriaRuleBean ruleBean = bean.getRules().get(t);
			if (ruleBean.isEnable()) {
				CriteriaRules rules = new CriteriaRules();
				client.put(t, rules);
				switch (t) {
				case Events: {
					if (ruleBean.isOnUserEvent()) {
						for (ClientEventType tt : ruleBean.getEvents()) {
							CriteriaRuleEvent ev = new CriteriaRuleEvent();
							ev.setType(tt);
							rules.add(ev);
						}
					}
					break;
				}
				case BirthDay: {
					if (ruleBean.isOnBirthday()) {
						rules.add(new CriteriaRuleVoid());
					}
					break;
				}
				case AgeOfVisit:
				case AgeOfOrder: {
					if (ruleBean.isHasDurationCountFrom() || ruleBean.isHasDurationCountTo()) {
						rules.add(buildDuration(ruleBean));
					}
					break;
				}
				case FilleulCount:
				case OrderCount:
				case AmountOrder: {
					if (ruleBean.isHasRangeFrom() || ruleBean.isHasRangeTo()) {
						{

							rules.add(buildRange(ruleBean));
						}
						{
							if (ruleBean.isHasDurationCountFrom() || ruleBean.isHasDurationCountTo()) {
								rules.add(buildDuration(ruleBean));
							}
						}
					}
					break;
				}
				case ClientId: {
					if (ruleBean.isHasClientIds()) {
						rules.add(buildIds(ruleBean));
					}
					break;
				}
				case ClientPosition: {
					if (ruleBean.isHasPosition()) {
						CriteriaRulePosition rule = new CriteriaRulePosition();
						rule.setLatitude(ruleBean.getLatitude());
						rule.setLongitude(ruleBean.getLongitude());
						rule.setRadius(ruleBean.getRadius());
						rules.add(rule);
					}
					break;
				}
				}
			}
		}
		return client;
	}

	@Transactional(readOnly = true)
	public Client convert(Client client, ClientForm bean) {
		OptionsList options = new OptionsList();
		client.setEmail(bean.getEmail());
		client.setFirstname(bean.getFirstname());
		client.setId(bean.getId());
		client.setName(bean.getName());
		client.setPhone(bean.getPhone());
		client.setBirthDate(bean.getBirthDate());
		if (bean.getAddress() != null) {
			Address address = addressConverter.toEntitySafe(bean.getAddress(), options);
			client.add(address);
		}
		return client;
	}

	@Transactional(readOnly = true)
	public ClientForm convert(Client client, Collection<ModelOptions> opts) {
		OptionsList options = new OptionsList().withAddAll(opts);
		ClientForm bea = new ClientForm();
		bea.setEmail(client.getEmail());
		bea.setFirstname(client.getFirstname());
		bea.setId(client.getId());
		bea.setName(client.getName());
		bea.setBirthDate(client.getBirthDate());
		bea.setPhone(client.getPhone());
		bea.setCreated(client.getCreated());
		bea.setReference(client.getReference());
		//
		if (options.contains(ClientOptions.Addresses)) {
			for (Address add : client.getAdresses()) {
				AddressFormDto form = addressConverter.toFormDto(add, options);
				if (Strings.isNullOrEmpty(form.getName())) {
					form.setName(client.getName() + " " + client.getFirstname());
				}
				bea.getAddresses().add(form);
			}
		}
		if (options.contains(ClientOptions.LastAddress)) {
			bea.setAddress(addressConverter.toFormDto(client.getLast(), options));
		}
		return bea;
	}

}
