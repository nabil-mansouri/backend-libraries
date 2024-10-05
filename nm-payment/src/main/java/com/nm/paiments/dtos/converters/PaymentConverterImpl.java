package com.nm.paiments.dtos.converters;

import com.nm.paiments.dtos.PayerDto;
import com.nm.paiments.dtos.impl.PaymentDtoImpl;
import com.nm.paiments.models.Payer;
import com.nm.paiments.models.Payment;
import com.nm.paiments.models.PaymentMean;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class PaymentConverterImpl extends DtoConverterDefault<PaymentDtoImpl, Payment> {

	public PaymentDtoImpl toDto(PaymentDtoImpl dto, Payment entity, OptionsList options) throws DtoConvertException {
		try {
			DtoConverter<PaymentDtoImpl, PaymentMean> converter2 = registry().search(dto, PaymentMean.class);
			dto.setAmount(entity.getTotal());
			dto.setQuantity(entity.getQuantity());
			dto.setTotal(entity.getTotal());
			converter2.toDto(dto, entity.getMean(), options);
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public PaymentDtoImpl toDto(Payment entity, OptionsList options) throws DtoConvertException {
		return toDto(new PaymentDtoImpl(), entity, options);
	}

	public Payment toEntity(PaymentDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			DtoConverter<PayerDto, Payer> converter = registry().search(dto.getPayer(), Payer.class);
			DtoConverter<PaymentDtoImpl, PaymentMean> converter2 = registry().search(dto, PaymentMean.class);
			Payer payer = converter.toEntity(dto.getPayer(), options);
			Payment payment = new Payment();
			payment.setTotal(dto.getAmount());
			payment.setQuantity(1);
			payment.setMean(converter2.toEntity(dto, options));
			switch (dto.getPaymentType()) {
			case Check:
			case RestaurantTicket: {
				payment.setTotal(dto.getAmount() * dto.getQuantity());
				payment.setQuantity(dto.getQuantity());
				break;
			}
			case Cb:
			case Cash:
			case Paypal: {
				break;
			}
			}
			if (dto.isSaveFavorite() && payer.canFavorite(payment.getMean())) {
				payer.favorite(payment.getMean());
			}
			// MUST BE AFTER (because flush)
			payment.setPayer(payer);
			//
			return payment;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
