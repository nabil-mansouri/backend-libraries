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

import com.nm.paiments.TransactionNotFoundedException;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionSimple;
import com.nm.payment.finder.TransactionFinder;
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
public class TestTransactionFinder {

	@Autowired
	private TransactionFinder finder;
	//

	//
	@Before
	public void setUp() throws Exception {

	}

	@Test
	@Transactional
	public void testShouldFindByUUID() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setTransactionid("1"));
		transaction.getTransactions().add(new TransactionSimple().setTransactionid("2"));
		transaction.getTransactions().add(new TransactionSimple().setTransactionid("3"));
		GraphFinderPath<Transaction> total = finder.find(transaction, "1");
		Assert.assertTrue(total.founded());
	}

	@Test(expected = TransactionNotFoundedException.class)
	@Transactional
	public void testShouldNotFindByUUID() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setTransactionid("1"));
		transaction.getTransactions().add(new TransactionSimple().setTransactionid("2"));
		transaction.getTransactions().add(new TransactionSimple().setTransactionid("3"));
		finder.find(transaction, "4");
	}

	@Test
	@Transactional
	public void testShouldFIndByID() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setId(1l));
		transaction.getTransactions().add(new TransactionSimple().setId(2l));
		transaction.getTransactions().add(new TransactionSimple().setId(3l));
		GraphFinderPath<Transaction> total = finder.find(transaction, 1l);
		Assert.assertTrue(total.founded());
	}

	@Test(expected = TransactionNotFoundedException.class)
	@Transactional
	public void testShouldNotFIndByID() throws Exception {
		TransactionComposed transaction = new TransactionComposed();
		transaction.getTransactions().add(new TransactionSimple().setId(1l));
		transaction.getTransactions().add(new TransactionSimple().setId(2l));
		transaction.getTransactions().add(new TransactionSimple().setId(3l));
		finder.find(transaction, 4l);
	}

}
