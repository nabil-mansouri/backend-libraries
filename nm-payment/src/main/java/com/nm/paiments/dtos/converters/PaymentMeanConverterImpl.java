package com.nm.paiments.dtos.converters;

import com.nm.paiments.daos.DaoPaymentMean;
import com.nm.paiments.dtos.impl.PaymentDtoImpl;
import com.nm.paiments.models.PaymentMean;
import com.nm.paiments.models.PaymentMeanDefault;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class PaymentMeanConverterImpl extends DtoConverterDefault<PaymentDtoImpl, PaymentMean> {

	private DaoPaymentMean daoMean;

	public void setDaoMean(DaoPaymentMean daoMean) {
		this.daoMean = daoMean;
	}

	public PaymentDtoImpl toDto(PaymentDtoImpl dto, PaymentMean entity, OptionsList options)
			throws DtoConvertException {
		try {
			dto.setPaymentParams(entity.getPaymentParams());
			dto.setPaymentType(entity.getPaymentType());
			dto.setIdMean(entity.getId());
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public PaymentDtoImpl toDto(PaymentMean entity, OptionsList options) throws DtoConvertException {
		return toDto(new PaymentDtoImpl(), entity, options);
	}

	public PaymentMean toEntity(PaymentDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			//
			boolean isAny = true;
			PaymentMean mean = new PaymentMeanDefault();
			//
			if (dto.getIdMean() != null) {
				isAny = false;
				mean = daoMean.get(dto.getIdMean());
			}
			//
			if (dto.isSaveFavorite()) {
				isAny = false;
			}
			//
			if (!dto.getPaymentType().isCanAny()) {
				isAny = false;
			}
			//
			if (isAny) {
				mean = daoMean.getOrCreateAny(dto.getPaymentType());
			} else {
				mean.setPaymentParams(dto.getPaymentParams());
			}
			mean.setPaymentType(dto.getPaymentType());
			return mean;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
