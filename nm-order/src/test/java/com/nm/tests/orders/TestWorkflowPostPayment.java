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
import com.nm.orders.contracts.OrderAdapterDefaultPost;
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
public class TestWorkflowPostPayment {
	@Autowired
	private SoaOrder soaOrder;
	private IGenericDao<Order, Long> daoOrder;
	@Autowired
	private DaoTransaction daotransaction;
	//
	private OptionsList options = new OptionsList();
	private OrderAdapter adapter = new OrderAdapterDefaultPost();
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
		order.add(new OrderState(OrderStateType.Delivered));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.PayingEvent), adapter,
				options);
		dto = new OrderViewDtoImpl(order.getId());
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl(), adapter, options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.WaitingPayment);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.WaitingPayment);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccure, OrderStateType.Paid);
	}

	@Test
	@Transactional
	public void testShouldBePaid() throws Exception {
		order.add(new OrderState(OrderStateType.WaitingPayment));
		transaction.add(new TransactionState(TransactionStateType.Commit));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl(), adapter, options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.Paid);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.WaitingPayment);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.Paid);
	}

	@Test
	@Transactional
	public void testShouldBePreparing() throws Exception {
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.PreparingEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.Preparing);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.Initial,
				OrderStateType.Preparing);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccure, OrderStateType.Prepared);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccureFar, OrderStateType.Delivering,
				OrderStateType.Delivered);
	}

	@Test
	@Transactional
	public void testShouldPreparedFromInitial() throws Exception {
		order.add(new OrderState(OrderStateType.Initial));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.PreparedEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.Prepared);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.Initial,
				OrderStateType.Preparing, OrderStateType.Prepared);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccure, OrderStateType.Delivering);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccureFar, OrderStateType.Delivered);
	}

	@Test
	@Transactional
	public void testShouldPreparedFromPreparing() throws Exception {
		order.add(new OrderState(OrderStateType.Preparing));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.PreparedEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.Prepared);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.Preparing,
				OrderStateType.Prepared);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccure, OrderStateType.Delivering);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccureFar, OrderStateType.Delivered);
	}

	@Test
	@Transactional
	public void testShouldDeliveringFromInitial() throws Exception {
		order.add(new OrderState(OrderStateType.Initial));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveringEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.Delivering);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.Preparing,
				OrderStateType.Prepared, OrderStateType.Delivering);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccure, OrderStateType.Delivered);
	}

	@Test
	@Transactional
	public void testShouldDeliveringFromPreparing() throws Exception {
		order.add(new OrderState(OrderStateType.Preparing));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveringEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.Delivering);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.Preparing,
				OrderStateType.Prepared, OrderStateType.Delivering);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccure, OrderStateType.Delivered);
	}

	@Test
	@Transactional
	public void testShouldDeliveringFromPrepared() throws Exception {
		order.add(new OrderState(OrderStateType.Prepared));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveringEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		HelperTestWorkflow.checkIfStateLast(v, OrderStateType.Delivering);
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.Occured, OrderStateType.Prepared,
				OrderStateType.Delivering);
		HelperTestWorkflow.hasActionForStates(v, OrderActionType.CanOccure, OrderStateType.Delivered);
	}

	@Test
	@Transactional
	public void testShouldWaitingPaymentFromPaid() throws Exception {
		order.add(new OrderState(OrderStateType.Initial));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.WaitingPayment));
		Assert.assertEquals(OrderStateType.WaitingPayment, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Initial:
			case Preparing:
			case Prepared:
			case Delivering:
			case Delivered:
			case WaitingPayment: {
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
	public void testShouldWaitingPaymentFromPreparing() throws Exception {
		order.add(new OrderState(OrderStateType.Preparing));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.WaitingPayment));
		Assert.assertEquals(OrderStateType.WaitingPayment, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Preparing:
			case Prepared:
			case Delivering:
			case Delivered:
			case WaitingPayment: {
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
	public void testShouldWaitingPaymentFromPrepared() throws Exception {
		order.add(new OrderState(OrderStateType.Prepared));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.WaitingPayment));
		Assert.assertEquals(OrderStateType.WaitingPayment, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Prepared:
			case Delivering:
			case Delivered:
			case WaitingPayment: {
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
	public void testShouldWaitingPaymentFromDelivering() throws Exception {
		order.add(new OrderState(OrderStateType.Delivering));
		daotransaction.flush();
		dto = soaOrder.operation(dto, new OrderRequestDtoImpl().withEvent(OrderEventType.DeliveredEvent), adapter,
				options);
		OrderViewDtoImpl v = (OrderViewDtoImpl) dto;
		Assert.assertTrue(v.contains(OrderStateType.WaitingPayment));
		Assert.assertEquals(OrderStateType.WaitingPayment, v.getLastState().getState());
		//
		Assert.assertEquals(OrderStateType.values().length, v.getStatesFlow().size());
		for (OrderStateType key : v.getStatesFlow().keySet()) {
			System.out.println("Key testing ...." + key);
			switch (key) {
			case Delivering:
			case Delivered:
			case WaitingPayment: {
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
