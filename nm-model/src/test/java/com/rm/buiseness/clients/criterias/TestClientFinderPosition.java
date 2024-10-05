package com.rm.buiseness.clients.criterias;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.rm.utils.geo.GeoLocation;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestClientFinderPosition {

	@Autowired
	private SoaClient soaClient;
	@Autowired
	private ClientFinderProcessor clientFinderProcessor;
	@Autowired
	private DaoClientCriterias daoClientCriterias;
	//
	protected Log log = LogFactory.getLog(getClass());
	private ClientForm client, client2, client3, client4;
	private Double centerLat = 46.80535099999999, centerLong = 4.427799999999934;

	@Before
	public void globalSetUp() throws NoDataFoundException {
		// <5km (3.857km)
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
										AddressComponentFormBuilder.get().withCountry("FR").withLocality("LOC").withLatitude(46.839803588473146)
												.withLongitude(4.433128237724304).withPostal("POSTAL").withStreet("STREET").build())).build();
		client = soaClient.save(client);
		// (6.887km)
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
										AddressComponentFormBuilder.get().withCountry("FR").withLocality("LOC").withLatitude(46.8440306623559)
												.withLongitude(4.498359560966492).withPostal("POSTAL").withStreet("STREET").build())).build();
		client2 = soaClient.save(client2);
		// (4.190 km)
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
										AddressComponentFormBuilder.get().withCountry("FR").withLocality("LOC").withLatitude(46.79496954686281)
												.withLongitude(4.480651617050171).withPostal("POSTAL").withStreet("STREET").build())).build();
		client3 = soaClient.save(client3);
		// (12.693km)
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
										AddressComponentFormBuilder.get().withCountry("FR").withLocality("LOC").withLatitude(46.69482920023661)
												.withLongitude(4.386870861053467).withPostal("POSTAL").withStreet("STREET").build())).build();
		client4 = soaClient.save(client4);
	}

	@Test
	@Transactional
	public void testPositionFilter() throws Exception {
		// radius 5km
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.ClientPosition,
				new ClientCriteriaRuleBean().setEnable(true).setHasPosition(true).setLatitude(centerLat).setLongitude(centerLong).setRadius(5d));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(2, clientIds.size());
			Assert.assertTrue(clientIds.contains(client.getId()));
			Assert.assertTrue(clientIds.contains(client3.getId()));
		}

	}

	@Test
	@Transactional
	public void testPosition10Filter() throws Exception {
		GeoLocation center = GeoLocation.fromDegrees(46.80535099999999, 4.427799999999934);
		GeoLocation g1 = GeoLocation.fromDegrees(46.839803588473146, 4.433128237724304);
		System.out.println("**************************************");
		System.out.println(center.distanceTo(g1));
		// radius 10km
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.ClientPosition,
				new ClientCriteriaRuleBean().setEnable(true).setHasPosition(true).setLatitude(centerLat).setLongitude(centerLong).setRadius(10d));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(3, clientIds.size());
			Assert.assertTrue(clientIds.contains(client.getId()));
			Assert.assertTrue(clientIds.contains(client2.getId()));
			Assert.assertTrue(clientIds.contains(client3.getId()));
		}

	}

}
