package com.nm.cms.converters;

import java.math.BigInteger;
import java.util.Collection;

import com.nm.cms.dao.QueryBuilderCms;
import com.nm.cms.dtos.CmsDtoContents;
import com.nm.cms.dtos.CmsDtoContentsTable;
import com.nm.cms.dtos.CmsDtoImpl;
import com.nm.cms.models.Cms;
import com.nm.cms.models.CmsContents;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.node.DtoNode;
import com.nm.utils.node.ModelNode;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class CmsConverterImpl extends DtoConverterDefault<CmsDtoImpl, Cms> {

	public Collection<Class<? extends CmsDtoImpl>> managed() {
		return ListUtils.all(CmsDtoImpl.class);
	}

	public Collection<Class<? extends Cms>> managedEntity() {
		return ListUtils.all(Cms.class);
	}

	public CmsDtoImpl toDto(CmsDtoImpl dto, Cms entity, OptionsList options) throws DtoConvertException {
		dto.setId(entity.getId());
		//
		try {
			dto.setOwner(new DtoNode(entity.getOwner() != null ? entity.getOwner().getId() : null));
			dto.setSubject(new DtoNode(entity.getSubject() != null ? entity.getSubject().getId() : null));
			Class<CmsDtoContents> clazzContents = options.dtoForModel(entity.getData().getClass(), CmsDtoContentsTable.class);
			DtoConverter<CmsDtoContents, CmsContents> converter = registry().search(clazzContents, entity.getData().getClass());
			CmsDtoContents con = converter.toDto(entity.getData(), options);
			dto.setContents(con);
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public Cms toEntity(CmsDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			Cms any = new Cms();
			if (dto.getId() != null) {
				any = AbstractGenericDao.get(Cms.class).get(dto.getId());
			} else if (dto.getSubject() != null && dto.getSubject().getUuid() != null) {
				Collection<Cms> cms = AbstractGenericDao.get(Cms.class).find(QueryBuilderCms.get().withSubject(dto.getSubject().getUuid()));
				if (!cms.isEmpty()) {
					any = cms.iterator().next();
				}
			}
			//
			IGenericDao<ModelNode, BigInteger> dao = AbstractGenericDao.get(ModelNode.class, BigInteger.class);
			any.setOwner((dto.getOwner().getUuid() != null) ? dao.load(dto.getOwner().getUuid()) : null);
			any.setSubject((dto.getSubject().getUuid() != null) ? dao.load(dto.getSubject().getUuid()) : null);
			//
			CmsContents con = registry().search(dto.getContents(), CmsContents.class).toEntity(dto.getContents(), options);
			any.setData(con);
			return any;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}
}
