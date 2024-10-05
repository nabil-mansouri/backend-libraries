package com.nm.cms.converters;

import java.util.Collection;

import com.nm.cms.constants.CmsPartType.CmsPartTypeDefault;
import com.nm.cms.dtos.CmsDtoContentsPrimitive;
import com.nm.cms.models.CmsContents;
import com.nm.cms.models.CmsContentsPrimitiveDate;
import com.nm.cms.models.CmsContentsPrimitiveDouble;
import com.nm.cms.models.CmsContentsPrimitiveInt;
import com.nm.cms.models.CmsContentsPrimitiveString;
import com.nm.utils.ListUtils;
import com.nm.utils.ReflectionUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CmsContentsPrimitiveConverter extends DtoConverterDefault<CmsDtoContentsPrimitive, CmsContents> {
	@Override
	public Collection<Class<? extends CmsContents>> managedEntity() {
		return ListUtils.all(CmsContents.class, CmsContentsPrimitiveDouble.class, //
				CmsContentsPrimitiveInt.class, //
				CmsContentsPrimitiveString.class, //
				CmsContentsPrimitiveDate.class);
	}

	public CmsDtoContentsPrimitive toDto(final CmsDtoContentsPrimitive dto, CmsContents entity, OptionsList options)
			throws DtoConvertException {
		try {
			if (entity instanceof CmsContentsPrimitiveDate) {
				dto.setDataDate(((CmsContentsPrimitiveDate) entity).getData());
			} else if (entity instanceof CmsContentsPrimitiveDouble) {
				dto.setDataDouble(((CmsContentsPrimitiveDouble) entity).getData());
			} else if (entity instanceof CmsContentsPrimitiveInt) {
				dto.setDataInt(((CmsContentsPrimitiveInt) entity).getData());
			} else if (entity instanceof CmsContentsPrimitiveString) {
				dto.setDataString(((CmsContentsPrimitiveString) entity).getData());
			} else {
				throw new IllegalArgumentException("Undefined primitive content:" + entity);
			}
			dto.setId(entity.getId());
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
		return dto;
	}

	public CmsContents toEntity(CmsDtoContentsPrimitive dto, OptionsList options) throws DtoConvertException {
		try {
			if (dto.getDataDate() != null) {
				CmsContentsPrimitiveDate date = getOrCreate(dto, CmsContentsPrimitiveDate.class);
				date.setData(dto.getDataDate());
				return date;
			} else if (dto.getDataDouble() != null) {
				CmsContentsPrimitiveDouble date = getOrCreate(dto, CmsContentsPrimitiveDouble.class);
				date.setData(dto.getDataDouble());
				return date;
			} else if (dto.getDataInt() != null) {
				CmsContentsPrimitiveInt date = getOrCreate(dto, CmsContentsPrimitiveInt.class);
				date.setData(dto.getDataInt());
				return date;
			} else if (dto.getDataString() != null) {
				CmsContentsPrimitiveString date = getOrCreate(dto, CmsContentsPrimitiveString.class);
				date.setData(dto.getDataString());
				return date;
			} else if (dto.isOptionnal()) {
				CmsContentsPrimitiveString date = getOrCreate(dto, CmsContentsPrimitiveString.class);
				date.setData("");
				return date;
			} else {
				throw new DtoConvertException("Could not determine type to use");
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends CmsContents> T getOrCreate(CmsDtoContentsPrimitive dto, Class<T> clazz) throws Exception {
		CmsContents contents = null;
		if (dto.getId() == null) {
			contents = ReflectionUtils.build(clazz);
		} else {
			try {
				contents = AbstractGenericDao.get(clazz).get(dto.getId());
			} catch (NoDataFoundException e) {
				// TYPE HAS CHANGED
				AbstractGenericDao.get(CmsContents.class).delete(dto.getId());
				dto.setId(null);
				contents = ReflectionUtils.build(clazz);
			}
		}
		contents.setType(CmsPartTypeDefault.Main);
		return (T) contents;
	}

}
