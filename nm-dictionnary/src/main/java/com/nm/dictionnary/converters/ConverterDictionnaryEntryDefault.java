package com.nm.dictionnary.converters;

import java.util.Map;

import com.google.common.collect.Maps;
import com.nm.dictionnary.constants.OptionsDictionnary;
import com.nm.dictionnary.daos.DaoDictionnary;
import com.nm.dictionnary.daos.QueryBuilderDictionnary;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryEntry;
import com.nm.dictionnary.dtos.DtoDictionnaryEntryDefault;
import com.nm.dictionnary.dtos.DtoDictionnaryValueDefault;
import com.nm.dictionnary.models.Dictionnary;
import com.nm.dictionnary.models.DictionnaryEntry;
import com.nm.dictionnary.models.DictionnaryValue;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class ConverterDictionnaryEntryDefault
		extends DtoConverterDefault<DtoDictionnaryEntryDefault, DictionnaryEntry> {
	private DaoDictionnary daoDictionnary;

	public void setDaoDictionnary(DaoDictionnary daoDictionnary) {
		this.daoDictionnary = daoDictionnary;
	}

	public DtoDictionnaryEntryDefault toDto(DtoDictionnaryEntryDefault dto, DictionnaryEntry entity,
			OptionsList options) throws DtoConvertException {
		try {
			dto.setDomain(entity.getDictionnary().getDomain());
			dto.setId(entity.getId());
			dto.setKey(entity.getKey());
			dto.setState(entity.getState());
			dto.setType(entity.getType());
			//
			dto.getValues().clear();
			DtoConverter<DtoDictionnaryValueDefault, DictionnaryValue> converter = registry()
					.search(DtoDictionnaryValueDefault.class, DictionnaryValue.class);
			//
			for (DictionnaryValue v : entity.getValues()) {
				dto.getValues().add(converter.toDto(v, options));
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DictionnaryEntry toEntity(DtoDictionnaryEntryDefault dto, OptionsList options) throws DtoConvertException {
		try {
			IGenericDao<DictionnaryEntry, Long> dao = AbstractGenericDao.get(DictionnaryEntry.class);
			DictionnaryEntry entry = new DictionnaryEntry();
			if (dto.getId() == null) {
				if (options.contains(OptionsDictionnary.DictionnarySafe)) {
					QueryBuilderDictionnaryEntry query = QueryBuilderDictionnaryEntry.get()
							.withDictionary(QueryBuilderDictionnary.get().withDomain(dto.getDomain()))
							.withKey(dto.getKey());
					entry = AbstractGenericDao.get(DictionnaryEntry.class).findFirstOrDefault(query, entry);
				}
				//
				Dictionnary dictionnary = daoDictionnary.getOrCreate(dto.getDomain());
				entry.setDictionnary(dictionnary);
				entry.setState(dto.getState());
				entry.setType(dto.getType());
			} else {
				entry = dao.get(dto.getId());
			}
			entry.setKey(dto.getKey());
			//
			Map<Long, DictionnaryValue> map = Maps.newHashMap();
			for (DictionnaryValue v : entry.getValues()) {
				map.put(v.getId(), v);
			}
			for (DtoDictionnaryValueDefault v : dto.getValues()) {
				if (v.getIdValue() != null) {
					DictionnaryValue value = map.get(v.getIdValue());
					switch (v.getOperation()) {
					case Add:
					case Update:
						ConverterDictionnaryValueDefault.hydrateEntity(value, v);
						break;
					case Remove:
						entry.remove(value);
						break;
					default:
					}
				} else {
					switch (v.getOperation()) {
					case Add:
					case Update:
						DictionnaryValue toTest = new DictionnaryValue();
						ConverterDictionnaryValueDefault.hydrateEntity(toTest, v);
						//
						DictionnaryValue value = entry.findByNormalized(toTest, new DictionnaryValue());
						ConverterDictionnaryValueDefault.hydrateEntity(value, v);
						entry.add(value);
						break;
					default:
					}
				}

			}
			return entry;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}
}
