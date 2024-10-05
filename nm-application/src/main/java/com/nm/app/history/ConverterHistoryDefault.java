package com.nm.app.history;

import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class ConverterHistoryDefault extends DtoConverterDefault<DtoHistoryDefault, History> {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public DtoHistoryDefault toDto(History entity, OptionsList options) throws DtoConvertException {
		try {
			DtoHistoryDefault dto = new DtoHistoryDefault();
			dto.setId(entity.getId());
			dto.setWhen(entity.getHappendAt());
			dto.setCreated(entity.getCreatedAt());
			AdapterHistory adapter = options.get(OptionsHistory.ADAPTER_KEY, AdapterHistory.class);
			DtoHistoryActor dtoA = registry.search(adapter.historyActorClass(), entity.getActor())
					.toDto(entity.getActor(), options);
			dto.setActor(dtoA);
			DtoHistorySubject dtoS = registry.search(adapter.historySubjectClass(), entity.getSubject())
					.toDto(entity.getSubject(), options);
			dto.setSubject(dtoS);
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DtoHistoryDefault toDto(DtoHistoryDefault dto, History entity, OptionsList options)
			throws DtoConvertException {
		return toDto(entity, options);
	}

	public History toEntity(DtoHistoryDefault dto, OptionsList options) throws DtoConvertException {
		try {
			History entity = new History();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(History.class).get(dto.getId());
			}
			entity.setHappendAt(dto.getWhen());
			entity.setActor(registry.search(dto.getActor(), HistoryActor.class).toEntity(dto.getActor(), options));
			entity.setSubject(
					registry.search(dto.getSubject(), HistorySubject.class).toEntity(dto.getSubject(), options));
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}
}
