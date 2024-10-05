package com.nm.tests.history;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.nm.app.history.HistoryActor;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
@Component
public class ConverterHistoryActorTest extends DtoConverterDefault<DtoHistoryTest, HistoryActor> {

	public DtoHistoryTest toDto(DtoHistoryTest dto, HistoryActor entity, OptionsList options)
			throws DtoConvertException {
		HistoryActorTest a = (HistoryActorTest) entity;
		dto.setName(a.getName());
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public Collection<Class<? extends HistoryActor>> managedEntity() {
		return ListUtils.all(HistoryActorTest.class, HistoryActor.class);
	}

	public HistoryActor toEntity(DtoHistoryTest dto, OptionsList options) throws DtoConvertException {
		try {
			HistoryActorTest entity = new HistoryActorTest();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(HistoryActorTest.class).get(dto.getId());
			}
			entity.setName(dto.getName());
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
