package com.rm.buiseness.discounts.lifecycle;

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

import com.rm.app.geo.AddressFormComponentsBean;
import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.builders.AddressComponentFormBuilder;
import com.rm.contract.clients.builders.AddressFormBuilder;
import com.rm.contract.clients.builders.ClientFormBuilder;
import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.contract.discounts.beans.DiscountLifeCycleRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleSubjectBean;
import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.contract.discounts.constants.DiscountOperationType;
import com.rm.dao.discounts.DaoDiscount;
import com.rm.dao.discounts.DaoDiscountLifeCycleState;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleState;
import com.rm.soa.clients.SoaClient;
import com.rm.soa.discounts.SoaDiscount;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleContext;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleProcessor;
import com.rm.utils.dao.NoDataFoundException;
import com.rm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestDiscountLifeCycleDateTime {

	@Autowired
	private SoaDiscount soaDiscount;
	@Autowired
	private SoaClient soaClient;
	@Autowired
	private DiscountLifeCycleProcessor lifeCycleProcessor;
	@Autowired
	private DaoDiscount doDaoDiscount;
	@Autowired
	private DaoDiscountLifeCycleState daoLifeState;

	//
	private DiscountFormBean discountBuilder;
	private ClientForm client;
	//
	protected Log log = LogFactory.getLog(getClass());

	@Before
	public void globalSetUp() throws NoDataFoundException {
		discountBuilder = new DiscountFormBean().setName("PROMO1").addRule(
				new DiscountRuleBean().setOperation(true).setOperationType(DiscountOperationType.Fixe).setOperationValue(10d)
						.setSubject(new DiscountRuleSubjectBean().setAllProducts(true)));
		AddressFormComponentsBean addrComp = AddressComponentFormBuilder.get().withCountry("FR").withLatitude(2d).withLocality("LOC").withLongitude(2d)
				.withPostal("POSTAL").withStreet("STREET").build();
		client = ClientFormBuilder.get().withBirthDate(new Date()).withEmail("n@a.com").withFirstname("f").withIgnore(false).withName("n")
				.withPhone("p").withAddress(AddressFormBuilder.get().withGeocode("GEOCODE").withComponents(addrComp)).build();
		client = soaClient.save(client);
	}

	@Test
	@Transactional
	public void testNoCriteria() throws NoDataFoundException {
		discountBuilder = soaDiscount.save(discountBuilder, "fr");
		doDaoDiscount.flush();
		System.out.println("----------------------------");
		System.out.println(doDaoDiscount.findAll().size());
		// always available
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		Collection<Long> founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
	}

	@Test
	@Transactional
	public void testDateBetween() throws NoDataFoundException {
		MutableDateTime dateFrom = new MutableDateTime();
		dateFrom.addDays(1);
		MutableDateTime dateTo = new MutableDateTime();
		dateTo.addDays(2);
		MutableDateTime dateAfter = new MutableDateTime();
		dateAfter.addDays(3);
		//
		discountBuilder.addLifeRules(
				DiscountLifeCycleRuleType.Date,
				new DiscountLifeCycleRuleBean().setEnable(true).setDateLimited(true).setFrom(dateFrom.toDate()).setHasToDate(true)
						.setTo(dateTo.toDate()));
		discountBuilder = soaDiscount.save(discountBuilder, "fr");
		doDaoDiscount.flush();
		System.out.println("----------------------------");
		System.out.println(doDaoDiscount.findAll().size());
		// today should not be available and not unavailable
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		Collection<Long> founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(0, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// Tomorow should be available and not unavailable
		log.info(String.format("Testing available ", dateFrom.toDate()));
		context.setRelativeTo(dateFrom.toDate());
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// After Tomorow should be available and not unavailable
		context.setRelativeTo(dateTo.toDate());
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// 2 days later should be unavailable
		context.setRelativeTo(dateAfter.toDate());
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(0, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(1, founded.size());
	}

	@Test
	@Transactional
	public void testDateExact() throws NoDataFoundException {
		MutableDateTime dateFrom = new MutableDateTime();
		dateFrom.addDays(1);
		MutableDateTime dateAfter = new MutableDateTime();
		dateAfter.addDays(2);
		//
		discountBuilder.addLifeRules(DiscountLifeCycleRuleType.Date,
				new DiscountLifeCycleRuleBean().setEnable(true).setDateLimited(true).setFrom(dateFrom.toDate()).setExactDay(true));
		discountBuilder = soaDiscount.save(discountBuilder, "fr");
		// today should not be available and not unavailable
		doDaoDiscount.flush();
		System.out.println("----------------------------");
		System.out.println(doDaoDiscount.findAll().size());
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		Collection<Long> founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(0, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// Tomorow should be available and not unavailable
		log.info(String.format("Testing available ", dateFrom.toDate()));
		context.setRelativeTo(dateFrom.toDate());
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// 2 days later should be unavailable
		context.setRelativeTo(dateAfter.toDate());
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(0, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(1, founded.size());
	}

	@Test
	@Transactional
	public void testDatTo() throws NoDataFoundException {
		MutableDateTime dateTo = new MutableDateTime();
		dateTo.addDays(2);
		MutableDateTime dateAfter = new MutableDateTime();
		dateAfter.addDays(3);
		//
		discountBuilder.addLifeRules(DiscountLifeCycleRuleType.Date, new DiscountLifeCycleRuleBean().setEnable(true).setDateLimited(true)
				.setHasToDate(true).setTo(dateTo.toDate()));
		discountBuilder = soaDiscount.save(discountBuilder, "fr");
		Assert.assertNotNull(discountBuilder.getId());
		doDaoDiscount.flush();
		System.out.println("----------------------------");
		System.out.println(doDaoDiscount.findAll().size());
		// today should not be available and not unavailable
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		Collection<Long> founded = lifeCycleProcessor.findAvailable(context);
		System.out.println(String.format("Looking for to %s.", dateTo.toDate()));
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// After Tomorow should be available and not unavailable
		context.setRelativeTo(dateTo.toDate());
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// 2 days later should be unavailable
		context.setRelativeTo(dateAfter.toDate());
		doDaoDiscount.flush();
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(0, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(1, founded.size());
	}

	@Test
	@Transactional
	public void testDatePeriod() throws NoDataFoundException {
		MutableDateTime dateFrom = new MutableDateTime();
		dateFrom.addDays(1);
		MutableDateTime dateToInv = new MutableDateTime();
		dateToInv.addDays(-2);
		dateToInv.addHours(2);
		MutableDateTime dateAfterInv = new MutableDateTime();
		dateAfterInv.addDays(-3);
		//
		discountBuilder.addLifeRules(
				DiscountLifeCycleRuleType.Date,
				new DiscountLifeCycleRuleBean().setEnable(true).setDateLimited(true).setFrom(dateFrom.toDate()).setPeriod(PeriodType.Day)
						.setPeriodNumber(1d)).addLifeRules(DiscountLifeCycleRuleType.Duration,
				new DiscountLifeCycleRuleBean().setEnable(true).setPeriodLimited(true).setPeriod(PeriodType.Day).setPeriodNumber(2d));
		discountBuilder = soaDiscount.save(discountBuilder, "fr");
		doDaoDiscount.flush();
		System.out.println("----------------------------");
		System.out.println(doDaoDiscount.findAll().size());
		// today should not be available and not unavailable
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		Collection<Long> founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(0, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// Tomorow should be available and not unavailable
		log.info(String.format("Testing available relative to %s", dateFrom.toDate()));
		context.setRelativeTo(dateFrom.toDate());
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// 2 day period => avilable
		DiscountLifeCycleState s = new DiscountLifeCycleState(DiscountLifeCycleStateType.ReadyToUse);
		doDaoDiscount.load(discountBuilder.getId()).getLifecycle().add(s);
		doDaoDiscount.flush();
		daoLifeState.updateDate(s.getId(), dateToInv.toDate());
		doDaoDiscount.flush();
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(1, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(0, founded.size());
		// 3 days later should be unavailable
		daoLifeState.updateDate(s.getId(), dateAfterInv.toDate());
		doDaoDiscount.flush();
		founded = lifeCycleProcessor.findAvailable(context);
		Assert.assertEquals(0, founded.size());
		founded = lifeCycleProcessor.findUnAvailable(context);
		Assert.assertEquals(1, founded.size());
	}
}
