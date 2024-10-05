package com.nm.tests.orders;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.orders.constants.OrderTreeNodeType.OrderTreeNodeTypeDefault;
import com.nm.orders.contracts.OrderAdapter;
import com.nm.orders.contracts.OrderAdapterDefault;
import com.nm.orders.dao.DaoOrderAccount;
import com.nm.orders.dao.QueryOrderAccountBuilder;
import com.nm.orders.dtos.OrderDetailsDto;
import com.nm.orders.dtos.impl.OrderAccountDtoImpl;
import com.nm.orders.dtos.impl.OrderDetailsDtoDefault;
import com.nm.orders.dtos.impl.OrderDetailsDtoTree;
import com.nm.orders.dtos.impl.OrderViewDtoImpl;
import com.nm.orders.models.Order;
import com.nm.orders.models.OrderAccountAny;
import com.nm.orders.models.OrderAccountSimple;
import com.nm.orders.models.OrderDetails;
import com.nm.orders.models.OrderDetailsTree;
import com.nm.orders.models.OrderDetailsTreeNode;
import com.nm.orders.soa.SoaOrder;
import com.nm.paiments.contract.PaimentAdapter;
import com.nm.paiments.contract.PaimentAdapterDefault;
import com.nm.paiments.daos.DaoTransaction;
import com.nm.paiments.dtos.impl.TransactionDtoImpl;
import com.nm.paiments.dtos.impl.TransactionSubjectSimpleDtoImpl;
import com.nm.paiments.models.Transaction;
import com.nm.payment.soa.SoaPaiment;
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
public class TestCrudOrders {
	// TODO cart checkers (if load cart many days after)
	// TODO graph parent checker
	// TODO bridge for cart and orders
	// TODO rule criteria
	// TODO remove rule processors
	// TODO statistics
	@Autowired
	private SoaOrder soaOrder;
	@Autowired
	private DaoTransaction daoTransaction;
	@Autowired
	private SoaPaiment soaPaiment;
	private IGenericDao<Order, Long> daoOrder;
	@Autowired
	private DaoOrderAccount daoAccount;
	private OrderAdapter adapter = new OrderAdapterDefault();
	private OrderAdapter adapterTree = new OrderAdapterDefault() {
		private static final long serialVersionUID = 1L;

		public Class<? extends OrderDetails> entityDetailsClass() {
			return OrderDetailsTree.class;
		}

		@Override
		public Class<? extends OrderDetailsDto> dtoDetailsClass() {
			return OrderDetailsDtoTree.class;
		}
	};
	private PaimentAdapter adapterP = new PaimentAdapterDefault();
	private OrderDetailsDtoTree tree;

	@Before
	public void setup() {
		daoOrder = AbstractGenericDao.get(Order.class);
		tree = new OrderDetailsDtoTree().setAmount(2D).setDetails("Ma commande");
		tree.createChildren(true).setAmount(1d).setDetails("Article 1").setType(OrderTreeNodeTypeDefault.Product)
				.setReference(1l)//
				.createChildren(false).setAmount(0d).setDetails("Couleurs bleu").setReference(2l)
				.setType(OrderTreeNodeTypeDefault.Caracteristics);
		tree.createChildren(true).setReference(2l).setAmount(1d).setDetails("Article 1")
				.setType(OrderTreeNodeTypeDefault.Product);
	}

	@Test
	@Transactional
	public void testShouldCreateOrder() throws Exception {
		OrderViewDtoImpl dto = new OrderViewDtoImpl();
		dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
		dto.setSeller(new OrderAccountDtoImpl().setAny(true));
		dto.setDetails(new OrderDetailsDtoDefault().setAmount(2D).setDetails("Ma commande"));
		soaOrder.getOrCreate(dto, adapter, new OptionsList());
		daoOrder.flush();
		Assert.assertNotNull(dto.getId());
		Order order = daoOrder.get(dto.getId());
		Assert.assertNotNull(order.getUuid());
		Assert.assertNotNull(order.getBuyer().getId());
		Assert.assertTrue(order.getBuyer() instanceof OrderAccountSimple);
		Assert.assertNotNull(order.getSeller().getId());
		Assert.assertTrue(order.getSeller() instanceof OrderAccountAny);
	}

	@Test
	@Transactional
	public void testShouldCreateOrderTree() throws Exception {
		OrderViewDtoImpl dto = new OrderViewDtoImpl();
		dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
		dto.setSeller(new OrderAccountDtoImpl().setAny(true));
		//
		dto.setDetails(tree);
		soaOrder.getOrCreate(dto, adapterTree, new OptionsList());
		daoOrder.flush();
		Assert.assertNotNull(dto.getId());
		Order order = daoOrder.get(dto.getId());
		Assert.assertTrue(order.getDetails() instanceof OrderDetailsTree);
		OrderDetailsTree treeEntity = (OrderDetailsTree) order.getDetails();
		Assert.assertEquals(2, treeEntity.getNodes().size());
		Iterator<OrderDetailsTreeNode> it = treeEntity.getNodes().iterator();
		Assert.assertEquals(1, it.next().getChildren().size());
		Assert.assertEquals(0, it.next().getChildren().size());
	}

