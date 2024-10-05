package com.nm.auths.oauth;

import java.util.Collection;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.nm.auths.constants.AuthenticationState;
import com.nm.auths.constants.AuthenticationType.AuthenticationTypeDefault;
import com.nm.auths.dtos.DtoAuthenticationOAuth;
import com.nm.auths.models.Authentication;
import com.nm.auths.models.AuthenticationOpenID;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterAuthenticationOAuth extends DtoConverterDefault<DtoAuthenticationOAuth, AuthenticationOpenID> {
	private ConverterAuthenticationToken converter;

	public void setConverter(ConverterAuthenticationToken converter) {
		this.converter = converter;
	}

	@Override
	public Collection<Class<? extends AuthenticationOpenID>> managedEntity() {
		return ListUtils.all(AuthenticationOpenID.class, Authentication.class);
	}

	public DtoAuthenticationOAuth toDto(DtoAuthenticationOAuth dto, AuthenticationOpenID entity, OptionsList options)
			throws DtoConvertException {
		dto.setId(entity.getId());
		dto.setOpenid(entity.getOpenid());
		dto.setEmail(entity.getEmail());
		dto.setProvider(entity.getProvider());
		return dto;
	}

	@Override
	public AuthenticationOpenID toEntity(DtoAuthenticationOAuth dto, OptionsList options) throws DtoConvertException {
		try {
			//
			if (dto.getId() == null) {
				// FIRST Authentication => Create token then save it
				PreAuthenticatedAuthenticationToken token = converter.toEntity(dto, options);
				dto = (DtoAuthenticationOAuth) token.getPrincipal();
				//
				AuthenticationOpenID entity = new AuthenticationOpenID();
				entity.setType(AuthenticationTypeDefault.OpenId);
				entity.setOpenid(dto.getOpenid());
				entity.setEmail(dto.getEmail());
				entity.setState(AuthenticationState.Enabled);
				entity.setProvider(dto.getProvider());
				return entity;
			} else {
				// CANNOT UPDATE
				return AbstractGenericDao.get(AuthenticationOpenID.class).get(dto.getId());
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
