package com.nm.tests.history;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.nm.app.history.HistorySubject;
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
public class ConverterHistorySubjectTest extends DtoConverterDefault<DtoHistoryTest, HistorySubject> {

	public DtoHistoryTest toDto(DtoHistoryTest dto, HistorySubject entity, OptionsList options)
			throws DtoConvertException {
		HistorySubjectTest s = (HistorySubjectTest) entity;
		dto.setName(s.getName());
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public Collection<Class<? extends HistorySubject>> managedEntity() {
		return ListUtils.all(HistorySubjectTest.class, HistorySubject.class);
	}

	public HistorySubject toEntity(DtoHistoryTest dto, OptionsList options) throws DtoConvertException {
		try {
			HistorySubjectTest entity = new HistorySubjectTest();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(HistorySubjectTest.class).get(dto.getId());
			}
			entity.setName(dto.getName());
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
