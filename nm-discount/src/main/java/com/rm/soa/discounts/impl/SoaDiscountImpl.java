package com.rm.soa.discounts.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.rm.app.ModelOptions;
import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.contract.discounts.constants.DiscountOptions;
import com.rm.dao.discounts.DaoDiscount;
import com.rm.dao.discounts.DaoDiscountTracking;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleState;
import com.rm.model.discounts.tracking.DiscountTracking;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleState;
import com.rm.soa.discounts.SoaDiscount;
import com.rm.soa.discounts.converters.DiscountConverter;
import com.rm.soa.discounts.lifecycle.beans.DiscountLifeCyclePlanBean;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Service
public class SoaDiscountImpl implements SoaDiscount {

	@Autowired
	private DaoDiscount daoDiscount;
	@Autowired
	private DaoDiscountTracking daoDiscountPlanning;
	@Autowired
	private DiscountConverter discountConverter;

	protected DiscountTracking create(Client client, DiscountDefinition discount) {
		DiscountTracking tracking = new DiscountTracking();
		tracking.setClient((client));
		tracking.setDiscount((discount));
		daoDiscountPlanning.saveOrUpdate(tracking);
		tracking.pushLifeCycle();
		return tracking;
	}

	@Transactional()
	public DiscountFormBean save(DiscountFormBean bean, String lang) throws NoDataFoundException {
		DiscountDefinition discount = new DiscountDefinition();
		if (bean.getId() != null) {
			discount = daoDiscount.loadById(bean.getId());
			discount.getCommunication().getContents().clear();
			discount.getClientCriterias().getRules().clear();
			discount.getLifeRules().clear();
			discount.getTrackingRules().clear();
			daoDiscount.saveOrUpdate(discount);
		}
		//
		discount = discountConverter.convert(discount, bean);
		daoDiscount.saveOrUpdate(discount);
		//
		bean.setId(discount.getId());
		return bean;
	}

	@Transactional(readOnly = true)
	public DiscountFormBean edit(Long id, String lang) throws NoDataFoundException {
		DiscountDefinition discount = daoDiscount.loadById(id);
		Collection<ModelOptions> options = new ArrayList<ModelOptions>();
		options.addAll(Lists.newArrayList(PriceOptions.values()));
		options.addAll(Lists.newArrayList(ProductOptions.Parts));
		options.addAll(Lists.newArrayList(DiscountOptions.values()));
		return discountConverter.convert(discount, lang, options);
	}

	@Transactional()
	public DiscountDefinition delete(Long id) throws NoDataFoundException {
		DiscountDefinition discount = daoDiscount.loadById(id);
		// Delete
		daoDiscount.delete(discount);
		return discount;
	}

	@Autowired
	private DaoClient daoClient;

	@Transactional()
	public DiscountTracking use(Long client, Long discount) throws NoDataFoundException {
		DiscountTrackingQueryBuilder q1 = DiscountTrackingQueryBuilder.get();
		q1.withClient(client).withDiscount(discount);
		Collection<DiscountTracking> trackings = daoDiscountPlanning.find(q1);
		DiscountTracking tracking = null;
		if (trackings.isEmpty()) {
			tracking = create(daoClient.loadById(client), daoDiscount.loadById(discount));
		} else {
			tracking = trackings.iterator().next();
		}
		tracking.getLifecycle().add(new DiscountTrackingLifeCycleState(DiscountLifeCycleStateType.Used));
		daoDiscountPlanning.saveOrUpdate(tracking);
		return tracking;
	}

	@Transactional(readOnly = true)
	public Collection<DiscountFormBean> fetch(String lang) throws NoDataFoundException {
		Collection<DiscountFormBean> beans = new ArrayList<DiscountFormBean>();
		Collection<DiscountDefinition> discounts = daoDiscount.find(DetachedCriteria.forClass(DiscountDefinition.class));
		for (DiscountDefinition dis : discounts) {
			beans.add(discountConverter.convert(dis, lang, new ArrayList<ModelOptions>()));
		}
		return beans;
	}

	@Autowired
	private DaoDiscountTracking daoDiscountTracking;

	public void plan(Collection<DiscountLifeCyclePlanBean> plans) {
		Collection<Long> allClients = new HashSet<Long>();
		//
		for (DiscountLifeCyclePlanBean p : plans) {
			allClients.add(p.getClient());
		}
		Collection<Client> clients = daoClient.findByIds(allClients);
		Map<Long, Client> clientsById = new HashMap<Long, Client>();
		for (Client c : clients) {
			clientsById.put(c.getId(), c);
		}
		// Unique plan
		Table<Long, Long, DiscountTracking> table = HashBasedTable.create();
		if (!plans.isEmpty()) {
			DiscountTrackingQueryBuilder q1 = DiscountTrackingQueryBuilder.get();
			q1.withDisjunction();
			for (DiscountLifeCyclePlanBean p : plans) {
				q1.withDisjunction(clientsById.get(p.getClient()), p.getDiscount());
			}
			Collection<DiscountTracking> tracking = daoDiscountPlanning.find(q1);
			for (DiscountTracking t : tracking) {
				table.put(t.getClientId(), t.getDiscountId(), t);
			}
		}
		//
		for (DiscountLifeCyclePlanBean p : plans) {
			if (!table.contains(p.getClient(), p.getDiscount().getId())) {
				DiscountTracking tracking = new DiscountTracking();
				tracking.setClient(clientsById.get(p.getClient()));
				tracking.setDiscount(p.getDiscount());
				daoDiscountTracking.saveOrUpdate(tracking);
				//
				tracking.getLifecycle().add(new DiscountTrackingLifeCycleState(DiscountLifeCycleStateType.ReadyToUse));
				// Only one time
				table.put(p.getClient(), p.getDiscount().getId(), tracking);
			}
		}
	}

	public void start(DiscountDefinition discount) {
		if (!discount.getLifecycle().getStates().isEmpty()) {
			discount.pushLifeCycle();
		}
		discount.getLifecycle().add(new DiscountLifeCycleState(DiscountLifeCycleStateType.ReadyToUse));
	}

	public void stop(DiscountDefinition discount) {
		discount.getLifecycle().add(new DiscountLifeCycleState(DiscountLifeCycleStateType.Unplanned));
	}

	public void start(DiscountTracking tracking) {
		if (!tracking.getLifecycle().getStates().isEmpty()) {
			tracking.pushLifeCycle();
		}
		tracking.getLifecycle().add(new DiscountTrackingLifeCycleState(DiscountLifeCycleStateType.ReadyToUse));
	}

	public void stop(DiscountTracking tracking) {
		tracking.getLifecycle().add(new DiscountTrackingLifeCycleState(DiscountLifeCycleStateType.Unplanned));
	}

	public boolean available(DiscountTracking tracking) {
		DiscountTrackingLifeCycleState trState = tracking.getLifecycle().getLastState();
		DiscountLifeCycleState lastState = tracking.getDiscount().getLifecycle().getLastState();
		boolean disOk = true, trOk = true;
		disOk = disOk && !tracking.getLifecycle().isDisabled();
		if (trState != null) {
			trOk = trOk && !trState.getType().equals(DiscountLifeCycleStateType.Unplanned);
		}
		if (lastState != null) {
			disOk = disOk && !lastState.getType().equals(DiscountLifeCycleStateType.Unplanned);

		}
		return disOk && trOk;
	}
}
