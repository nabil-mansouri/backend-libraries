package com.rm.soa.discounts.lifecycle.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.dao.discounts.DaoDiscount;
import com.rm.dao.discounts.DaoDiscountTracking;
import com.rm.dao.discounts.impl.DiscountQueryBuilder;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingLifeCycleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.tracking.DiscountTracking;
import com.rm.soa.discounts.SoaDiscount;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleContext;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleManager;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleProcessor;
import com.rm.soa.discounts.lifecycle.beans.DiscountLifeCyclePlanBean;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleContext;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleProcessor;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DiscountLifeCycleManagerImpl implements DiscountLifeCycleManager {
	@Autowired
	private DiscountLifeCycleProcessor lifeCycleProcessor;
	@Autowired
	private DiscountTrackingLifeCycleProcessor trackingProcessor;
	@Autowired
	private ClientFinderProcessor clientFinderProcessor;
	@Autowired
	private DaoDiscount daoDiscount;
	@Autowired
	private SoaDiscount soaDiscount;
	@Autowired
	private DaoDiscountTracking daoDiscountTracking;

	public void checkForStart() {
		// Start new discount (only discount not ReadyToUse)
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		context.setBase(DiscountQueryBuilder.get().withLifeCycle(
				DiscountLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.Planned)));
		Collection<Long> discountIds = lifeCycleProcessor.findAvailable(context);
		Collection<DiscountLifeCyclePlanBean> results = new ArrayList<DiscountLifeCyclePlanBean>();
		Collection<DiscountDefinition> allDiscounts = daoDiscount.findByIds(discountIds);
		for (DiscountDefinition discount : allDiscounts) {
			soaDiscount.start(discount);
		}
		// Check new client for already started
		DiscountQueryBuilder q1 = DiscountQueryBuilder.get().withIdNot(discountIds);
		DiscountLifeCycleQueryBuilder q2 = DiscountLifeCycleQueryBuilder.get().withStateType(DiscountLifeCycleStateType.ReadyToUse);
		q1.withLifeCycle(q2);
		allDiscounts.addAll(daoDiscount.findByIds(discountIds));
		//
		for (DiscountDefinition discount : allDiscounts) {
			ClientCriterias criteria = discount.getClientCriterias();
			Collection<Long> clientIds = clientFinderProcessor.find(criteria);
			for (Long client : clientIds) {
				DiscountLifeCyclePlanBean res = new DiscountLifeCyclePlanBean();
				res.setClient(client);
				res.setDiscount(discount);
				results.add(res);
			}
		}
		soaDiscount.plan(results);
	}

	public void checkForStop() {
		// stop only discount not Unplanned
		{
			DiscountLifeCycleContext context = new DiscountLifeCycleContext();
			context.setBase(DiscountQueryBuilder.get().withLifeCycle(
					DiscountLifeCycleQueryBuilder.get().withNotLastStateType(DiscountLifeCycleStateType.Unplanned)));
			Collection<Long> discountIds = lifeCycleProcessor.findUnAvailable(context);
			Collection<DiscountDefinition> discounts = daoDiscount.findByIds(discountIds);
			for (DiscountDefinition d : discounts) {
				soaDiscount.stop(d);
			}
		}
		// stop only tracking not Unplanned
		{
			DiscountTrackingLifeCycleContext context = new DiscountTrackingLifeCycleContext();
			context.setBase(DiscountTrackingQueryBuilder.get().withLifeCycle(
					DiscountTrackingLifeCycleQueryBuilder.get().withNotLastStateType(DiscountLifeCycleStateType.Unplanned)));
			Collection<Long> trackingIds = trackingProcessor.findInactive(context);
			Collection<DiscountTracking> trackings = daoDiscountTracking.findByIds(trackingIds);
			for (DiscountTracking t : trackings) {
				soaDiscount.stop(t);
			}
		}

	}

	public void checkForRestart() {
		DiscountTrackingLifeCycleContext context = new DiscountTrackingLifeCycleContext();
		checkForRestart(context);
	}

	public void checkForRestart(DiscountTrackingLifeCycleContext context) {
		// restart only unplanned
		context.setBase(DiscountTrackingQueryBuilder.get().withLifeCycle(
				DiscountTrackingLifeCycleQueryBuilder.get().withLastStateType(DiscountLifeCycleStateType.Unplanned)));

		Collection<Long> trackingIds = trackingProcessor.findRestart(context);
		Collection<DiscountTracking> trackings = daoDiscountTracking.findByIds(trackingIds);
		for (DiscountTracking t : trackings) {
			soaDiscount.start(t);
		}
	}

}
