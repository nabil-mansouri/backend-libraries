package com.nm.dictionnary.converters;

import com.nm.dictionnary.dtos.DtoDictionnaryValueDefault;
import com.nm.dictionnary.models.DictionnaryValue;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class ConverterDictionnaryValueDefault
		extends DtoConverterDefault<DtoDictionnaryValueDefault, DictionnaryValue> {

	public DtoDictionnaryValueDefault toDto(DtoDictionnaryValueDefault dto, DictionnaryValue entity,
			OptionsList options) throws DtoConvertException {
		dto.setIdValue(entity.getId());
		dto.setState(entity.getState());
		dto.setValue(entity.getValue());
		dto.setValueDate(entity.getValueDate());
		dto.setValueDouble(entity.getValueDouble());
		dto.setValueInteger(entity.getValueInt());
		return dto;
	}

	public DictionnaryValue toEntity(DtoDictionnaryValueDefault dto, OptionsList options) throws DtoConvertException {
		try {
			DictionnaryValue value = new DictionnaryValue();
			hydrateEntity(value, dto);
			return value;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public static void hydrateEntity(DictionnaryValue value, DtoDictionnaryValueDefault dto)
			throws DtoConvertException {
		value.setState(dto.getState());
		value.setValue(null);
		value.setValueDate(null);
		value.setValueDouble(null);
		value.setValueInt(null);
		//
		if (dto.getValue() != null) {
			value.setValue(dto.getValue());
		} else if (dto.getValueDate() != null) {
			value.setValueDate(dto.getValueDate());
		} else if (dto.getValueDouble() != null) {
			value.setValueDouble(dto.getValueDouble());
		} else if (dto.getValueInteger() != null) {
			value.setValueInt(dto.getValueInteger());
		} else {
			throw new DtoConvertException("No value found in the dicvalue...");
		}
	}
}
