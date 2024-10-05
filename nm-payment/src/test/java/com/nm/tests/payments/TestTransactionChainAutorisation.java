package com.nm.tests.payments;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.paiments.PaimentException;
import com.nm.paiments.constants.TransactionActionType;
import com.nm.paiments.constants.TransactionStateType;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionState;
import com.nm.payment.operations.TransactionChain;
import com.nm.payment.operations.TransactionChainContext;
import com.nm.payment.operations.impl.TransactionChainAutorisation;
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
public class TestTransactionChainAutorisation {

	//
	private TransactionChain chain = new TransactionChainAutorisation();

	@Before
	public void setUp() throws Exception {
	}

	@Test()
	@Transactional
	public void testShouldAutoriseAuthorizeBegin() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Rollback))));
		context.getRequest().setAction(TransactionActionType.Push);
		context.setRoot(new TransactionComposed());
		chain.onProcess(context);
		Assert.assertEquals(TransactionStateType.Begin, context.getRoot().getLastState().getType());
	}

	@Test(expected = PaimentException.class)
	@Transactional
	public void testShouldAutoriseNotPushInCommitedTransaction() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setRoot(new TransactionComposed().add(new TransactionState(TransactionStateType.Commit)));
		context.getRequest().setAction(TransactionActionType.Push);
		chain.onProcess(context);
	}

	@Test()
	@Transactional
	public void testShouldAutoriseCommit() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setRoot(new TransactionComposed());
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Begin))));
		context.getRequest().setAction(TransactionActionType.DoCommit);
		chain.onProcess(context);
	}

	@Test(expected = PaimentException.class)
	@Transactional
	public void testShouldNotAutoriseCommitWithoutUUID() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setRoot(new TransactionComposed());
		context.setFinderResult(new GraphFinderPath<Transaction>());
		context.getRequest().setAction(TransactionActionType.DoCommit);
		chain.onProcess(context);
	}

	@Test(expected = PaimentException.class)
	@Transactional
	public void testShouldNotAutoriseCommitWithRollback() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setRoot(new TransactionComposed());
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Rollback))));
		context.getRequest().setAction(TransactionActionType.DoCommit);
		chain.onProcess(context);
	}

	@Test()
	@Transactional
	public void testShouldAutoriseRollback() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setRoot(new TransactionComposed());
		context.setFinderResult(new GraphFinderPath<Transaction>()
				.setFounded(new TransactionComposed().add(new TransactionState(TransactionStateType.Begin))));
		context.getRequest().setAction(TransactionActionType.DoRollback);
		chain.onProcess(context);
	}

	@Test(expected = PaimentException.class)
	@Transactional
	public void testShouldNotAutoriseRollbackWithoutUUID() throws Exception {
		TransactionChainContext context = new TransactionChainContext();
		context.setRoot(new TransactionComposed());
		context.setFinderResult(new GraphFinderPath<Transaction>());
		context.getRequest().setAction(TransactionActionType.DoRollback);
		chain.onProcess(context);
	}

}
