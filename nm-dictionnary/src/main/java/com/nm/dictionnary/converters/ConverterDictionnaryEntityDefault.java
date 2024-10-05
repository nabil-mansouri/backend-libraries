package com.nm.dictionnary.converters;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;
import com.nm.dictionnary.dtos.DtoDictionnaryEntityDefault;
import com.nm.dictionnary.dtos.DtoDictionnaryValue;
import com.nm.dictionnary.dtos.DtoDictionnaryValueDefault;
import com.nm.dictionnary.models.DictionnaryEntity;
import com.nm.dictionnary.models.DictionnaryValue;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.node.ModelNode;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class ConverterDictionnaryEntityDefault
		extends DtoConverterDefault<DtoDictionnaryEntityDefault, DictionnaryEntity> {

	@Override
	public Collection<Class<? extends DictionnaryEntity>> managedEntity() {
		return ListUtils.all(DictionnaryEntity.class, DictionnaryEntity.class);
	}

	public DtoDictionnaryEntityDefault toDto(DtoDictionnaryEntityDefault dto, DictionnaryEntity entity,
			OptionsList options) throws DtoConvertException {
		try {
			dto.setDictionnaryEntityId(entity.getId());
			//
			//
			dto.getValues().clear();
			Class<DtoDictionnaryValue> dtoClass = options.dtoForModel(DictionnaryValue.class,
					DtoDictionnaryValueDefault.class);
			DtoConverter<DtoDictionnaryValue, DictionnaryValue> converter = registry().search(dtoClass,
					DictionnaryValue.class);
			//
			for (DictionnaryValue v : entity.getValues()) {
				dto.getValues().add(converter.toDto(v, options));
			}
			dto.getAbout().setUuid(entity.getAbout() != null ? entity.getAbout().getId() : null);
			//
			dto.setValues(converter.toDto(entity.getValues(), options));
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	@Override
	public DictionnaryEntity toEntity(DictionnaryEntity entry, DtoDictionnaryEntityDefault dto, OptionsList options)
			throws DtoConvertException {
		try {
			IGenericDao<DictionnaryEntity, Long> dao = AbstractGenericDao.get(DictionnaryEntity.class);
			IGenericDao<DictionnaryValue, Long> daoE = AbstractGenericDao.get(DictionnaryValue.class);
			IGenericDao<ModelNode, BigInteger> daoN = AbstractGenericDao.get(ModelNode.class, BigInteger.class);
			if (dto.getDictionnaryEntityId() != null) {
				entry = dao.get(dto.getDictionnaryEntityId());
			}
			entry.setAbout((dto.getAbout().getUuid() != null) ? daoN.load(dto.getAbout().getUuid()) : null);
			//
			entry.getValues().clear();
			// FORCE UNIQUE VALUES
			Set<Long> idValues = Sets.newHashSet();
			for (DtoDictionnaryValue v : dto.getValues()) {
				idValues.add(v.getIdValue());
			}
			for (Long v : idValues) {
				entry.getValues().add(daoE.load(v));
			}
			return entry;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
