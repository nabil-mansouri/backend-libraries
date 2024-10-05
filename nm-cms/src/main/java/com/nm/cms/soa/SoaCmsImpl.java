package com.nm.cms.soa;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.cms.CmsException;
import com.nm.cms.constants.CmsOptions;
import com.nm.cms.dao.QueryBuilderCms;
import com.nm.cms.dao.QueryBuilderCmsContent;
import com.nm.cms.dtos.CmsDto;
import com.nm.cms.dtos.CmsDtoContents;
import com.nm.cms.dtos.CmsDtoContentsTable;
import com.nm.cms.dtos.CmsDtoImpl;
import com.nm.cms.models.Cms;
import com.nm.cms.models.CmsContents;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class SoaCmsImpl implements SoaCms {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	@Override
	public CmsDto fetch(Number id, OptionsList options) throws CmsException {
		try {
			Cms cms = AbstractGenericDao.get(Cms.class).get(id.longValue());
			Class<CmsDto> cmsClazz = options.dtoForModel(Cms.class, CmsDtoImpl.class);
			DtoConverter<CmsDto, Cms> converter = registry.search(cmsClazz, Cms.class);
			return converter.toDto(cms, options);
		} catch (Exception e) {
			throw new CmsException(e);
		}
	}

	public CmsDto create(OptionsList options) throws CmsException {
		try {
			Cms cms = new Cms();
			Class<CmsDto> cmsClazz = options.dtoForModel(Cms.class, CmsDtoImpl.class);
			DtoConverter<CmsDto, Cms> converter = registry.search(cmsClazz, Cms.class);
			return converter.toDto(cms, options);
		} catch (Exception e) {
			throw new CmsException(e);
		}
	}

	public Cms saveOrUpdate(CmsDto dto, OptionsList options) throws CmsException {
		try {
			options.withOption(CmsOptions.CmsSubjectEntity);
			DtoConverter<CmsDto, Cms> converter = registry.search(dto, Cms.class);
			Cms entity = converter.toEntity(dto, options);
			AbstractGenericDao.get(Cms.class).saveOrUpdate(entity);
			dto.setId(entity.getId());
			dto.getContents().setContentId(entity.getData().getId());
			return entity;
		} catch (Exception e) {
			throw new CmsException(e);
		}
	}

	public CmsContents saveOrUpdate(CmsDtoContents dto, OptionsList options) throws CmsException {
		try {
			DtoConverter<CmsDtoContents, CmsContents> converter = registry.search(dto.getClass(), CmsContents.class);
			CmsContents entity = converter.toEntity(dto, options);
			AbstractGenericDao.get(CmsContents.class).saveOrUpdate(entity);
			dto.setContentId(entity.getId());
			return entity;
		} catch (Exception e) {
			throw new CmsException(e);
		}
	}

	public Collection<CmsDtoContents> fetch(QueryBuilderCmsContent query, OptionsList options) throws CmsException {
		try {
			Collection<CmsContents> cms = AbstractGenericDao.get(CmsContents.class).find(query);
			if (cms.isEmpty()) {
				return Lists.newArrayList();
			} else {
				Class<CmsDtoContents> cmsClazz = options.dtoForModel(CmsContents.class, CmsDtoContentsTable.class);
				DtoConverter<CmsDtoContents, CmsContents> converter = registry.search(cmsClazz, CmsContents.class);
				return converter.toDto(cms, options);
			}
		} catch (Exception e) {
			throw new CmsException(e);
		}
	}

	public Collection<CmsDto> fetch(QueryBuilderCms query, OptionsList options) throws CmsException {
		try {
			Collection<Cms> cms = AbstractGenericDao.get(Cms.class).find(query);
			if (cms.isEmpty()) {
				return Lists.newArrayList();
			} else {
				Class<CmsDto> cmsClazz = options.dtoForModel(cms.iterator().next().getClass(), CmsDtoImpl.class);
				DtoConverter<CmsDto, Cms> converter = registry.search(cmsClazz, Cms.class);
				return converter.toDto(cms, options);
			}
		} catch (Exception e) {
			throw new CmsException(e);
		}
	}

	@Override
	public void remove(QueryBuilderCms query, OptionsList options) {
		IGenericDao<Cms, Long> dao = AbstractGenericDao.get(Cms.class);
		Collection<Cms> cms = dao.find(query);
		dao.deleteAll(cms);
	}
}
