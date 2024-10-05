package com.nm.tests.payments;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.paiments.constants.PaymentActionType;
import com.nm.paiments.constants.PaymentType;
import com.nm.paiments.constants.TransactionActionType;
import com.nm.paiments.constants.TransactionOptions;
import com.nm.paiments.contract.PaimentAdapterDefault;
import com.nm.paiments.daos.DaoTransaction;
import com.nm.paiments.daos.QueryTransactionBuilder;
import com.nm.paiments.dtos.PayerDto;
import com.nm.paiments.dtos.TransactionDto;
import com.nm.paiments.dtos.impl.PayerAnyDtoImpl;
import com.nm.paiments.dtos.impl.PaymentDtoImpl;
import com.nm.paiments.dtos.impl.TransactionDtoImpl;
import com.nm.paiments.dtos.impl.TransactionRequestImpl;
import com.nm.paiments.dtos.impl.TransactionSubjectSimpleDtoImpl;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionSubjectSame;
import com.nm.payment.operations.TransactionChain;
import com.nm.payment.soa.SoaPaiment;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPaiment.class)
public class TestTransactionCrud {

	//
	@Autowired
	private SoaPaiment soa;
	@Autowired
	private DaoTransaction daoTransaction;
	//
	private TransactionSubjectSimpleDtoImpl dto;

	//
	@Before
	public void setUp() throws Exception {
		dto = new TransactionSubjectSimpleDtoImpl();
		dto.setValue(1l);
		dto.setPrice(1d);
	}

	@Test()
	@Transactional
	public void testShouldCreateOnlyOneTransaction() throws Exception {
		Transaction transaction = soa.getOrCreate(dto, new PaimentAdapterDefault());
		Assert.assertNotNull(transaction.getId());
		daoTransaction.flush();
		//
		Transaction transaction2 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		Assert.assertEquals(transaction.getId(), transaction2.getId());
		daoTransaction.flush();
	}

	protected void push(Transaction transaction, PaymentType type) throws Exception {
		push(transaction, type, new PayerAnyDtoImpl(), false);
	}

	protected void push(Transaction transaction, PaymentType type, PayerDto payer, boolean savePayment)
			throws Exception {
		TransactionRequestImpl request = new TransactionRequestImpl();
		request.setAction(TransactionActionType.Push);
		TransactionDtoImpl tr = new TransactionDtoImpl();
		tr.setActionType(PaymentActionType.Credit);
		PaymentDtoImpl p = tr.create(new PaymentDtoImpl());
		p.setAmount(5d);
		p.setPayer(payer);
		p.setPaymentType(type);
		p.setSaveFavorite(savePayment);
		request.setTransaction(tr);
		TransactionChain.create(new PaimentAdapterDefault(), transaction.getSubject(), request);
	}

	@Test()
	@Transactional
	public void testShouldCreateInnerTransactionSimple() throws Exception {
		Transaction transaction = soa.getOrCreate(dto, new PaimentAdapterDefault());
		daoTransaction.flush();
		push(transaction, PaymentType.Cash);
		//
		daoTransaction.flush();
		daoTransaction.refresh(transaction);
		Assert.assertEquals(1, transaction.childrens().size());
	}

	@Test()
	@Transactional
	public void testShouldShouldSameSubject() throws Exception {
		Transaction tr1 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		daoTransaction.flush();
		Transaction tr2 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		daoTransaction.flush();
		Assert.assertEquals(tr1.getSubject().getId(), tr2.getSubject().getId());
	}

	@Test()
	@Transactional
	public void testShouldShouldSameSubjectInner() throws Exception {
		Transaction tr1 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		daoTransaction.flush();
		push(tr1, PaymentType.Cash);
		//
		dto = new TransactionSubjectSimpleDtoImpl();
		dto.setValue(3l);
		dto.setPrice(3d);
		Transaction tr2 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		daoTransaction.flush();
		push(tr2, PaymentType.Cash);
		daoTransaction.flush();
		Transaction child1 = (Transaction) tr1.childrens().iterator().next();
		Transaction child2 = (Transaction) tr2.childrens().iterator().next();
		Assert.assertTrue(child1.getSubject() instanceof TransactionSubjectSame);
		Assert.assertEquals(child1.getSubject().getId(), child2.getSubject().getId());
	}

	@Test()
	@Transactional
	public void testShouldShouldNotSameSubject() throws Exception {
		Transaction tr1 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		daoTransaction.flush();
		dto = new TransactionSubjectSimpleDtoImpl();
		dto.setValue(3l);
		dto.setPrice(3d);
		Transaction tr2 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		daoTransaction.flush();
		Assert.assertNotEquals(tr1.getSubject().getId(), tr2.getSubject().getId());
	}

	@Test()
	@Transactional
	public void testShouldShouldFetchDeepTransaction() throws Exception {
		for (int i = 0; i < 3; i++) {
			dto.setId(null);
			dto.setValue((long) i);
			dto.setPrice(10d);
			Transaction tr1 = soa.getOrCreate(dto, new PaimentAdapterDefault());
			push(tr1, PaymentType.Cash);
		}
		daoTransaction.flush();
		//
		Collection<TransactionDto> dtos = soa.fetch(QueryTransactionBuilder.get().withRoot(true),
				new PaimentAdapterDefault(), new OptionsList().withOption(TransactionOptions.values()));
		Assert.assertEquals(3, dtos.size());
		for (TransactionDto d : dtos) {
			TransactionDtoImpl di = (TransactionDtoImpl) d;
			Assert.assertEquals(5d, di.getDue(), 0d);
			Assert.assertEquals(1, d.childrens().size());
			TransactionDtoImpl dto = (TransactionDtoImpl) d.childrens().iterator().next();
			Assert.assertEquals(5d, dto.getPaiment().getTotal(), 0d);
			Assert.assertEquals(PaymentType.Cash, dto.getPaiment().getPaymentType());
			break;
		}
	}

}
