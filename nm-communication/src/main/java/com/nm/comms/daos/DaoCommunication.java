package com.nm.comms.daos;

import com.nm.comms.constants.CommunicationType;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.models.CommunicationMedium;
import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoCommunication extends IGenericDao<Communication, Long> {

	public CommunicationActor getOrCreateAny();

	public CommunicationMedium getOrCreateMedium(CommunicationType type);

	public CommunicationActorMail getOrCreateMail(String mail);
}
