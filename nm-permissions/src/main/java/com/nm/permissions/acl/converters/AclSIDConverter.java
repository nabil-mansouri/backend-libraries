package com.nm.permissions.acl.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.nm.permissions.acl.EnumSID;
import com.nm.permissions.acl.dtos.DtoAclImpl;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class AclSIDConverter extends DtoConverterDefault<DtoAclImpl, Sid> {

	public Sid toEntity(DtoAclImpl dto, OptionsList options) throws DtoConvertException {
		try {
			switch (dto.getSid()) {
			case CurrentUser:
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				return new PrincipalSid(auth);
			case GrantedAuthority:
				return new GrantedAuthoritySid(dto.getGrantedAuthority());
			case Username:
				return new PrincipalSid(dto.getUsername());

			}
			throw new IllegalArgumentException("Could not identitify type :" + dto.getSid());
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DtoAclImpl toDto(DtoAclImpl dto, Sid entity, OptionsList options) throws DtoConvertException {
		try {
			if (entity instanceof PrincipalSid) {
				PrincipalSid pSid = (PrincipalSid) entity;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String current = null;
				if (auth != null && auth.getPrincipal() instanceof UserDetails) {
					current = ((UserDetails) auth.getPrincipal()).getUsername();
				}
				if (StringUtils.equalsIgnoreCase(current, pSid.getPrincipal())) {
					dto.setSid(EnumSID.CurrentUser);
				} else {
					dto.setSid(EnumSID.Username);
					dto.setUsername(pSid.getPrincipal());
				}
			} else if (entity instanceof GrantedAuthoritySid) {
				GrantedAuthoritySid pSid = (GrantedAuthoritySid) entity;
				dto.setSid(EnumSID.GrantedAuthority);
				dto.setGrantedAuthority(pSid.getGrantedAuthority());
			} else {
				throw new IllegalArgumentException("Could not identitify type :" + entity.getClass());
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
