package com.nm.app.history;

import java.util.Collection;

import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaHistoryImpl implements SoaHistory {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Collection<DtoHistory> fetch(QueryBuilderHistory query, OptionsList options, AdapterHistory adapter) throws HistoryException {
		try {
			IGenericDao<History, Long> dao = AbstractGenericDao.get(History.class);
			Collection<History> entites = dao.find(query);
			options.put(OptionsHistory.ADAPTER_KEY, adapter);
			DtoConverter<DtoHistory, History> converter = registry.search(adapter.historyClass(), History.class);
			Collection<DtoHistory> history = converter.toDto(entites, options);
			return history;
		} catch (Exception e) {
			throw new HistoryException(e);
		}
	}

	public History saveOrUpdate(DtoHistory dto, OptionsList options) throws HistoryException {
		try {
			History history = registry.search(dto, History.class).toEntity(dto, options);
			IGenericDao<History, Long> dao = AbstractGenericDao.get(History.class);
			dao.saveOrUpdate(history);
			dao.flush();
			return history;
		} catch (Exception e) {
			throw new HistoryException(e);
		}
	}

}
