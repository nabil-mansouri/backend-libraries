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

import com.nm.paiments.constants.PaymentActionType;
import com.nm.paiments.constants.PaymentType;
import com.nm.paiments.constants.TransactionActionType;
import com.nm.paiments.contract.PaimentAdapterDefault;
import com.nm.paiments.daos.DaoPayer;
import com.nm.paiments.daos.DaoTransaction;
import com.nm.paiments.dtos.PayerDto;
import com.nm.paiments.dtos.impl.PayerAnyDtoImpl;
import com.nm.paiments.dtos.impl.PayerSimpleDtoImpl;
import com.nm.paiments.dtos.impl.PaymentDtoImpl;
import com.nm.paiments.dtos.impl.TransactionDtoImpl;
import com.nm.paiments.dtos.impl.TransactionRequestImpl;
import com.nm.paiments.dtos.impl.TransactionSubjectSimpleDtoImpl;
import com.nm.paiments.models.Payer;
import com.nm.paiments.models.PaymentMean;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionSimple;
import com.nm.payment.operations.TransactionChain;
import com.nm.payment.soa.SoaPaiment;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPaiment.class)
public class TestPaimentCrud {

	//
	@Autowired
	private SoaPaiment soa;
	@Autowired
	private DaoTransaction daoTransaction;
	@Autowired
	private DaoPayer daoPayer;
	//
	private TransactionSubjectSimpleDtoImpl dto;

	//
	@Before
	public void setUp() throws Exception {
		dto = new TransactionSubjectSimpleDtoImpl();
		dto.setValue(1l);
		dto.setPrice(1d);
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
	public void testShouldShouldSamePayerAnyOk() throws Exception {
		TransactionComposed tr1 = (TransactionComposed) soa.getOrCreate(dto, new PaimentAdapterDefault());
		for (int i = 0; i < 3; i++) {
			push(tr1, PaymentType.Cash);
		}
		daoTransaction.flush();
		daoTransaction.refresh(tr1);
		TransactionSimple child1 = (TransactionSimple) tr1.getTransactions().get(0);
		TransactionSimple child2 = (TransactionSimple) tr1.getTransactions().get(1);
		Assert.assertEquals(child1.getPayment().getPayer().getId(), child2.getPayment().getPayer().getId());
	}

	@Test()
	@Transactional
	public void testShouldShouldSamePaymentAnyOk() throws Exception {
		TransactionComposed tr1 = (TransactionComposed) soa.getOrCreate(dto, new PaimentAdapterDefault());
		for (int i = 0; i < 3; i++) {
			push(tr1, PaymentType.Cash);
		}
		daoTransaction.flush();
		daoTransaction.refresh(tr1);
		TransactionSimple child1 = (TransactionSimple) tr1.getTransactions().get(0);
		TransactionSimple child2 = (TransactionSimple) tr1.getTransactions().get(1);
		Assert.assertEquals(child1.getPayment().getMean().getId(), child2.getPayment().getMean().getId());
	}

	@Test()
	@Transactional
	public void testShouldShouldManyPaymentMeanAnyOk() throws Exception {
		TransactionComposed tr1 = (TransactionComposed) soa.getOrCreate(dto, new PaimentAdapterDefault());
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				push(tr1, PaymentType.Check);
			} else {
				push(tr1, PaymentType.Cash);
			}
		}
		//
		daoTransaction.flush();
		daoTransaction.refresh(tr1);
		TransactionSimple child1 = (TransactionSimple) tr1.getTransactions().get(0);
		TransactionSimple child2 = (TransactionSimple) tr1.getTransactions().get(1);
		Assert.assertNotEquals(child1.getPayment().getMean().getId(), child2.getPayment().getMean().getId());
	}

	@Test()
	@Transactional
	public void testShouldCrudFavoritePaymentMean() throws Exception {
		PayerSimpleDtoImpl si = new PayerSimpleDtoImpl();
		for (int i = 0; i < 3; i++) {
			dto.setId(null);
			dto.setValue((long) i);
			dto.setPrice(10d);
			si.setValue(2l);
			Transaction tr1 = soa.getOrCreate(dto, new PaimentAdapterDefault());
			push(tr1, PaymentType.Cash, si, i == 0);
		}
		Assert.assertNotNull(si.getId());
		//
		daoPayer.flush();
		Payer payer = daoPayer.get(si.getId());
		daoPayer.refresh(payer);
		Assert.assertEquals(1, payer.getFavorites().size());
		// 3 simple
		Assert.assertEquals(3, payer.getTransactions().size());
		daoPayer.flush();
	}

	@Test()
	@Transactional
	public void testShouldUseFavoritePaymentMean() throws Exception {
		PayerSimpleDtoImpl si = new PayerSimpleDtoImpl();
		dto.setId(null);
		dto.setValue((long) 2);
		dto.setPrice(10d);
		si.setValue(2l);
		Transaction tr1 = soa.getOrCreate(dto, new PaimentAdapterDefault());
		push(tr1, PaymentType.Cb, si, true);
		Assert.assertNotNull(si.getId());
		//
		daoPayer.flush();
		Payer payer = daoPayer.get(si.getId());
		daoPayer.refresh(payer);
		Assert.assertEquals(1, payer.getFavorites().size());
		Assert.assertEquals(1, payer.getTransactions().size());
		//
		PaymentMean favorite = payer.getFavorites().iterator().next();
		TransactionRequestImpl request = new TransactionRequestImpl();
		TransactionDtoImpl tr = new TransactionDtoImpl();
		tr.setActionType(PaymentActionType.Credit);
		PaymentDtoImpl p = tr.create(new PaymentDtoImpl());
		p.setAmount(5d);
		p.setPayer(si);
		request.setTransaction(tr);
		request.setAction(TransactionActionType.Push);
		//
		soa.operation(new TransactionDtoImpl().setId(tr1.getId()), new PaimentAdapterDefault(), request,
				new PaymentDtoImpl(favorite.getId()));
		//
		daoPayer.flush();
		daoPayer.refresh(payer);
		Assert.assertEquals(1, payer.getFavorites().size());
		Assert.assertEquals(2, payer.getTransactions().size());
	}
}
