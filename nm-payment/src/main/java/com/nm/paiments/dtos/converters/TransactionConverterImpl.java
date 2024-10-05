package com.nm.paiments.dtos.converters;

import com.google.common.base.Strings;
import com.nm.paiments.computers.TransactionComputer;
import com.nm.paiments.computers.TransactionComputerResult;
import com.nm.paiments.constants.TransactionOptions;
import com.nm.paiments.contract.PaimentAdapter;
import com.nm.paiments.daos.DaoTransaction;
import com.nm.paiments.daos.DaoTransactionSubject;
import com.nm.paiments.dtos.PaymentDto;
import com.nm.paiments.dtos.impl.TransactionDtoImpl;
import com.nm.paiments.dtos.impl.TransactionStateDto;
import com.nm.paiments.models.Payment;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionComposed;
import com.nm.paiments.models.TransactionSimple;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class TransactionConverterImpl extends DtoConverterDefault<TransactionDtoImpl, Transaction> {
	private DaoTransaction daoTransaction;
	private TransactionComputer computer;
	private DaoTransactionSubject subjectDao;

	public void setComputer(TransactionComputer computer) {
		this.computer = computer;
	}

	public void setDaoTransaction(DaoTransaction daoTransaction) {
		this.daoTransaction = daoTransaction;
	}

	public void setSubjectDao(DaoTransactionSubject subjectDao) {
		this.subjectDao = subjectDao;
	}

	public TransactionDtoImpl toDto(TransactionDtoImpl dto, Transaction entity, OptionsList options)
			throws DtoConvertException {
		try {
			PaimentAdapter adapter = options.get(TransactionOptions.ADAPTER_KEY, PaimentAdapter.class);
			dto.setRoot(entity.isRoot());
			dto.setTransactionId(entity.getTransactionid());
			dto.setLastState(new TransactionStateDto().setState(entity.getLastState().getType())
					.setCreated(entity.getLastState().getCreated()));
			//
			if (options.contains(TransactionOptions.States)) {
				dto.getStates().add(new TransactionStateDto().setState(entity.getLastState().getType())
						.setCreated(entity.getLastState().getCreated()));
			}
			if (entity instanceof TransactionSimple) {
				TransactionSimple simple = (TransactionSimple) entity;
				dto.setActionType(simple.getAction());
				//
				DtoConverter<PaymentDto, Payment> converter = registry().search(adapter.dtoPaymentClass(),
						Payment.class);
				dto.setPaiment(converter.toDto(simple.getPayment(), options));
			} else if (entity instanceof TransactionComposed) {
				TransactionComposed composed = (TransactionComposed) entity;
				if (options.contains(TransactionOptions.Children)) {
					for (Transaction tr : composed.getTransactions()) {
						dto.getTransactions().add(toDto(tr, options));
					}
				}
				if (options.contains(TransactionOptions.Compute)) {
					TransactionComputerResult result = computer.compute(entity.getSubject(), entity, adapter);
					dto.setPaid(result.getPaid());
					dto.setDue(result.getDue());
				}
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public TransactionDtoImpl toDto(Transaction entity, OptionsList options) throws DtoConvertException {
		return toDto(new TransactionDtoImpl(), entity, options);

	}

	protected String buildId(TransactionDtoImpl transaction) {
		String uuidString = UUIDUtils.UUID();
		String id = uuidString + "-" + Strings.padStart(daoTransaction.count().toString(), 20, '0');
		// Set transaction in order to get it in output
		transaction.setTransactionId(id);
		return id;
	}

	public Transaction toEntity(TransactionDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			if (dto.getId() != null) {
				return daoTransaction.get(dto.getId());
			} else {
				TransactionSimple begined = new TransactionSimple();
				// MUST BE BEFORE (because flush subject)
				begined.setTransactionid(buildId(dto));
				//
				DtoConverter<PaymentDto, Payment> converter = registry().search(dto.getPaiment(), Payment.class);
				begined.setPayment(converter.toEntity(dto.getPaiment(), options));
				//
				begined.setSubject(subjectDao.createSame());
				begined.setAction(dto.getActionType());
				return begined;
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
