package com.nm.permissions.acl.converters;

import org.springframework.security.acls.model.Permission;

import com.nm.permissions.acl.EnumPermission;
import com.nm.permissions.acl.PermissionFactoryCustom;
import com.nm.permissions.acl.dtos.DtoAclImpl;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class AclPermissionConverter extends DtoConverterDefault<DtoAclImpl, Permission> {
	private PermissionFactoryCustom factory;

	public void setFactory(PermissionFactoryCustom factory) {
		this.factory = factory;
	}

	public Permission toEntity(DtoAclImpl dto, OptionsList options) throws DtoConvertException {
		try {
			switch (dto.getPermission()) {
			case ByMask:
				return factory.buildFromMask(dto.getPermissionMask());
			case ByName:
				return factory.buildFromName(dto.getPermissionName());
			case ByAction:
				return factory.buildFromAction(dto.getPermissionAction());
			}
			throw new IllegalArgumentException("Could not identitify type :" + dto.getPermission());
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DtoAclImpl toDto(DtoAclImpl dto, Permission entity, OptionsList options) throws DtoConvertException {
		try {
			dto.setPermissionMask(entity.getMask());
			dto.setPermissionAction(factory.getAction(entity));
			dto.setPermissionName(factory.getName(entity));
			if (dto.getPermissionAction() != null) {
				dto.setPermission(EnumPermission.ByAction);
			} else if (dto.getPermissionName() != null) {
				dto.setPermission(EnumPermission.ByName);
			} else {
				dto.setPermission(EnumPermission.ByMask);
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
