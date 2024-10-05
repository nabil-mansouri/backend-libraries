package com.nm.dictionnary;

import java.util.Collection;

import com.nm.dictionnary.daos.QueryBuilderDictionnaryEntity;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryEntry;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryValue;
import com.nm.dictionnary.dtos.DtoDictionnaryEntity;
import com.nm.dictionnary.dtos.DtoDictionnaryEntityDefault;
import com.nm.dictionnary.dtos.DtoDictionnaryEntry;
import com.nm.dictionnary.dtos.DtoDictionnaryEntryDefault;
import com.nm.dictionnary.dtos.DtoDictionnaryValue;
import com.nm.dictionnary.dtos.DtoDictionnaryValueDefault;
import com.nm.dictionnary.models.DictionnaryEntity;
import com.nm.dictionnary.models.DictionnaryEntry;
import com.nm.dictionnary.models.DictionnaryException;
import com.nm.dictionnary.models.DictionnaryValue;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SoaDictionnaryImpl implements SoaDictionnary {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void delete(DtoDictionnaryEntry entry, OptionsList otpions) throws DictionnaryException {
		AbstractGenericDao.get(DictionnaryEntry.class).delete(entry.getId());
	}

	@Override
	public void delete(QueryBuilderDictionnaryEntity query, OptionsList otpions) throws DictionnaryException {
		IGenericDao<DictionnaryEntity, Long> dao = AbstractGenericDao.get(DictionnaryEntity.class);
		Collection<DictionnaryEntity> cms = dao.find(query);
		dao.deleteAll(cms);
	}

	public Collection<DtoDictionnaryEntry> fetch(QueryBuilderDictionnaryEntry query, OptionsList options) throws DictionnaryException {
		Collection<DictionnaryEntry> entries = AbstractGenericDao.get(DictionnaryEntry.class).find(query);
		try {
			Class<DtoDictionnaryEntry> dtoClass = options.dtoForModel(DictionnaryEntry.class, DtoDictionnaryEntryDefault.class);
			DtoConverter<DtoDictionnaryEntry, DictionnaryEntry> converter = registry.search(dtoClass, DictionnaryEntry.class);
			return converter.toDto(entries, options);
		} catch (Exception e) {
			throw new DictionnaryException(e);
		}
	}

	public Collection<DtoDictionnaryEntity> fetch(QueryBuilderDictionnaryEntity query, OptionsList options) throws DictionnaryException {
		Collection<DictionnaryEntity> entries = AbstractGenericDao.get(DictionnaryEntity.class).find(query);
		try {
			Class<DtoDictionnaryEntity> dtoClass = options.dtoForModel(DictionnaryEntity.class, DtoDictionnaryEntityDefault.class);
			DtoConverter<DtoDictionnaryEntity, DictionnaryEntity> converter = registry.search(dtoClass, DictionnaryEntity.class);
			return converter.toDto(entries, options);
		} catch (Exception e) {
			throw new DictionnaryException(e);
		}
	}

	public Collection<DtoDictionnaryValue> fetch(QueryBuilderDictionnaryValue query, OptionsList options) throws DictionnaryException {
		Collection<DictionnaryValue> entries = AbstractGenericDao.get(DictionnaryValue.class).find(query);
		try {
			Class<DtoDictionnaryValue> dtoClass = options.dtoForModel(DictionnaryValue.class, DtoDictionnaryValueDefault.class);
			DtoConverter<DtoDictionnaryValue, DictionnaryValue> converter = registry.search(dtoClass, DictionnaryValue.class);
			return converter.toDto(entries, options);
		} catch (Exception e) {
			throw new DictionnaryException(e);
		}
	}

	public DictionnaryEntity saveOrUpdate(DtoDictionnaryEntity entry, OptionsList options) throws DictionnaryException {
		try {
			DtoConverter<DtoDictionnaryEntity, DictionnaryEntity> converter = registry.search(entry, DictionnaryEntity.class);
			DictionnaryEntity entity = converter.toEntity(entry, options);
			IGenericDao<DictionnaryEntity, Long> dao = AbstractGenericDao.get(DictionnaryEntity.class);
			dao.saveOrUpdate(entity);
			converter.toDto(entry, entity, options);
			return entity;
		} catch (Exception e) {
			throw new DictionnaryException(e);
		}
	}

	public void saveOrUpdate(DtoDictionnaryEntry entry, OptionsList options) throws DictionnaryException {
		try {
			DtoConverter<DtoDictionnaryEntry, DictionnaryEntry> converter = registry.search(entry, DictionnaryEntry.class);
			DictionnaryEntry entity = converter.toEntity(entry, options);
			IGenericDao<DictionnaryEntry, Long> dao = AbstractGenericDao.get(DictionnaryEntry.class);
			dao.saveOrUpdate(entity);
			converter.toDto(entry, entity, options);
		} catch (Exception e) {
			throw new DictionnaryException(e);
		}
	}

}
