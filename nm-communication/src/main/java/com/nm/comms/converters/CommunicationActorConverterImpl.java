package com.nm.comms.converters;

import java.util.Collection;

import com.google.common.base.Strings;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorAnonymous;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CommunicationActorConverterImpl
		extends DtoConverterDefault<CommunicationActorDtoImpl, CommunicationActor> {

	public Collection<Class<? extends CommunicationActor>> managedEntity() {
		return ListUtils.all(CommunicationActorMail.class, CommunicationActorAnonymous.class, CommunicationActor.class);
	}

	public CommunicationActorDtoImpl toDto(CommunicationActor entity, OptionsList options) {
		CommunicationActorDtoImpl dto = new CommunicationActorDtoImpl();
		dto.setId(entity.getId());
		return dto;
	}

	public CommunicationActorDtoImpl toDto(CommunicationActorDtoImpl dto, CommunicationActor entity,
			OptionsList options) throws DtoConvertException {
		return toDto(entity, options);
	}

	public CommunicationActor toEntity(CommunicationActorDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			if (!Strings.isNullOrEmpty(dto.getMail())) {
				CommunicationActorMail any = (CommunicationActorMail) ApplicationUtils.getBean(DaoCommunication.class)
						.getOrCreateMail(dto.getMail());
				return any;
			}
			CommunicationActor any = (CommunicationActor) ApplicationUtils.getBean(DaoCommunication.class)
					.getOrCreateAny();
			return any;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
