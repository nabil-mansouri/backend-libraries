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
import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.dao.clients.DaoClient;
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
public class TestClientFinderBirthday {

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

	@Before
	public void globalSetUp() throws NoDataFoundException {
		//
		MutableDateTime birth = new MutableDateTime();
		birth.addWeeks(1);
		client = ClientFormBuilder
				.get()
				.withBirthDate(birth.toDate())
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
	}

	@Test
	@Transactional
	public void testBirthday() throws NoDataFoundException {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.BirthDay, new ClientCriteriaRuleBean().setEnable(true)
				.setOnBirthday(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Not birthday => next week
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// NEXT WEEK => borthday
			MutableDateTime age = new MutableDateTime();
			age.addWeeks(1);
			daoClient.updateSubscribedDate(client.getId(), new Date());
			daoClient.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
	}
}
