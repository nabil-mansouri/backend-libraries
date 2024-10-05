package com.nm.tests.payments;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.paiments.PaimentException;
import com.nm.paiments.computers.TransactionComputer;
import com.nm.paiments.constants.PaymentActionType;
import com.nm.paiments.constants.PaymentType;
import com.nm.paiments.constants.TransactionActionType;
import com.nm.paiments.constants.TransactionStateType;
import com.nm.paiments.dtos.impl.PaymentDtoImpl;
import com.nm.paiments.dtos.impl.TransactionDtoImpl;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionState;
import com.nm.payment.operations.TransactionChain;
import com.nm.payment.operations.TransactionChainContext;
import com.nm.payment.operations.impl.TransactionChainCheckImpl;
import com.nm.utils.graphs.finder.GraphFinderPath;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPaiment.class)
public class TestTransactionChainCheck {

	//
	@Autowired
	private TransactionComputer computer;
	private TransactionChain chain = new TransactionChainCheckImpl();
	private TransactionDtoImpl tr = new TransactionDtoImpl();
	private PaymentDtoImpl p = new PaymentDtoImpl();

	@Before
	public void setup() {
		tr.setPaiment(p);
	}

	@Test()
	@Transactional
	public void testShouldPushCheck() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Begin))));
		context.getRequest().setAction(TransactionActionType.Push);
		this.tr.setPaiment(p);
		this.p.setPaymentType(PaymentType.Check);
		this.tr.setActionType(PaymentActionType.Credit);
		this.p.setQuantity(3);
		this.p.setAmount(2d);
		context.getRequest().setTransaction(tr);
		context.setRoot(new TransactionComposed());
		chain.onProcess(context);
		Assert.assertEquals(1, context.getRoot().childrens().size());
		Assert.assertEquals(6d, computer.compute(context.getRoot()), 0d);
	}

	@Test()
	@Transactional
	public void testShouldRollbackCheck() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Begin))));
		context.getRequest().setAction(TransactionActionType.Push);
		this.p.setPaymentType(PaymentType.Check);
		this.tr.setActionType(PaymentActionType.Credit);
		this.p.setQuantity(1);
		this.p.setAmount(2d);
		context.getRequest().setTransaction(tr);
		context.setRoot(new TransactionComposed());
		chain.onProcess(context);
		context.getRequest().setAction(TransactionActionType.DoRollback);
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded((Transaction) context.getRoot().childrens().iterator().next()));
		chain.onProcess(context);
		Assert.assertEquals(1, context.getRoot().childrens().size());
		Assert.assertEquals(0d, computer.compute(context.getRoot()), 0d);
	}

	@Test(expected = PaimentException.class)
	@Transactional
	public void testShouldNotPushCheckWithCommit() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Begin))));
		context.getRequest().setAction(TransactionActionType.DoCommit);
		context.getRequest().setTransaction(tr);
		this.p.setPaymentType(PaymentType.Check);
		this.tr.setActionType(PaymentActionType.Credit);
		this.p.setQuantity(1);
		this.p.setAmount(2d);
		context.setRoot(new TransactionComposed());
		chain.onProcess(context);
	}
}
