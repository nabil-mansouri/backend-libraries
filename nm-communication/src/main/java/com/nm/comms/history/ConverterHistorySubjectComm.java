package com.nm.comms.history;

import java.util.Collection;

import com.nm.app.history.HistorySubject;
import com.nm.comms.constants.MessagePartType;
import com.nm.comms.models.Message;
import com.nm.datas.SoaAppData;
import com.nm.datas.dtos.AppDataDtoImpl;
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
public class ConverterHistorySubjectComm extends DtoConverterDefault<DtoHistorySubjectComm, HistorySubjectComm> {

	private SoaAppData soaData;

	public void setSoaData(SoaAppData soaData) {
		this.soaData = soaData;
	}

	public DtoHistorySubjectComm toDto(DtoHistorySubjectComm dto, HistorySubjectComm entity, OptionsList options)
			throws DtoConvertException {
		// TODO
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public Collection<Class<? extends HistorySubjectComm>> managedEntity() {
		return ListUtils.all(HistorySubjectComm.class, HistorySubject.class);
	}

	public HistorySubjectComm toEntity(DtoHistorySubjectComm dto, OptionsList options) throws DtoConvertException {
		try {
			HistorySubjectComm entity = new HistorySubjectComm();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(HistorySubjectComm.class).get(dto.getId());
			}
			entity.setMessage(AbstractGenericDao.get(Message.class).get(dto.getMessage().getId()));
			entity.setType(dto.getType());
			//
			entity.getContent().clear();
			for (MessagePartType type : dto.getContent().keySet()) {
				AppDataDtoImpl dDto = dto.getContent().get(type);
				dDto.setName(type.toString());
				dDto.setType("*/*");
				if (dDto.getFile() == null) {
					dDto.setFile(new byte[0]);
				}
				entity.getContent().add(soaData.saveUnique(dDto, new OptionsList()));
			}
			//
			entity.getJoined().clear();
			for (AppDataDtoImpl dDto : dto.getJoined()) {
				dDto.setType("*/*");
				dDto.setName("join");
				if (dDto.getFile() == null) {
					dDto.setFile(new byte[0]);
				}
				entity.getJoined().add(soaData.saveUnique(dDto, new OptionsList()));
			}
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}
}
