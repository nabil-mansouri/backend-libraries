package com.nm.app.draft;


import java.util.Collection;

import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaDraft {

	public DraftDto getDraft(Long id) throws NoDataFoundException;

	public Collection<DraftDto> getDrafts(DraftType type);

	public DraftDto getLastModifiedDraft(DraftType type) throws NoDataFoundException;

	public void remove(Long idDraft);

	public DraftDto saveDraft(DraftType type, String payload);

	public DraftDto saveOrUpdate(DraftType type, String payload, Long id) throws NoDataFoundException;

	public DraftDto update(DraftType type, String payload, Long id) throws NoDataFoundException;
}
