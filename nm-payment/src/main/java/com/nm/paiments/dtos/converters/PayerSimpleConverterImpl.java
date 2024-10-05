package com.nm.paiments.dtos.converters;

import com.nm.paiments.daos.DaoPayer;
import com.nm.paiments.dtos.impl.PayerSimpleDtoImpl;
import com.nm.paiments.models.Payer;
import com.nm.paiments.models.PayerSimple;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class PayerSimpleConverterImpl extends DtoConverterDefault<PayerSimpleDtoImpl, Payer> {
	private DaoPayer daoPayer;

	public void setDaoPayer(DaoPayer daoPayer) {
		this.daoPayer = daoPayer;
	}

	public PayerSimpleDtoImpl toDto(Payer entity, OptionsList options) throws DtoConvertException {
		return toDto(new PayerSimpleDtoImpl(), entity, options);
	}

	public PayerSimpleDtoImpl toDto(PayerSimpleDtoImpl dto, Payer entity, OptionsList options)
			throws DtoConvertException {
		dto.setId(entity.getId());
		if (entity instanceof PayerSimple) {
			dto.setValue(((PayerSimple) entity).getValue());
		}
		return dto;
	}

	public Payer toEntity(PayerSimpleDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			PayerSimple payer = new PayerSimple();
			if (dto.getId() != null) {
				payer = (PayerSimple) daoPayer.get(dto.getId());
			}
			payer.setValue(dto.getValue());
			daoPayer.saveOrUpdate(payer);
			//
			dto.setId(payer.getId());
			return payer;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
