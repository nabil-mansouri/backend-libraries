package com.nm.permissions.acl.converters;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;

import com.nm.permissions.acl.EnumObjectID;
import com.nm.permissions.acl.dtos.DtoAclImpl;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class AclObjectIdentityConverter extends DtoConverterDefault<DtoAclImpl, ObjectIdentity> {

	public ObjectIdentity toEntity(DtoAclImpl dto, OptionsList options) throws DtoConvertException {
		try {
			switch (dto.getIdentity()) {
			case Generic:
				return new ObjectIdentityImpl(dto.getString(), dto.getId());
			case JavaClass:
				return new ObjectIdentityImpl(dto.getClazz(), dto.getId());
			case Object:
				return new ObjectIdentityImpl(dto.getObject());
			}
			throw new IllegalArgumentException("Could not identitify type :" + dto.getIdentity());
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DtoAclImpl toDto(DtoAclImpl dto, ObjectIdentity entity, OptionsList options) throws DtoConvertException {
		try {
			dto.setIdentity(EnumObjectID.Generic);
			dto.setId(entity.getIdentifier());
			dto.setString(entity.getType());
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