	@Test
	@Transactional
	public void testShouldUpdateOrderTree() throws Exception {
		OrderViewDtoImpl dto = new OrderViewDtoImpl();
		dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
		dto.setSeller(new OrderAccountDtoImpl().setAny(true));
		//
		dto.setDetails(tree);
		soaOrder.getOrCreate(dto, adapterTree, new OptionsList());
		daoOrder.flush();
		// UPDATE TREE
		Long idBefore = dto.getId();
		tree.getChildren().clear();
		tree.createChildren(true).setAmount(1d).setDetails("Article 1").setType(OrderTreeNodeTypeDefault.Product)
				.setReference(1l);
		tree.createChildren(true).setReference(2l).setAmount(1d).setDetails("Article 1")
				.setType(OrderTreeNodeTypeDefault.Product);
		dto.setDetails(tree);
		soaOrder.getOrCreate(dto, adapterTree, new OptionsList());
		daoOrder.flush();
		//
		Order order = daoOrder.get(dto.getId());
		Assert.assertEquals(idBefore, dto.getId());
		OrderDetailsTree treeEntity = (OrderDetailsTree) order.getDetails();
		Assert.assertEquals(2, treeEntity.getNodes().size());
		Iterator<OrderDetailsTreeNode> it = treeEntity.getNodes().iterator();
		Assert.assertEquals(0, it.next().getChildren().size());
		Assert.assertEquals(0, it.next().getChildren().size());
	}

	@Test
	@Transactional
	public void testShouldCreateTransaction() throws Exception {
		OrderViewDtoImpl dto = new OrderViewDtoImpl();
		dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
		dto.setSeller(new OrderAccountDtoImpl().setAny(true));
		dto.setDetails(new OrderDetailsDtoDefault().setAmount(2D).setDetails("Ma commande"));
		soaOrder.getOrCreate(dto, adapter, new OptionsList());
		daoOrder.flush();
		Assert.assertNotNull(dto.getId());
		Order order = daoOrder.get(dto.getId());
		daoOrder.refresh(order);
		Transaction tr = daoTransaction.findBySubject(order.getTransaction());
		Assert.assertNotNull(tr.getId());
	}

	@Test
	@Transactional
	public void testShouldCreateOnlyOneAny() throws Exception {
		for (int i = 0; i < 3; i++) {
			OrderViewDtoImpl dto = new OrderViewDtoImpl();
			dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
			dto.setSeller(new OrderAccountDtoImpl().setAny(true));
			dto.setDetails(new OrderDetailsDtoDefault().setAmount(2D).setDetails("Ma commande"));
			soaOrder.getOrCreate(dto, adapter, new OptionsList());
			daoOrder.flush();
		}
		Assert.assertEquals(1, daoAccount.find(QueryOrderAccountBuilder.getAny()).size());
	}

	@Test
	@Transactional
	public void testShouldReuseTransaction() throws Exception {
		Transaction tr = soaPaiment.getOrCreate(new TransactionSubjectSimpleDtoImpl().setPrice(2D).setValue(1l),
				adapterP);
		daoOrder.flush();
		Long idBeforeTrans = tr.getId();
		Long idBeforeSubject = tr.getSubject().getId();
		Assert.assertNotNull(tr.getId());
		Assert.assertNotNull(idBeforeSubject);
		//
		OrderViewDtoImpl dto = new OrderViewDtoImpl();
		dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
		dto.setSeller(new OrderAccountDtoImpl().setAny(true));
		dto.setDetails(new OrderDetailsDtoDefault().setAmount(2D).setDetails("Ma commande"));
		dto.setTransaction(new TransactionDtoImpl().setId(tr.getId()));
		soaOrder.getOrCreate(dto, adapter, new OptionsList());
		daoOrder.flush();
		//
		daoTransaction.refresh(tr);
		Assert.assertEquals(tr.getId(), idBeforeTrans);
		Assert.assertNotEquals(tr.getSubject().getId(), idBeforeSubject);
	}

	@Test
	@Transactional
	public void testShouldRemoveOrder() throws Exception {
		OrderViewDtoImpl dto = new OrderViewDtoImpl();
		dto.setBuyer(new OrderAccountDtoImpl().setValue(2l));
		dto.setSeller(new OrderAccountDtoImpl().setAny(true));
		dto.setDetails(new OrderDetailsDtoDefault().setAmount(2D).setDetails("Ma commande"));
		soaOrder.getOrCreate(dto, adapter, new OptionsList());
		daoOrder.flush();
		soaOrder.remove(dto);
		daoOrder.flush();
		Assert.assertEquals(0, daoOrder.findAll().size());
	}
}
