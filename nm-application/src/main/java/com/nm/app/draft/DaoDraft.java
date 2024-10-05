package com.nm.app.draft;

import java.util.List;

import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * Category dao
 * 
 * @author diallo
 * 
 */
public interface DaoDraft extends IGenericDao<Draft, Long> {

	public List<Draft> getDraftsByType(DraftType type);

	public Draft getLastModifiedDraft(DraftType type) throws NoDataFoundException;

	public Draft getLastCreatedDraft(DraftType type) throws NoDataFoundException;
}
