package com.rm.dao.clients;

import java.util.Date;

import com.rm.model.clients.ClientActions;
import com.rm.utils.dao.GenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoClientAction extends GenericDao<ClientActions, Long> {

	void updateDate(Long id, Date date);

}
