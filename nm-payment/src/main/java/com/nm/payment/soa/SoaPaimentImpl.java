package com.nm.payment.soa;

import java.util.Collection;

import com.nm.paiments.PaimentException;
import com.nm.paiments.constants.TransactionOptions;
import com.nm.paiments.constants.TransactionStateType;
import com.nm.paiments.contract.PaimentAdapter;
import com.nm.paiments.daos.DaoPaymentMean;
import com.nm.paiments.daos.DaoTransaction;
import com.nm.paiments.daos.DaoTransactionSubject;
import com.nm.paiments.daos.QueryPaymentMeanBuilder;
import com.nm.paiments.daos.QueryTransactionBuilder;
import com.nm.paiments.dtos.PaymentDto;
import com.nm.paiments.dtos.PaymentMeanDto;
import com.nm.paiments.dtos.TransactionDto;
import com.nm.paiments.dtos.TransactionSubjectDto;
import com.nm.paiments.dtos.impl.TransactionRequestImpl;
import com.nm.paiments.models.PaymentMean;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionState;
import com.nm.paiments.models.TransactionSubject;
import com.nm.payment.operations.TransactionChain;
import com.nm.payment.operations.TransactionChainContext;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaPaimentImpl implements SoaPaiment {

	private DtoConverterRegistry registry;
	private DaoTransaction daoTransaction;
	private DaoTransactionSubject daoSubject;
	private DaoPaymentMean daoPayment;

	public void setDaoPayment(DaoPaymentMean daoPayment) {
		this.daoPayment = daoPayment;
	}

	public void setDaoSubject(DaoTransactionSubject daoSubject) {
		this.daoSubject = daoSubject;
	}

	public void setDaoTransaction(DaoTransaction daoTransaction) {
		this.daoTransaction = daoTransaction;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Transaction changeSubject(TransactionDto transaction, TransactionSubjectDto subject, PaimentAdapter adapter)
			throws PaimentException {
		try {
			// MUST BE AT FIRST
			Transaction tr = daoTransaction.get(transaction.getId());
			OptionsList options = new OptionsList();
			DtoConverter<TransactionSubjectDto, TransactionSubject> converter = registry.search(subject,
					adapter.subjectClass());
			TransactionSubject owner = converter.toEntity(subject, options);
			if (owner.getId() == null) {
				daoSubject.insert(owner);
			}
			subject.setIdSubject(owner.getId());
			tr.setSubject(owner);
			daoTransaction.saveOrUpdate(tr);
			daoTransaction.flush();
			return tr;
		} catch (Exception e) {
			throw new PaimentException(e);
		}
	}

	public Transaction getOrCreate(TransactionSubjectDto subject, PaimentAdapter adapter) throws PaimentException {
		try {
			OptionsList options = new OptionsList();
			DtoConverter<TransactionSubjectDto, TransactionSubject> converter = registry.search(subject,
					adapter.subjectClass());
			//
			TransactionSubject owner = converter.toEntity(subject, options);
			if (owner.getId() == null) {
				AbstractGenericDao.get(TransactionSubject.class).saveOrUpdate(owner);
				TransactionComposed trans = new TransactionComposed();
				trans.setRoot(true);
				trans.setSubject(owner);
				trans.setTransactionid(adapter.transactionId(owner));
				trans.add(new TransactionState(TransactionStateType.Begin));
				daoTransaction.saveOrUpdate(trans);
				subject.setIdSubject(owner.getId());
				return trans;
			} else {
				Transaction tr = daoTransaction.findBySubject(owner);
				return tr;
			}
		} catch (Exception e) {
			throw new PaimentException(e);
		}
	}

	public TransactionDto operation(TransactionSubjectDto owner, PaimentAdapter adapter, TransactionRequestImpl request)
			throws PaimentException {
		try {
			TransactionSubject o = AbstractGenericDao.get(TransactionSubject.class).get(owner.getIdSubject());
			TransactionChainContext context = TransactionChain.create(adapter, o, request);
			return context.getRequest().getTransaction();
		} catch (Exception e) {
			throw new PaimentException(e);
		}
	}

	public TransactionDto operation(TransactionDto subject, PaimentAdapter adapter, TransactionRequestImpl request)
			throws PaimentException {
		try {
			Transaction o = daoTransaction.get(subject.getId());
			TransactionChainContext context = TransactionChain.create(adapter, o, request);
			return context.getRequest().getTransaction();
		} catch (Exception e) {
			throw new PaimentException(e);
		}
	}

	public TransactionDto operation(TransactionDto transacDto, PaimentAdapter adapter, TransactionRequestImpl request,
			PaymentMeanDto favorite) throws PaimentException {
		try {
			Transaction o = daoTransaction.get(transacDto.getId());
			PaymentMean fav = daoPayment.get(favorite.getId());
			DtoConverter<PaymentMeanDto, PaymentMean> converter2 = registry.search(favorite, PaymentMean.class);
			favorite = converter2.toDto(fav, new OptionsList());
			request.getTransaction().getPaiment().setMean(favorite);
			TransactionChainContext context = TransactionChain.create(adapter, o, request);
			return context.getRequest().getTransaction();
		} catch (Exception e) {
			throw new PaimentException(e);
		}
	}

	public Collection<TransactionDto> fetch(QueryTransactionBuilder builder, PaimentAdapter adapter,
			OptionsList options) throws PaimentException {
		try {
			Collection<Transaction> transactions = daoTransaction.find(builder);
			DtoConverter<TransactionDto, Transaction> converter = registry.search(adapter.dtoClass(),
					Transaction.class);
			options.put(TransactionOptions.ADAPTER_KEY, adapter);
			return converter.toDto(transactions, options);
		} catch (Exception e) {
			throw new PaimentException(e);
		}
	}

	public Collection<PaymentDto> fetch(QueryPaymentMeanBuilder builder, PaimentAdapter adapter, OptionsList options)
			throws PaimentException {
		try {
			Collection<PaymentMean> transactions = daoPayment.find(builder);
			DtoConverter<PaymentDto, PaymentMean> converter = registry.search(adapter.dtoPaymentClass(),
					PaymentMean.class);
			options.put(TransactionOptions.ADAPTER_KEY, adapter);
			return converter.toDto(transactions, options);
		} catch (Exception e) {
			throw new PaimentException(e);
		}
	}
}
