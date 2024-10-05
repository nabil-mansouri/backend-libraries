package com.nm.comms.history;

import java.util.Collection;

import com.nm.app.history.HistoryActor;
import com.nm.comms.AdapterCommunication;
import com.nm.comms.OptionsCommunication;
import com.nm.comms.daos.CommunicationActorQueryBuilder;
import com.nm.comms.models.CommunicationActor;
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
public class ConverterHistoryActorComm extends DtoConverterDefault<DtoHistoryActorComm, HistoryActorComm> {

	public DtoHistoryActorComm toDto(DtoHistoryActorComm dto, HistoryActorComm entity, OptionsList options)
			throws DtoConvertException {
		try {
			AdapterCommunication adapter = options.get(OptionsCommunication.ADAPTER_KEY, AdapterCommunication.class);
			dto.setActor(
					registry().search(adapter.actorDtoClass(), entity.getActor()).toDto(entity.getActor(), options));
			dto.setId(entity.getId());
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	@Override
	public Collection<Class<? extends HistoryActorComm>> managedEntity() {
		return ListUtils.all(HistoryActorComm.class, HistoryActor.class);
	}

	public HistoryActorComm toEntity(DtoHistoryActorComm dto, OptionsList options) throws DtoConvertException {
		try {
			HistoryActorComm entity = new HistoryActorComm();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(HistoryActorComm.class).get(dto.getId());
			} else {
				QueryBuilderHistoryActorComm query = QueryBuilderHistoryActorComm.getComm()
						.withActor(CommunicationActorQueryBuilder.get().withId(dto.getActor().getId()));
				Collection<HistoryActorComm> found = AbstractGenericDao.get(HistoryActorComm.class).find(query);
				if (found.isEmpty()) {
					entity.setActor(AbstractGenericDao.get(CommunicationActor.class).get(dto.getActor().getId()));
				} else {
					entity = found.iterator().next();
				}
			}
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}
}
