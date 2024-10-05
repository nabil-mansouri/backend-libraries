package com.nm.paiments.dtos.converters;

import com.nm.paiments.daos.DaoPayer;
import com.nm.paiments.dtos.impl.PayerAnyDtoImpl;
import com.nm.paiments.models.Payer;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class PayerAnyConverterImpl extends DtoConverterDefault<PayerAnyDtoImpl, Payer> {
	private DaoPayer daoPayer;

	public void setDaoPayer(DaoPayer daoPayer) {
		this.daoPayer = daoPayer;
	}

	public PayerAnyDtoImpl toDto(Payer entity, OptionsList options) throws DtoConvertException {
		return toDto(new PayerAnyDtoImpl(), entity, options);
	}

	public PayerAnyDtoImpl toDto(PayerAnyDtoImpl dto, Payer entity, OptionsList options) throws DtoConvertException {
		dto.setId(entity.getId());
		return dto;
	}

	public Payer toEntity(PayerAnyDtoImpl dto, OptionsList options) throws DtoConvertException {
		Payer payer = daoPayer.getOrCreateAny();
		dto.setId(payer.getId());
		return payer;
	}

}
