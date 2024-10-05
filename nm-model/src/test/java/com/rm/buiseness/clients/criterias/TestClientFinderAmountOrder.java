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
import com.rm.model.orders.OrderState;
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
public class TestClientFinderAmountOrder {

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
	private ProductViewDto menuEt;

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
		// menu = test.getAllProducts().get(TestScenarios.MENU_MOYEN);
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
	public void testOrderedAmountFromEmpty() throws Exception {
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
	public void testOrderedAmountFrom() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.AmountOrder,
				new ClientCriteriaRuleBean().setRangeFrom(25d).setEnable(true).setHasRangeFrom(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never order so => nok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 2);
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 1);
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}

	}

	@Test
	@Transactional
	public void testOrderedAmountTo() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.AmountOrder,
				new ClientCriteriaRuleBean().setRangeTo(25d).setEnable(true).setHasRangeTo(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never order so => nok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 2);
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 1);
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}

	}

	@Test
	@Transactional
	public void testOrderedAmountBetween() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.AmountOrder,
				new ClientCriteriaRuleBean().setEnable(true).setRangeFrom(25d).setHasRangeFrom(true).setRangeTo(50d)
						.setHasRangeTo(true));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		{
			// Never order so => nok
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 2);
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 1);
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			// Prepare ORDERS for other client
			helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1), 1, 3);
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
	}

	@Test
	@Transactional
	public void testOrderedAmountBetweenDates() throws Exception {
		// Not visited since 2 weeks
		ClientCriteriaRulesBean rules = new ClientCriteriaRulesBean().put(ClientCriteriaType.AmountOrder,
				new ClientCriteriaRuleBean().setEnable(true).setRangeFrom(25d).setHasRangeFrom(true)
						.setHasDurationCountTo(true).setDurationCountTo(2d).setPeriodDuration(PeriodType.Week));
		rules = soaClient.save(rules);
		ClientCriterias c = daoClientCriterias.loadById(rules.getId());
		// Prepare ORDERS for other client
		Collection<Order> orders = helper.addOrder(client, menuEt, test.getAllRestaurants().get(TestScenarios.RESTO1),
				3, 1);
		{
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(1, clientIds.size());
		}
		{
			for (OrderState s : orders.iterator().next().getStates()) {
				MutableDateTime now = new MutableDateTime();
				now.addWeeks(-2);
				now.addDays(-1);
				daoOrderState.updateDate(s.getId(), now.toDate());
			}
			daoOrderState.flush();
			Collection<Long> clientIds = clientFinderProcessor.find(c);
			Assert.assertEquals(0, clientIds.size());
		}
	}
}
