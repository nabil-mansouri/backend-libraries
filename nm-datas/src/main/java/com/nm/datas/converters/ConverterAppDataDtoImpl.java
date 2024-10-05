package com.nm.datas.converters;

import com.google.common.base.Strings;
import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.constants.AppDataOptions;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.datas.processors.AppDataProcessor;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterAppDataDtoImpl extends DtoConverterDefault<AppDataDtoImpl, AppData> {

	public AppDataDtoImpl toDto(AppDataDtoImpl dto, AppData entity, OptionsList options) throws DtoConvertException {
		try {
			AppDataProcessor processor = AppDataProcessor.get(entity);
			dto.setId(entity.getId());
			dto.setName(entity.getFullName());
			dto.setType(entity.getType());
			dto.setKind(entity.getKind());
			dto.setSize(entity.getSize());
			dto.setDestination(entity.getDestination());
			if (options.contains(AppDataOptions.Content)) {
				processor.pullFromDestination(entity, dto);
			}
			if (options.contains(AppDataOptions.URL)) {
				dto.setUrl(processor.toURL(entity));
				if (dto.getDestination().equals(AppDataDestinationDefault.URL)) {
					dto.setAbsURL(true);
				} else {
					dto.setAbsURL(false);
				}
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public AppData toEntity(AppDataDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			AppDataProcessor processor = AppDataProcessor.get(dto);
			AppData entity = processor.create(dto);
			entity.setDestination(dto.getDestination());
			if (Strings.isNullOrEmpty(dto.getName())) {
				dto.setName(String.format("%s-file", System.currentTimeMillis()));
			}
			entity.setFullName(dto.getName());
			entity.setKind(dto.getKind());
			entity.setSize(dto.getSize());
			entity.setType(dto.getType());
			processor.pushToDestination(entity, dto);
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
