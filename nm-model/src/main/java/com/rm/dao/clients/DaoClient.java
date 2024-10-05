package com.rm.dao.clients;

import java.util.Date;

import com.rm.model.clients.Client;
import com.rm.utils.dao.GenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoClient extends GenericDao<Client, Long> {
	public void updateSubscribedDate(final Long id, final Date date);
}
