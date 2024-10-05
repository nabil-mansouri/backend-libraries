package com.nm.auths.converters;

import com.nm.auths.dtos.DtoUserDefault;
import com.nm.auths.models.User;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterUserDefault extends DtoConverterDefault<DtoUserDefault, User> {
	@Override
	public DtoUserDefault toDto(DtoUserDefault dto, User entity, OptionsList options) throws DtoConvertException {
		dto.setUserId(entity.getId());
		return dto;
	}

}
