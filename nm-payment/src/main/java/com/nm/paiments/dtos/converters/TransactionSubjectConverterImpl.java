package com.nm.paiments.dtos.converters;

import com.nm.paiments.daos.DaoTransactionSubject;
import com.nm.paiments.dtos.impl.TransactionSubjectSimpleDtoImpl;
import com.nm.paiments.models.TransactionSubjectSimple;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class TransactionSubjectConverterImpl
		extends DtoConverterDefault<TransactionSubjectSimpleDtoImpl, TransactionSubjectSimple> {
	private DaoTransactionSubject daoSubject;

	public void setDaoSubject(DaoTransactionSubject daoSubject) {
		this.daoSubject = daoSubject;
	}

	public TransactionSubjectSimpleDtoImpl toDto(TransactionSubjectSimple entity, OptionsList options)
			throws DtoConvertException {
		return toDto(new TransactionSubjectSimpleDtoImpl(), entity, options);
	}

	public TransactionSubjectSimple toEntity(TransactionSubjectSimpleDtoImpl dto, OptionsList options)
			throws DtoConvertException {
		try {
			TransactionSubjectSimple entity = new TransactionSubjectSimple();
			if (dto.getId() != null) {
				entity = (TransactionSubjectSimple) daoSubject.get(dto.getId());
			}
			entity.setPrice(dto.getPrice());
			entity.setValue(dto.getValue());
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public TransactionSubjectSimpleDtoImpl toDto(TransactionSubjectSimpleDtoImpl dto, TransactionSubjectSimple entity,
			OptionsList options) throws DtoConvertException {
		TransactionSubjectSimple simple = (TransactionSubjectSimple) entity;
		dto.setId(entity.getId());
		dto.setValue(simple.getValue());
		dto.setPrice(simple.getPrice());
		return dto;
	}

}
