package com.nm.auths.converters;

import com.nm.auths.dtos.DtoGroup;
import com.nm.auths.models.UserGroup;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterGroupDefault extends DtoConverterDefault<DtoGroup, UserGroup> {
	@Override
	public DtoGroup toDto(DtoGroup dto, UserGroup entity, OptionsList options) throws DtoConvertException {
		dto.setCode(entity.getCode());
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

}
