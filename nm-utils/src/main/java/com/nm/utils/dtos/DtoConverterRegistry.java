package com.nm.utils.dtos;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NotFoundException;


/**
 * 
 * @author nabilmansouri
 * 
 */
public class DtoConverterRegistry {

	private MultiMap convertersByDto = null;
	private MultiMap convertersByEntity = null;

	public MultiMap getConvertersByDto() {
		if (convertersByDto == null) {
			reload();
		}
		return convertersByDto;
	}

	public MultiMap getConvertersByEntity() {
		if (convertersByEntity == null) {
			reload();
		}
		return convertersByEntity;
	}

	public void reload() {
		convertersByDto = new MultiValueMap();
		convertersByEntity = new MultiValueMap();
		@SuppressWarnings("rawtypes")
		Collection<DtoConverter> converters = ApplicationUtils.getBeansCollection(DtoConverter.class);
		for (DtoConverter<?, ?> c : converters) {
			for (Class<?> i : c.managed()) {
				for (Class<?> j : c.managedEntity()) {
					this.convertersByDto.put(i, c);
					this.convertersByEntity.put(j, c);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <DTO, ENTITY> DtoConverter<DTO, ENTITY> search(Class<? extends DTO> dto, Class<? extends ENTITY> entity)
			throws NotFoundException {
		Collection<DtoConverter<DTO, ENTITY>> founded = new ArrayList<DtoConverter<DTO, ENTITY>>();
		if (this.getConvertersByDto().containsKey(dto)) {
			Collection<?> a = (Collection<?>) this.getConvertersByDto().get(dto);
			for (Object aa : a) {
				founded.add((DtoConverter<DTO, ENTITY>) aa);
			}
		}
		//
		if (this.getConvertersByEntity().containsKey(entity)) {
			Collection<?> a = (Collection<?>) this.getConvertersByEntity().get(entity);
			for (Object aa : a) {
				founded.add((DtoConverter<DTO, ENTITY>) aa);
			}
		}
		//
		if (founded.isEmpty()) {
			throw new NotFoundException("Could not found dtoconverter for class:" + entity + "->" + dto);
		} else {
			for (DtoConverter<DTO, ENTITY> c : founded) {
				if (c.managed().contains(dto) && c.managedEntity().contains(entity)) {
					return c;
				}
			}
			if (founded.size() > 1) {
				throw new NotFoundException(
						"Many converter founded but none of one is ok for class:" + entity + "->" + dto);
			} else {
				throw new NotFoundException("One converter founded but is not ok for class:" + entity + "->" + dto);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <DTO, ENTITY> DtoConverter<DTO, ENTITY> searchFirstByEntity(Class<? extends ENTITY> entity)
			throws NotFoundException {
		Collection<DtoConverter<DTO, ENTITY>> founded = new ArrayList<DtoConverter<DTO, ENTITY>>();
		if (this.getConvertersByEntity().containsKey(entity)) {
			Collection<?> a = (Collection<?>) this.getConvertersByEntity().get(entity);
			for (Object aa : a) {
				founded.add((DtoConverter<DTO, ENTITY>) aa);
			}
		}
		//
		if (founded.isEmpty()) {
			throw new NotFoundException("Could not found dtoconverter for class:" + entity);
		} else {
			return founded.iterator().next();
		}
	}

	@SuppressWarnings("unchecked")
	public <DTO, ENTITY> DtoConverter<DTO, ENTITY> searchFirstByDto(Class<? extends DTO> dto) throws NotFoundException {
		Collection<DtoConverter<DTO, ENTITY>> founded = new ArrayList<DtoConverter<DTO, ENTITY>>();
		if (this.getConvertersByDto().containsKey(dto)) {
			Collection<?> a = (Collection<?>) this.getConvertersByDto().get(dto);
			for (Object aa : a) {
				founded.add((DtoConverter<DTO, ENTITY>) aa);
			}
		}
		//
		if (founded.isEmpty()) {
			throw new NotFoundException("Could not found dtoconverter for class:" + dto);
		} else {
			return founded.iterator().next();
		}
	}

	@SuppressWarnings("unchecked")
	public <DTO, ENTITY> DtoConverter<DTO, ENTITY> search(DTO dto, Class<? extends ENTITY> entity)
			throws NotFoundException {
		return search((Class<DTO>) dto.getClass(), entity);
	}

	@SuppressWarnings("unchecked")
	public <DTO, ENTITY> DtoConverter<DTO, ENTITY> search(Class<? extends DTO> dto, ENTITY entity)
			throws NotFoundException {
		return (DtoConverter<DTO, ENTITY>) search(dto, entity.getClass());
	}

	@SuppressWarnings("unchecked")
	public <DTO, ENTITY> DtoConverter<DTO, ENTITY> search(DTO dto, ENTITY entity) throws NotFoundException {
		return (DtoConverter<DTO, ENTITY>) search((Class<DTO>) dto.getClass(), entity.getClass());
	}
}
