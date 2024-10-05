package com.nm.auths.simple;

import java.util.Collection;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nm.auths.constants.AuthenticationState;
import com.nm.auths.constants.AuthenticationType.AuthenticationTypeDefault;
import com.nm.auths.dtos.DtoAuthenticationDefault;
import com.nm.auths.models.Authentication;
import com.nm.auths.models.AuthenticationSimple;
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
public class ConverterAuthenticationSimple extends DtoConverterDefault<DtoAuthenticationDefault, AuthenticationSimple> {

	private PasswordEncoder passwordEncoder;

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Collection<Class<? extends AuthenticationSimple>> managedEntity() {
		return ListUtils.all(AuthenticationSimple.class, Authentication.class);
	}

	public DtoAuthenticationDefault toDto(DtoAuthenticationDefault dto, AuthenticationSimple entity,
			OptionsList options) throws DtoConvertException {
		dto.setId(entity.getId());
		dto.setUsername(entity.getUsername());
		return dto;
	}

	@Override
	public AuthenticationSimple toEntity(DtoAuthenticationDefault dto, OptionsList options) throws DtoConvertException {
		try {
			AuthenticationSimple entity = new AuthenticationSimple();
			if (dto.getId() == null) {
				entity.setUsername(dto.getUsername());
				entity.setType(AuthenticationTypeDefault.UsernamePwd);
			} else {
				entity = AbstractGenericDao.get(AuthenticationSimple.class).get(dto.getId());
				if (!passwordEncoder.matches(dto.getOldPassword(), entity.getPassword())) {
					throw new BadCredentialsException("Old password is not ok");
				}
			}
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
			entity.setState(AuthenticationState.Enabled);
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
