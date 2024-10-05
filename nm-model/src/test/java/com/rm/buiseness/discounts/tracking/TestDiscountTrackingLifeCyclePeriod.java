package com.rm.buiseness.discounts.tracking;

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
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.builders.AddressComponentFormBuilder;
import com.rm.contract.clients.builders.AddressFormBuilder;
import com.rm.contract.clients.builders.ClientFormBuilder;
import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.contract.discounts.beans.DiscountLifeCycleTrackingRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleSubjectBean;
import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.contract.discounts.constants.DiscountOperationType;
import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.dao.discounts.DaoDiscount;
import com.rm.dao.discounts.DaoDiscountTracking;
import com.rm.dao.discounts.DaoDiscountTrackingLifeCycleState;
import com.rm.model.discounts.tracking.DiscountTracking;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleState;
import com.rm.soa.clients.SoaClient;
import com.rm.soa.discounts.SoaDiscount;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleContext;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleProcessor;
import com.rm.utils.dao.NoDataFoundException;
import com.rm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestDiscountTrackingLifeCyclePeriod {

	@Autowired
	private SoaDiscount soaDiscount;
	@Autowired
	private SoaClient soaClient;
	@Autowired
	private DiscountTrackingLifeCycleProcessor trackProcessor;
	@Autowired
	private DaoDiscount doDaoDiscount;
	@Autowired
	private DaoDiscountTracking daoDiscountPlanning;
	@Autowired
	private DaoDiscountTrackingLifeCycleState daoState;
	//
	private DiscountFormBean discountBuilder;
	private ClientForm client1;
	//
	protected Log log = LogFactory.getLog(getClass());

	@Before
	public void globalSetUp() throws NoDataFoundException {
		discountBuilder = new DiscountFormBean().setName("PROMO1").addRule(
				new DiscountRuleBean().setOperation(true).setOperationType(DiscountOperationType.Fixe).setOperationValue(10d)
						.setSubject(new DiscountRuleSubjectBean().setAllProducts(true)));
		client1 = ClientFormBuilder
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
		client1 = soaClient.save(client1);
	}

	// TODO order criteria
	// TODO g�rer les rules
	// TODO mettre � jour la gestion des ageorder et ageclient et period
	// discount (utiliser les interval comme pour tracking et lifecycle)
	@Test
	@Transactional
	public void testLimitPeriod() throws NoDataFoundException {
		DiscountFormBean promo1 = discountBuilder.addTrackingLifeRules(DiscountTrackingLifeCycleRuleType.Duration,
				new DiscountLifeCycleTrackingRuleBean().setEnable(true).setLimitedPeriod(true).setPeriod(PeriodType.Day).setPeriodNumber(2));
		promo1 = soaDiscount.save(promo1, "fr");
		doDaoDiscount.flush();
		System.out.println("----------------------------");
		System.out.println(doDaoDiscount.findAll().size());
		//
		DiscountTrackingLifeCycleContext context = new DiscountTrackingLifeCycleContext();
		// No tracking
		Collection<Long> founded = trackProcessor.findActive(context);
		Assert.assertEquals(0, founded.size());
		founded = trackProcessor.findInactive(context);
		Assert.assertEquals(0, founded.size());
		// Use once client 1=> available
		DiscountTracking t = soaDiscount.use(client1.getId(), promo1.getId());
		DiscountTrackingLifeCycleState d = new DiscountTrackingLifeCycleState(DiscountLifeCycleStateType.ReadyToUse);
		t.getLifecycle().add(d);
		daoDiscountPlanning.saveOrUpdate(t);
		daoDiscountPlanning.flush();
		founded = trackProcessor.findActive(context);
		Assert.assertEquals(1, founded.size());
		founded = trackProcessor.findInactive(context);
		Assert.assertEquals(0, founded.size());
		// 1 day limit
		MutableDateTime yesterday = new MutableDateTime();
		yesterday.addDays(-1);
		daoState.updateDate(d.getId(), yesterday.toDate());
		daoDiscountPlanning.flush();
		founded = trackProcessor.findActive(context);
		Assert.assertEquals(1, founded.size());
		founded = trackProcessor.findInactive(context);
		Assert.assertEquals(0, founded.size());
		// 3 days limit
		MutableDateTime from3 = new MutableDateTime();
		from3.addDays(-3);
		daoState.updateDate(d.getId(), from3.toDate());
		daoDiscountPlanning.flush();
		founded = trackProcessor.findActive(context);
		Assert.assertEquals(0, founded.size());
		founded = trackProcessor.findInactive(context);
		Assert.assertEquals(1, founded.size());
	}
}
