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
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rm.buiseness.commons.TestScenarios;
import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.clients.beans.ClientCriteriaRuleBean;
import com.rm.contract.clients.beans.ClientCriteriaRulesBean;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.builders.AddressComponentFormBuilder;
import com.rm.contract.clients.builders.AddressFormBuilder;
import com.rm.contract.clients.builders.ClientFormBuilder;
import com.rm.contract.clients.constants.ClientCriteriaType;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.dao.clients.DaoClientCriterias;
import com.rm.dao.orders.DaoOrderState;
import com.rm.model.clients.criterias.ClientCriterias;
import com.rm.model.orders.Order;
import com.rm.soa.clients.SoaClient;
import com.rm.soa.clients.criterias.ClientFinderProcessor;
import com.rm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestClientFinderAgeOrder {

	@Autowired
	private SoaClient soaClient;
	@Autowired
	private ClientFinderProcessor clientFinderProcessor;
	@Autowired
	private DaoClientCriterias daoClientCriterias;
	private TestScenarios test;
	private TestOrderHelper helper;
	@Autowired
	private DaoOrderState daoOrderState;
	@Autowired
	private ApplicationContext applicationContext;
	//
	protected Log log = LogFactory.getLog(getClass());
	private ClientForm client;
	private ProductViewDto menu;
	protected ProductViewDto menuEt;

	@Before
	public void globalSetUp() throws Exception {
		//
		MutableDateTime birth = new MutableDateTime();
		birth.addWeeks(1);
		client = ClientFormBuilder.get().withBirthDate(new Date()).withEmail("n@a.com").withFirstname("f")
				.withIgnore(false).withName("n").withPhone("p")
				.withAddress(
						AddressFormBuilder.get().withGeocode("GEOCODE")
								.withComponents(AddressComponentFormBuilder.get().withCountry("FR").withLatitude(2d)
										.withLocality("LOC").withLongitude(2d).withPostal("POSTAL").withStreet("STREET")
										.build()))
				.build();
		client = soaClient.save(client);
		//
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext)
				.getBeanFactory();
		test = beanFactory.createBean(TestScenarios.class);
		helper = beanFactory.createBean(TestOrderHelper.class);
		//
		helper.deleteAllOrders();
		test.testCreate();
		menu = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
		// for (ProductPartViewDto part : menu.parts()) {
		// part.setSelected(part.getProducts().get(0));
		// }
		// menuEt = test.getAllProducts().get(TestScenarios.MENUS_ETUDIANTS);
		// for (ProductPartViewDto part : menuEt.parts()) {
		// part.setSelected(part.getProducts().get(0));
		// }
	}

	@Test
	@Transactional
	public void testNotOrderedSinceEmptyCriteria() throws Exception {
		ClientCriterias crit = new ClientCriterias();
		daoClientCriterias.saveOrUpdate(crit);
		{
			// empty criteria so found all
			Collection<Long> clientIds = clientFinderProcessor.find(crit);
			Assert.assertEquals(1, clientIds.size());
			//
			clientIds = clientFinderProcessor.find(crit);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menu, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 1);
			Collection<Long> clientIds = clientFinderProcessor.find(crit);
			Assert.assertEquals(1, clientIds.size());
		}
	}

	@Test
	@Transactional
	public void testNotOrderedSince() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.AgeOfOrder,
				new ClientCriteriaRuleBean().setPeriodDuration(PeriodType.Week).setDurationCountFrom(2d).setEnable(true)
						.setHasDurationCountFrom(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never order so => nok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		// Prepare ORDERS for other client
		Collection<Order> orders = helper.addOrder(client, menu, test.getAllRestaurants().get(TestScenarios.RESTO1), 1,
				1);
		{
			// SET order 2 weeks agos
			MutableDateTime age = new MutableDateTime();
			age.addWeeks(-3);
			age.addDays(-1);
			daoOrderState.updateDate(orders.iterator().next().getLastState().getId(), age.toDate());
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// SET LOGGED 4 days agos
			MutableDateTime age = new MutableDateTime();
			age.addDays(-4);
			daoOrderState.updateDate(orders.iterator().next().getLastState().getId(), age.toDate());
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertTrue(clientIds.isEmpty());
		}

	}

}
