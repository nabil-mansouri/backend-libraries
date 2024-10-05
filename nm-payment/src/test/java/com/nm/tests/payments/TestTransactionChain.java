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
import com.nm.paiments.contract.PaimentAdapterDefault;
import com.nm.paiments.dtos.impl.PaymentDtoImpl;
import com.nm.paiments.dtos.impl.TransactionDtoImpl;
import com.nm.paiments.dtos.impl.TransactionRequestImpl;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionSubjectSimple;
import com.nm.payment.operations.TransactionChain;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPaiment.class)
public class TestTransactionChain {

	//
	@Autowired
	private TransactionComputer computer;
	//
	private Transaction transaction;
	private TransactionRequestImpl request;
	private TransactionDtoImpl tr = new TransactionDtoImpl();
	private PaymentDtoImpl p = new PaymentDtoImpl();

	//
	@Before
	public void setUp() throws Exception {
		this.tr = new TransactionDtoImpl();
		this.tr.setPaiment(p);
		transaction = new TransactionComposed();
		//
		TransactionSubjectSimple su = new TransactionSubjectSimple();
		su.setValue(2l);
		su.setPrice(2d);
		transaction.setSubject(su);
		//
		request = new TransactionRequestImpl();
		request.setAction(TransactionActionType.Push);
		tr.setActionType(PaymentActionType.Credit);
		p.setAmount(2d);
		p.setQuantity(1);
		request.setTransaction(tr);
	}

	@Test()
	@Transactional
	public void testShouldPushCash() throws Exception {
		p.setPaymentType(PaymentType.Cash);
		TransactionChain.create(new PaimentAdapterDefault(), transaction, request);
		Assert.assertEquals(1, transaction.childrens().size());
		System.out.println(transaction);
		Assert.assertEquals(2d, computer.compute(transaction), 0d);
		//
		Transaction tr = (Transaction) transaction.childrens().iterator().next();
		this.tr.setTransactionId(tr.getTransactionid());
		request.setAction(TransactionActionType.DoRollback);
		TransactionChain.create(new PaimentAdapterDefault(), transaction, request);
		Assert.assertEquals(0d, computer.compute(transaction), 0d);
	}

	@Test()
	@Transactional
	public void testShouldPushCheck() throws Exception {
		p.setPaymentType(PaymentType.Check);
		TransactionChain.create(new PaimentAdapterDefault(), transaction, request);
		Assert.assertEquals(1, transaction.childrens().size());
		System.out.println(transaction);
		Assert.assertEquals(2d, computer.compute(transaction), 0d);
		//
		Transaction tr = (Transaction) transaction.childrens().iterator().next();
		this.tr.setTransactionId(tr.getTransactionid());
		request.setAction(TransactionActionType.DoRollback);
		TransactionChain.create(new PaimentAdapterDefault(), transaction, request);
		Assert.assertEquals(0d, computer.compute(transaction), 0d);
	}

	@Test()
	@Transactional
	public void testShouldPushElec() throws Exception {
		this.p.setPaymentType(PaymentType.Cb);
		TransactionChain.create(new PaimentAdapterDefault(), transaction, request);
		Assert.assertEquals(1, transaction.childrens().size());
		System.out.println(transaction);
		Assert.assertEquals(0d, computer.compute(transaction), 0d);
		Assert.assertEquals(TransactionStateType.Begin, transaction.getLastState().getType());
		//
		Transaction tr = (Transaction) transaction.childrens().iterator().next();
		this.tr.setTransactionId(tr.getTransactionid());
		request.setAction(TransactionActionType.DoCommit);
		TransactionChain.create(new PaimentAdapterDefault(), transaction, request);
		Assert.assertEquals(2d, computer.compute(transaction), 0d);
		// COmmitable
		Assert.assertEquals(TransactionStateType.Commit, tr.getLastState().getType());
		Assert.assertEquals(TransactionStateType.Commit, transaction.getLastState().getType());
	}
}
