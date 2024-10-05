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
import com.nm.payment.operations.impl.TransactionChainElectronicsImpl;
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
public class TestTransactionChainElectronics {

	//
	@Autowired
	private TransactionComputer computer;
	private TransactionChain chain = new TransactionChainElectronicsImpl();
	private TransactionDtoImpl tr = new TransactionDtoImpl();
	private PaymentDtoImpl p = new PaymentDtoImpl();

	@Before
	public void setup() {
		tr.setPaiment(p);
	}

	@Test()
	@Transactional
	public void testShouldPushElec() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.getRequest().setTransaction(tr);
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Rollback))));
		context.getRequest().setAction(TransactionActionType.Push);
		this.tr.setPaiment(p);
		this.p.setPaymentType(PaymentType.Cb);
		this.tr.setActionType(PaymentActionType.Credit);
		this.p.setAmount(2d);
		context.setRoot(new TransactionComposed());
		chain.onProcess(context);
		Assert.assertEquals(1, context.getRoot().childrens().size());
		Assert.assertEquals(0d, computer.compute(context.getRoot()), 0d);
		//
		context.getRequest().setAction(TransactionActionType.DoCommit);
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded((Transaction) context.getRoot().childrens().iterator().next()));
		chain.onProcess(context);
		Assert.assertEquals(2d, computer.compute(context.getRoot()), 0d);
		Transaction tr = (Transaction) context.getRoot().childrens().iterator().next();
		Assert.assertEquals(TransactionStateType.Commit, tr.getLastState().getType());
	}

	@Test()
	@Transactional
	public void testShouldRollbackElec() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.getRequest().setTransaction(tr);
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Rollback))));
		context.getRequest().setAction(TransactionActionType.Push);
		this.p.setPaymentType(PaymentType.Cb);
		this.tr.setActionType(PaymentActionType.Credit);
		this.p.setAmount(2d);
		context.setRoot(new TransactionComposed());
		chain.onProcess(context);
		context.getRequest().setAction(TransactionActionType.DoRollback);
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded((Transaction) context.getRoot().childrens().iterator().next()));
		chain.onProcess(context);
		Assert.assertEquals(1, context.getRoot().childrens().size());
		Assert.assertEquals(0d, computer.compute(context.getRoot()), 0d);
	}

}
