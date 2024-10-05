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
import com.nm.paiments.computers.TransactionComputerResult;
import com.nm.paiments.constants.PaymentActionType;
import com.nm.paiments.constants.TransactionStateType;
import com.nm.paiments.contract.PaimentAdapterDefault;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionSimple;
import com.nm.paiments.models.TransactionState;
import com.nm.paiments.models.TransactionSubjectSimple;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPaiment.class)
public class TestTransactionComputer {

	@Autowired
	private TransactionComputer transactionComputer;
	//

	//
	@Before
	public void setUp() throws Exception {

	}

	@Test
	@Transactional
	public void testComputeTransactionRecursivelyCredit() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		Double total = transactionComputer.compute(transaction);
		Assert.assertEquals(15d, total, 0d);
	}

	@Test
	@Transactional
	public void testComputeTransactionRecursivelyDebit() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Debit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Debit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Debit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		Double total = transactionComputer.compute(transaction);
		Assert.assertEquals(-15d, total, 0d);
	}

	@Test
	@Transactional
	public void testComputeTransactionRecursivelyDebitAndCredit() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Debit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		Double total = transactionComputer.compute(transaction);
		Assert.assertEquals(0d, total, 0d);
	}

	@Test
	@Transactional
	public void testComputeTransactionShouldIgnoreNotCommitted() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Begin)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Rollback)));
		Double total = transactionComputer.compute(transaction);
		Assert.assertEquals(5d, total, 0d);
	}

	@Test
	@Transactional
	public void testComputeTransactionShouldComputeResult() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		transaction.getTransactions().add(new TransactionSimple().setAction(PaymentActionType.Credit).total(5d)
				.add(new TransactionState(TransactionStateType.Commit)));
		TransactionSubjectSimple simple = new TransactionSubjectSimple().setValue(5l).setPrice(5d);
		TransactionComputerResult res = transactionComputer.compute(simple, transaction, new PaimentAdapterDefault());
		Assert.assertEquals(-10d, res.getDue(), 0d);
		Assert.assertEquals(15d, res.getPaid(), 0d);
	}
}
