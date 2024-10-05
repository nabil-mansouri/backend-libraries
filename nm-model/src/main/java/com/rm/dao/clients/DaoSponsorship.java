package com.rm.dao.clients;

import java.util.Date;

import com.rm.model.clients.Sponsorship;
import com.rm.utils.dao.GenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoSponsorship extends GenericDao<Sponsorship, Long> {

	void updateDate(Long id, Date date);
}
