package com.nm.permissions.converters;

import com.nm.permissions.daos.DaoResource;
import com.nm.permissions.dtos.DtoResourceDefault;
import com.nm.permissions.models.Resource;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ResourceConverter extends DtoConverterDefault<DtoResourceDefault, Resource> {

	public DtoResourceDefault toDto(DtoResourceDefault dto, Resource entity, OptionsList options)
			throws DtoConvertException {
		dto.getAction().addAll(entity.getAvailable());
		dto.setType(entity.getType());
		dto.setId(entity.getUuid());
		return dto;
	}

	@Override
	public Resource toEntity(DtoResourceDefault dto, OptionsList options) throws DtoConvertException {
		try {
			DaoResource dao = ApplicationUtils.getBean(DaoResource.class);
			if (dto.getId() != null) {
				// DO NOT UPDATE
				return dao.get(dto.getId());
			} else {
				Resource resource = new Resource();
				resource.setType(dto.getType());
				resource.setAvailable(dto.getAction());
				dao.save(resource);
				return resource;
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
