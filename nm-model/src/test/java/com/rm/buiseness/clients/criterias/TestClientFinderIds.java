package com.rm.buiseness.clients.criterias;

import java.util.Arrays;
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
import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.DaoClientCriterias;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.soa.clients.SoaClient;
import com.rm.soa.clients.criterias.ClientFinderProcessor;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestClientFinderIds {

	@Autowired
	private SoaClient soaClient;
	@Autowired
	private ClientFinderProcessor clientFinderProcessor;
	@Autowired
	private DaoClientCriterias daoClientCriterias;
	//
	protected Log log = LogFactory.getLog(getClass());
	private ClientForm client, client2, client3, client4;

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
		//
		client2 = ClientFormBuilder
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
		client2 = soaClient.save(client2);
		//
		client3 = ClientFormBuilder
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
		client3 = soaClient.save(client3);
		client4 = ClientFormBuilder
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
		client4 = soaClient.save(client4);
	}

	// TODO contrainte sur order
	@Test
	@Transactional
	public void testIdsFilter() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.ClientId, new ClientCriteriaRuleBean().setEnable(true)
				.setHasClientIds(true).setClientIds(Arrays.asList(client2.getId(), client4.getId())));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(2, clientIds.size());
			Assert.assertTrue(clientIds.contains(client2.getId()));
			Assert.assertTrue(clientIds.contains(client4.getId()));
		}

	}

}
