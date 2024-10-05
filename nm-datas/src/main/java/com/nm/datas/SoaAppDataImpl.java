package com.nm.datas;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nm.datas.daos.DaoAppData;
import com.nm.datas.daos.QueryBuilderAppData;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.datas.processors.AppDataProcessor;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class SoaAppDataImpl implements SoaAppData {
	//
	protected Log log = LogFactory.getLog(getClass());
	private DaoAppData appDataDao;
	private DtoConverterRegistry registry;

	public void setAppDataDao(DaoAppData appDataDao) {
		this.appDataDao = appDataDao;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Collection<AppDataDto> fetch(QueryBuilderAppData query, OptionsList options) throws AppDataException {
		try {
			Collection<AppData> datas = appDataDao.find(query);
			Class<AppDataDto> dtoClass = options.dtoForModel(AppData.class, AppDataDtoImpl.class);
			return registry.search(dtoClass, AppData.class).toDto(datas, options);
		} catch (Exception e) {
			throw new AppDataException(e);
		}
	}

	public AppDataDto fetch(Long id, OptionsList options) throws AppDataException {
		try {
			AppData data = appDataDao.get(id);
			Class<AppDataDto> dtoClass = options.dtoForModel(AppData.class, AppDataDtoImpl.class);
			return registry.search(dtoClass, AppData.class).toDto(data, options);
		} catch (Exception e) {
			throw new AppDataException(e);
		}
	}

	public boolean remove(AppDataDto dto) throws AppDataException {
		try {
			AppData data = appDataDao.get(dto.getId());
			appDataDao.delete(data);
			AppDataProcessor.get(data).removeFromDestination(data);
			return true;
		} catch (Exception e) {
			throw new AppDataException(e);
		}
	}

	public AppData save(AppDataDto dto, OptionsList options) throws AppDataException {
		try {
			AppData data = registry.search(dto, AppData.class).toEntity(dto, options);
			appDataDao.saveOrUpdate(data);
			dto.setId(data.getId());
			return data;
		} catch (Exception e) {
			throw new AppDataException(e);
		}
	}

	public AppData saveUnique(AppDataDto dto, OptionsList options) throws AppDataException {
		try {
			AppData data = registry.search(dto, AppData.class).toEntity(dto, options);
			try {
				data = appDataDao.findByHash(data.getHash());
				dto.setId(data.getId());
				return data;
			} catch (NoDataFoundException e) {
				appDataDao.saveOrUpdate(data);
				dto.setId(data.getId());
				return data;
			}
		} catch (Exception e) {
			throw new AppDataException(e);
		}
	}

}
