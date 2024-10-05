package com.nm.tests.orders;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.orders.constants.OrderActionType;
import com.nm.orders.constants.OrderEventType;
import com.nm.orders.constants.OrderOptions;
import com.nm.orders.constants.OrderStateType;
import com.nm.orders.contracts.OrderAdapter;
import com.nm.orders.contracts.OrderAdapterDefault;
import com.nm.orders.dtos.OrderDto;
import com.nm.orders.dtos.impl.OrderAccountDtoImpl;
import com.nm.orders.dtos.impl.OrderDetailsDtoDefault;
import com.nm.orders.dtos.impl.OrderRequestDtoImpl;
import com.nm.orders.dtos.impl.OrderViewDtoImpl;
import com.nm.orders.models.Order;
import com.nm.orders.models.OrderState;
import com.nm.orders.soa.SoaOrder;
import com.nm.paiments.constants.TransactionStateType;
import com.nm.paiments.daos.DaoTransaction;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionState;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestOrder.class)
public class TestWorkflowPrePayment {
	@Autowired
	private SoaOrder soaOrder;
	private IGenericDao<Order, Long> daoOrder;
	@Autowired
	private DaoTransaction daotransaction;
	//
	private OptionsList options = new OptionsList();
	private OrderAdapter adapter = new OrderAdapterDefault();
	private Order order;
	private Transaction transaction;
	private OrderDto dto;

	@org.junit.Before
	public void setup() throws Exception {
		daoOrder = AbstractGenericDao.get(Order.class);
		OrderViewDtoImpl dto = new OrderViewDtoImpl();
		dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
		dto.setSeller(new OrderAccountDtoImpl().setAny(true));
		dto.setDetails(new OrderDetailsDtoDefault().setAmount(2D).setDetails("Ma commande"));
		soaOrder.getOrCreate(dto, adapter, new OptionsList());
		daoOrder.flush();
		Assert.assertNotNull(dto.getId());
		order = daoOrder.get(dto.getId());
		daoOrder.refresh(order);
		transaction = daotransaction.findBySubject(order.getTransaction());
		Assert.assertNotNull(transaction.getId());
		options.withOption(OrderOptions.States, OrderOptions.OrderFlow);
		this.dto = new OrderViewDtoImpl(order.getId());
	}

	@Test
	@Transactional
	public void testShouldPrepare() throws Exception {
		dto = new OrderViewDtoImpl(order.getId());
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl(), adapter,
				new OptionsList().withOption(OrderOptions.States));
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Initial));
		Assert.assertEquals(OrderStateType.Initial, v.getStates().iterator().next().getState());
		daoOrder.flush();
		daoOrder.refresh(order);
	}

	@Test
	@Transactional
	public void testShouldBeWaitingPayment() throws Exception {
		dto = new OrderViewDtoImpl(order.getId());
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl(), adapter, options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Initial));
		Assert.assertTrue(v.contains(OrderStateType.WaitingPayment));
		Assert.assertEquals(OrderStateType.WaitingPayment, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Initial:
			case WaitingPayment: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Paid: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			default: {
				Assert.assertEquals(OrderActionType.CouldOccure, v.getStatesFlow().get(key));
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldBePaid() throws Exception {
		order.add(new OrderState(OrderStateType.WaitingPayment));
		transaction.add(new TransactionState(TransactionStateType.Commit));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl(), adapter, options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Paid));
		Assert.assertEquals(OrderStateType.Paid, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case WaitingPayment:
			case Paid: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Preparing: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			case Prepared:
			case Delivering:
			case Delivered: {
				Assert.assertEquals(OrderActionType.CanOccureFar, v.getStatesFlow().get(key));
				break;
			}
			default: {
				Assert.assertEquals(OrderActionType.CouldOccure, v.getStatesFlow().get(key));
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldBePreparing() throws Exception {
		order.add(new OrderState(OrderStateType.Paid));
		daoOrder.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.PreparingEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Preparing));
		Assert.assertEquals(OrderStateType.Preparing, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Paid:
			case Preparing: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Prepared: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			case Delivering:
			case Delivered: {
				Assert.assertEquals(OrderActionType.CanOccureFar, v.getStatesFlow().get(key));
				break;
			}
			default: {
				Assert.assertEquals(OrderActionType.CouldOccure, v.getStatesFlow().get(key));
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldPreparedFromPaid() throws Exception {
		order.add(new OrderState(OrderStateType.Paid));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.PreparedEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Prepared));
		Assert.assertEquals(OrderStateType.Prepared, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Paid:
			case Preparing:
			case Prepared: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Delivering: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			case Delivered: {
				Assert.assertEquals(OrderActionType.CanOccureFar, v.getStatesFlow().get(key));
				break;
			}
			default: {
				Assert.assertEquals(OrderActionType.CouldOccure, v.getStatesFlow().get(key));
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldPreparedFromPreparing() throws Exception {
		order.add(new OrderState(OrderStateType.Preparing));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.PreparedEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Prepared));
		Assert.assertEquals(OrderStateType.Prepared, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Preparing:
			case Prepared: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Delivering: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			case Delivered: {
				Assert.assertEquals(OrderActionType.CanOccureFar, v.getStatesFlow().get(key));
				break;
			}
			default: {
				Assert.assertEquals(OrderActionType.CouldOccure, v.getStatesFlow().get(key));
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeliveringFromPaid() throws Exception {
		order.add(new OrderState(OrderStateType.Paid));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveringEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Delivering));
		Assert.assertEquals(OrderStateType.Delivering, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Paid:
			case Preparing:
			case Prepared:
			case Delivering: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Delivered: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeliveringFromPreparing() throws Exception {
		order.add(new OrderState(OrderStateType.Preparing));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveringEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Delivering));
		Assert.assertEquals(OrderStateType.Delivering, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Preparing:
			case Prepared:
			case Delivering: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Delivered: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeliveringFromPrepared() throws Exception {
		order.add(new OrderState(OrderStateType.Prepared));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveringEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Delivering));
		Assert.assertEquals(OrderStateType.Delivering, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Prepared:
			case Delivering: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			case Delivered: {
				Assert.assertEquals(OrderActionType.CanOccure, v.getStatesFlow().get(key));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeliveredFromPaid() throws Exception {
		order.add(new OrderState(OrderStateType.Paid));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Delivered));
		Assert.assertEquals(OrderStateType.Delivered, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Paid:
			case Preparing:
			case Prepared:
			case Delivering:
			case Delivered: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeliveredFromPreparing() throws Exception {
		order.add(new OrderState(OrderStateType.Preparing));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Delivered));
		Assert.assertEquals(OrderStateType.Delivered, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Preparing:
			case Prepared:
			case Delivering:
			case Delivered: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeliveredFromPrepared() throws Exception {
		order.add(new OrderState(OrderStateType.Prepared));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Delivered));
		Assert.assertEquals(OrderStateType.Delivered, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Prepared:
			case Delivering:
			case Delivered: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeliveredFromDelivering() throws Exception {
		order.add(new OrderState(OrderStateType.Delivering));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.Delivered));
		Assert.assertEquals(OrderStateType.Delivered, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Delivering:
			case Delivered: {
				Assert.assertEquals(OrderActionType.Occured, v.getStatesFlow().get(key));
				break;
			}
			default: {
				break;
			}
			}
		}
	}

}
