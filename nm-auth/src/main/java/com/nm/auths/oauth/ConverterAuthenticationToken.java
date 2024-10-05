package com.nm.auths.oauth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.nm.auths.dtos.DtoAuthenticationOAuth;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterAuthenticationToken
		extends DtoConverterDefault<DtoAuthenticationOAuth, PreAuthenticatedAuthenticationToken> {
	@Override
	public Collection<Class<? extends PreAuthenticatedAuthenticationToken>> managedEntity() {
		return ListUtils.all(PreAuthenticatedAuthenticationToken.class, Authentication.class);
	}

	public DtoAuthenticationOAuth toDto(DtoAuthenticationOAuth dto, PreAuthenticatedAuthenticationToken entity,
			OptionsList options) throws DtoConvertException {
		throw new IllegalStateException("Should not transform to dto");
	}

	@Override
	public PreAuthenticatedAuthenticationToken toEntity(DtoAuthenticationOAuth dto, OptionsList options)
			throws DtoConvertException {
		try {
			// HYDRATE ACCESS TOKEN
			TokenProcessor tokenP = options.getOverrides(TokenProcessor.class);
			tokenP.hydrate(dto);
			return new PreAuthenticatedAuthenticationToken(dto, "");
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
