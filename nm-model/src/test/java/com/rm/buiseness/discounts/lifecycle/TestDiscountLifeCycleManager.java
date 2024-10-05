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

import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.builders.AddressComponentFormBuilder;
import com.rm.contract.clients.builders.AddressFormBuilder;
import com.rm.contract.clients.builders.ClientFormBuilder;
import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.contract.discounts.beans.DiscountLifeCycleRuleBean;
import com.rm.contract.discounts.beans.DiscountLifeCycleTrackingRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleSubjectBean;
import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.contract.discounts.constants.DiscountOperationType;
import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.dao.discounts.DaoDiscount;
import com.rm.dao.discounts.DaoDiscountTracking;
import com.rm.dao.discounts.DaoDiscountTrackingLifeCycleState;
import com.rm.dao.discounts.impl.DiscountQueryBuilder;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingLifeCycleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.tracking.DiscountTracking;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleState;
import com.rm.soa.clients.SoaClient;
import com.rm.soa.discounts.SoaDiscount;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleManager;
import com.rm.utils.dao.NoDataFoundException;
import com.rm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestDiscountLifeCycleManager {

	@Autowired
	private SoaDiscount soaDiscount;
	@Autowired
	private SoaClient soaClient;

	@Autowired
	private DaoDiscount doDaoDiscount;
	@Autowired
	private DaoDiscountTracking daoDiscountPlanning;
	@Autowired
	private DiscountLifeCycleManager lifeCycleManager;
	@Autowired
	private DaoDiscountTrackingLifeCycleState daoState;
	//
	private DiscountFormBean discountBuilder;
	private ClientForm client1, client2;
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
	}

	@Test
	@Transactional
	public void testLifeCycleManager() throws NoDataFoundException {
		//
		DiscountFormBean promo1 = discountBuilder
				.addLifeRules(DiscountLifeCycleRuleType.AbsoluteCount,
						new DiscountLifeCycleRuleBean().setEnable(true).setCountLimited(true).setCountMax(3))
				.addTrackingLifeRules(DiscountTrackingLifeCycleRuleType.RelativeCount,
						new DiscountLifeCycleTrackingRuleBean().setEnable(true).setLimitedCount(true).setCountMax(2))
				.addTrackingLifeRules(
						DiscountTrackingLifeCycleRuleType.RecurrentPeriod,
						new DiscountLifeCycleTrackingRuleBean().setEnable(true).setPeriodic(true).setEveryPeriod(PeriodType.Day)
								.setEveryPeriodNumber(2));
		promo1 = soaDiscount.save(promo1, "fr");
		doDaoDiscount.flush();
		System.out.println("----------------------------");
		System.out.println(doDaoDiscount.findAll().size());
		{
			doDaoDiscount.flush();
			int size = doDaoDiscount.find(
					DiscountQueryBuilder.get().withLifeCycle(
							DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse))).size();
			Assert.assertEquals(0, size);
		}
		// today should not be available and not unavailable
		this.lifeCycleManager.checkForStart();
		this.lifeCycleManager.checkForStart();
		{
			doDaoDiscount.flush();
			int size = doDaoDiscount.find(
					DiscountQueryBuilder.get().withLifeCycle(
							DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse))).size();
			Assert.assertEquals(1, size);
		}
		{
			doDaoDiscount.flush();
			int size = daoDiscountPlanning.findIds(
					DiscountTrackingQueryBuilder.get().withLifeCycle(
							DiscountTrackingLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse))).size();
			Assert.assertEquals(2, size);
		}
		{
			Collection<DiscountDefinition> dis = doDaoDiscount.find(DiscountQueryBuilder.get().withLifeCycle(
					DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse)));
			Assert.assertEquals(2, dis.iterator().next().getLifecycle().getStates().size());
		}
		{
			Collection<DiscountTracking> dis = daoDiscountPlanning.find(DiscountTrackingQueryBuilder.get().withLifeCycle(
					DiscountTrackingLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse)));
			Assert.assertEquals(2, dis.iterator().next().getLifecycle().getStates().size());
		}
		//
		this.lifeCycleManager.checkForStop();
		this.lifeCycleManager.checkForStop();
		{
			doDaoDiscount.flush();
			int size = doDaoDiscount.find(
					DiscountQueryBuilder.get().withLifeCycle(
							DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.Unplanned))).size();
			Assert.assertEquals(0, size);
		}
		// Use two time => not available
		soaDiscount.use(client1.getId(), promo1.getId());
		soaDiscount.use(client2.getId(), promo1.getId());
		soaDiscount.use(client1.getId(), promo1.getId());
		daoDiscountPlanning.flush();
		//
		{
			doDaoDiscount.flush();
			int size = doDaoDiscount.find(
					DiscountQueryBuilder.get().withLifeCycle(
							DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.Unplanned))).size();
			Assert.assertEquals(0, size);
		}
		this.lifeCycleManager.checkForStop();
		this.lifeCycleManager.checkForStop();
		{
			doDaoDiscount.flush();
			int size = doDaoDiscount.find(
					DiscountQueryBuilder.get().withLifeCycle(
							DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.Unplanned))).size();
			Assert.assertEquals(1, size);
		}
		{
			// Only one tracking disable
			doDaoDiscount.flush();
			int size = daoDiscountPlanning.find(
					DiscountTrackingQueryBuilder.get().withLifeCycle(
							DiscountTrackingLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.Unplanned))).size();
			Assert.assertEquals(1, size);
		}
		{
			doDaoDiscount.flush();
			Collection<DiscountDefinition> dis = doDaoDiscount.find(DiscountQueryBuilder.get().withLifeCycle(
					DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.Unplanned)));
			Assert.assertEquals(3, dis.iterator().next().getLifecycle().getStates().size());
		}//
		this.lifeCycleManager.checkForStart();
		{
			doDaoDiscount.flush();
			int size = doDaoDiscount.find(
					DiscountQueryBuilder.get().withLifeCycle(
							DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse))).size();
			Assert.assertEquals(0, size);
		}
		// Check for restart
		{
			Collection<DiscountTracking> c = daoDiscountPlanning.find(DiscountTrackingQueryBuilder.get().withClient(client1.getId()));
			c.addAll(daoDiscountPlanning.find(DiscountTrackingQueryBuilder.get().withClient(client2.getId())));
			int nbUpdate = 0;
			for (DiscountTracking t : c) {
				for (DiscountTrackingLifeCycleState s : t.getLifecycle().getStates()) {
					if (s.getType().equals(DiscountLifeCycleStateType.ReadyToUse)) {
						// 3 days limit
						MutableDateTime from3 = new MutableDateTime();
						from3.addDays(-3);
						daoState.updateDate(s.getId(), from3.toDate());
						nbUpdate++;
					}
				}

			}
			Assert.assertEquals(2, nbUpdate);
		}
		daoState.flush();
		lifeCycleManager.checkForRestart();
		lifeCycleManager.checkForRestart();
		{
			doDaoDiscount.flush();
			int size = daoDiscountPlanning.find(
					DiscountTrackingQueryBuilder.get().withLifeCycle(
							DiscountTrackingLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse))).size();
			Assert.assertEquals(1, size);
		}
		// Client2 still available =< use two times
		soaDiscount.use(client2.getId(), promo1.getId());
		// Must stop before restart
		this.lifeCycleManager.checkForStop();
		lifeCycleManager.checkForRestart();
		lifeCycleManager.checkForRestart();
		{
			doDaoDiscount.flush();
			int size = daoDiscountPlanning.find(
					DiscountTrackingQueryBuilder.get().withLifeCycle(
							DiscountTrackingLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse))).size();
			Assert.assertEquals(2, size);
		}
		{
			Collection<DiscountTracking> dis = daoDiscountPlanning.find(DiscountTrackingQueryBuilder.get().withLifeCycle(
					DiscountTrackingLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.ReadyToUse)));
			DiscountTracking first = dis.iterator().next();
			Assert.assertEquals(2, first.getLifecycle().getStates().size());
			Assert.assertEquals(2, first.getHistory().size());
		}
	}
}
