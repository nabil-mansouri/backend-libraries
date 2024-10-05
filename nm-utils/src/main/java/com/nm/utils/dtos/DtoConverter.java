package com.nm.utils.dtos;

import java.util.Collection;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface DtoConverter<DTO, ENTITY> {
	public Collection<Class<? extends DTO>> managed();

	public Collection<Class<? extends ENTITY>> managedEntity();

	public DTO toDto(DTO dto, ENTITY entity, OptionsList options) throws DtoConvertException;

	public DTO toDto(ENTITY entity, OptionsList options) throws DtoConvertException;

	public Collection<DTO> toDto(Collection<ENTITY> entity, OptionsList options) throws DtoConvertException;

	public ENTITY toEntity(DTO dto, OptionsList options) throws DtoConvertException;

	public ENTITY toEntity(ENTITY entity, DTO dto, OptionsList options) throws DtoConvertException;

}
