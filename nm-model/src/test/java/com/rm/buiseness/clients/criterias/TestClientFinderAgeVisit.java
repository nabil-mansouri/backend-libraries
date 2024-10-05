package com.rm.buiseness.clients.criterias;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.MutableDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.clients.beans.ClientCriteriaRuleBean;
import com.rm.contract.clients.beans.ClientCriteriaRulesBean;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.builders.AddressComponentFormBuilder;
import com.rm.contract.clients.builders.AddressFormBuilder;
import com.rm.contract.clients.builders.ClientFormBuilder;
import com.rm.contract.clients.constants.ClientActionType;
import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.DaoClient;
import com.rm.dao.clients.DaoClientCriterias;
import com.rm.model.clients.Client;
import com.rm.model.clients.ClientActions;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.soa.clients.SoaClient;
import com.rm.soa.clients.criterias.ClientFinderProcessor;
import com.rm.utils.dao.NoDataFoundException;
import com.rm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestClientFinderAgeVisit {

	@Autowired
	private SoaClient soaClient;
	@Autowired
	private ClientFinderProcessor clientFinderProcessor;
	@Autowired
	private DaoClientCriterias daoClientCriterias;
	@Autowired
	private DaoClient daoClient;

	//
	protected Log log = LogFactory.getLog(getClass());
	private ClientForm client;
	private Client clientModel;

	@Before
	public void globalSetUp() throws NoDataFoundException {
		//
		MutableDateTime birth = new MutableDateTime();
		birth.addWeeks(1);
		client = ClientFormBuilder
				.get()
				.withBirthDate(new Date())
				.withEmail("n@a.com")
				.withFirstname("f")
				.withIgnore(false)
				.withName("n")
				.withPhone("p")
				.withAddress(
						AddressFormBuilder
								.get()
								.withGeocode("GEOCODE")
								.withComponents(
										AddressComponentFormBuilder.get().withCountry("FR").withLatitude(2d).withLocality("LOC").withLongitude(2d)
												.withPostal("POSTAL").withStreet("STREET").build())).build();
		client = soaClient.save(client);
		clientModel = daoClient.load(client.getId());
	}

	@Test
	@Transactional
	public void testNotVisitedSinceEmptyCriteria() throws NoDataFoundException {
		ClientCriterias crit = new ClientCriterias();
		daoClientCriterias.saveOrUpdate(crit);
		// empty criteria so found all
		Collection<Long> clientIds = clientFinderProcessor.find(crit);
		Assert.assertEquals(1, clientIds.size());
		//
		clientIds = clientFinderProcessor.find(crit);
		Assert.assertEquals(1, clientIds.size());
		{
			// SET LOGGED 4 days agos
			MutableDateTime age = new MutableDateTime();
			age.addDays(-4);
			clientModel.add(new ClientActions(age.toDate(), ClientActionType.Logged));
			daoClient.saveOrUpdate(clientModel);
			daoClient.flush();
		}
		clientIds = clientFinderProcessor.find(crit);
		Assert.assertEquals(1, clientIds.size());
	}

	@Test
	@Transactional
	public void testNotVisitedSince() throws NoDataFoundException {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.AgeOfVisit, new ClientCriteriaRuleBean()
				.setPeriodDuration(PeriodType.Week).setEnable(true).setDurationCountFrom(2d).setHasDurationCountFrom(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never connect so => ok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// SET LOGGED 2 weeks agos
			MutableDateTime age = new MutableDateTime();
			age.addWeeks(-3);
			age.addDays(-1);
			clientModel.add(new ClientActions(age.toDate(), ClientActionType.Logged));
			daoClient.saveOrUpdate(clientModel);
			daoClient.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			 Assert.assertEquals(1, clientIds.size());
		}
		{
			// SET LOGGED 4 days agos
			MutableDateTime age = new MutableDateTime();
			age.addDays(-4);
			clientModel.add(new ClientActions(age.toDate(), ClientActionType.Logged));
			daoClient.saveOrUpdate(clientModel);
			daoClient.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertTrue(clientIds.isEmpty());
		}

	}

}
