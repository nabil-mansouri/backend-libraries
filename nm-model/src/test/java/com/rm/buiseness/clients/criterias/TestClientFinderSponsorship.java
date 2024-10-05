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
import com.rm.dao.clients.DaoSponsorship;
import com.rm.model.clients.Sponsorship;
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
public class TestClientFinderSponsorship {

	@Autowired
	private SoaClient soaClient;
	@Autowired
	private ClientFinderProcessor clientFinderProcessor;
	@Autowired
	private DaoClientCriterias daoClientCriterias;
	@Autowired
	private DaoClient daoClient;
	@Autowired
	private DaoSponsorship daoSponsorship;
	//
	protected Log log = LogFactory.getLog(getClass());
	private ClientForm client, client2, client3,client4;

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

	@Test
	@Transactional
	public void testSponsorshipFrom() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.FilleulCount,
				new ClientCriteriaRuleBean().setRangeFrom(2d).setEnable(true).setHasRangeFrom(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never order so => nok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			Sponsorship sponsorship = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client2.getId()));
			daoSponsorship.saveOrUpdate(sponsorship);
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			Sponsorship sponsorship2 = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client3.getId()));
			daoSponsorship.saveOrUpdate(sponsorship2);
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}

	}

	@Test
	@Transactional
	public void testOrderedAmountTo() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.FilleulCount, new ClientCriteriaRuleBean()
				.setRangeTo(1d).setEnable(true).setHasRangeTo(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never order so => nok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			Sponsorship sponsorship = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client2.getId()));
			daoSponsorship.saveOrUpdate(sponsorship);
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			Sponsorship sponsorship = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client3.getId()));
			daoSponsorship.saveOrUpdate(sponsorship);
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}

	}

	@Test
	@Transactional
	public void testOrderedAmountBetween() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.FilleulCount, new ClientCriteriaRuleBean()
				.setEnable(true).setRangeFrom(2d).setHasRangeFrom(true).setRangeTo(2d).setHasRangeTo(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never order so => nok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			Sponsorship sponsorship = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client2.getId()));
			daoSponsorship.saveOrUpdate(sponsorship);
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			Sponsorship sponsorship = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client3.getId()));
			daoSponsorship.saveOrUpdate(sponsorship);
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			Sponsorship sponsorship = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client4.getId()));
			daoSponsorship.saveOrUpdate(sponsorship);
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
	}

	@Test
	@Transactional
	public void testOrderedAmountBetweenDates() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(
				ClientCriteriaType.FilleulCount,
				new ClientCriteriaRuleBean().setEnable(true).setRangeFrom(2d).setHasRangeFrom(true).setHasDurationCountTo(true)
						.setDurationCountTo(2d).setPeriodDuration(PeriodType.Week));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		// Prepare ORDERS for other client
		Sponsorship sponsorship = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client2.getId()));
		daoSponsorship.saveOrUpdate(sponsorship);
		Sponsorship sponsorship2 = new Sponsorship(daoClient.load(client.getId()), daoClient.load(client3.getId()));
		daoSponsorship.saveOrUpdate(sponsorship2);
		daoSponsorship.flush();
		{
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			MutableDateTime now = new MutableDateTime();
			now.addWeeks(-2);
			now.addDays(-1);
			daoSponsorship.updateDate(sponsorship2.getId(), now.toDate());
			daoSponsorship.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
	}
}
