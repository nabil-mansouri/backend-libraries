package com.nm.app.history;

import java.util.Collection;

import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaHistory {
	public Collection<DtoHistory> fetch(QueryBuilderHistory query, OptionsList options, AdapterHistory adapter) throws HistoryException;

	public History saveOrUpdate(DtoHistory dto, OptionsList options) throws HistoryException;
}
