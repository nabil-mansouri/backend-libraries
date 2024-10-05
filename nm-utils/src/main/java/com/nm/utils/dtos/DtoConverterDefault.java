package com.nm.utils.dtos;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.ListUtils;
import com.nm.utils.hibernate.NotFoundException;


/**
 * 
 * @author Nabil MANSOURI
 * 
 * @param <DTO>
 * @param <ENTITY>
 */
public abstract class DtoConverterDefault<DTO, ENTITY> implements DtoConverter<DTO, ENTITY> {
	private static DtoConverterRegistry registry;

	protected static DtoConverterRegistry registry() {
		if (registry == null) {
			registry = ApplicationUtils.getBean(DtoConverterRegistry.class);
		}
		return registry;
	}
	protected final <DTO1, ENTITY1> DtoConverter<DTO1, ENTITY1> search(Class<DTO1> dtoClazz, Class<ENTITY1> entityClazz)
			throws NotFoundException {
		 return registry().search(dtoClazz, entityClazz);
	}

	public Collection<Class<? extends DTO>> managed() {
		return ListUtils.all(getDtoClass());
	}

	public Collection<Class<? extends ENTITY>> managedEntity() {
		return ListUtils.all(getEntityClass());
	}

	@SuppressWarnings("unchecked")
	private Class<DTO> getDtoClass() {
		// Get the class name of this instance's type.
		ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
		// You may need this split or not, use logging to check
		String parameterClassName = pt.getActualTypeArguments()[0].toString().split("\\s")[1];
		// Instantiate the Parameter and initialize it.
		try {
			return (Class<DTO>) Class.forName(parameterClassName);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private Class<ENTITY> getEntityClass() {
		// Get the class name of this instance's type.
		ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
		// You may need this split or not, use logging to check
		String parameterClassName = pt.getActualTypeArguments()[1].toString().split("\\s")[1];
		// Instantiate the Parameter and initialize it.
		try {
			return (Class<ENTITY>) Class.forName(parameterClassName);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public ENTITY toEntity(DTO dto, OptionsList options) throws DtoConvertException {
		try {
			return toEntity(getEntityClass().newInstance(), dto, options);
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public ENTITY toEntity(ENTITY entity, DTO dto, OptionsList options) throws DtoConvertException {
		try {
			return toEntity(dto, options);
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DTO toDto(ENTITY entity, OptionsList options) throws DtoConvertException {
		try {
			return toDto(getDtoClass().newInstance(), entity, options);
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public Collection<DTO> toDto(Collection<ENTITY> entity, OptionsList options) throws DtoConvertException {
		Collection<DTO> list = Lists.newArrayList();
		for (ENTITY e : entity) {
			list.add(toDto(e, options));
		}
		return list;
	}

}
