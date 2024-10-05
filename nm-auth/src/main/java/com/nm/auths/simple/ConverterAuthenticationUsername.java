package com.nm.auths.simple;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.nm.auths.dtos.DtoAuthenticationDefault;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterAuthenticationUsername
		extends DtoConverterDefault<DtoAuthenticationDefault, UsernamePasswordAuthenticationToken> {
	@Override
	public Collection<Class<? extends UsernamePasswordAuthenticationToken>> managedEntity() {
		return ListUtils.all(UsernamePasswordAuthenticationToken.class, Authentication.class);
	}

	public DtoAuthenticationDefault toDto(DtoAuthenticationDefault dto, UsernamePasswordAuthenticationToken entity,
			OptionsList options) throws DtoConvertException {
		throw new IllegalStateException("Should not transform to dto");
	}

	@Override
	public UsernamePasswordAuthenticationToken toEntity(DtoAuthenticationDefault dto, OptionsList options)
			throws DtoConvertException {
		return new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
	}

}
