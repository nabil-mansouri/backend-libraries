package com.nm.templates.converters;

import java.util.Collection;

import com.nm.datas.daos.DaoAppData;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.constants.TemplateOptions;
import com.nm.templates.dtos.TemplateDto;
import com.nm.templates.dtos.TemplateDtoImpl;
import com.nm.templates.models.Template;
import com.nm.templates.models.TemplateArgument;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class TemplateContentConverterImpl extends DtoConverterDefault<TemplateDtoImpl, Template> {

	private DaoAppData dao;

	public void setDao(DaoAppData dao) {
		this.dao = dao;
	}

	public Collection<Class<? extends TemplateDtoImpl>> managed() {
		return ListUtils.all(TemplateDtoImpl.class, TemplateDto.class);
	}

	public Collection<Class<? extends Template>> managedEntity() {
		return ListUtils.all(Template.class);
	}

	public TemplateDtoImpl toDto(Template entity, OptionsList options) {
		TemplateDtoImpl dto = new TemplateDtoImpl();
		for (TemplateArgument e : entity.getArguments()) {
			dto.getArguments().add(e.getArgument());
		}
		dto.setName(entity.getTemplateName());
		dto.setId(entity.getId());
		dto.setCriteria(entity.getCriteria());
		dto.setTemplateType(entity.getProcessor());
		for (Template child : entity.getChildren()) {
			dto.getChildren().add(toDto(child, options));
		}
		return dto;
	}

	public TemplateDtoImpl toDto(TemplateDtoImpl dto, Template entity, OptionsList options) throws DtoConvertException {
		return toDto(entity, options);
	}

	public Template toEntity(TemplateDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			Template entity = new Template();
			IGenericDao<Template, Long> daoTemplate = AbstractGenericDao.get(Template.class);
			if (dto.getId() != null) {
				entity = daoTemplate.get(dto.getId());
			}
			entity.getArguments().clear();
			for (TemplateArgsEnum e : dto.getArguments()) {
				entity.getArguments().add(new TemplateArgument().setArgument(e));
			}
			entity.setCriteria(dto.getCriteria());
			entity.setProcessor(dto.getTemplateType());
			entity.setTemplateName(dto.getName());
			entity.setData(dao.load(dto.getContent().getId()));
			//
			entity.getChildren().clear();
			for (TemplateDtoImpl i : dto.getChildren()) {
				if (i.getId() != null && !options.contains(TemplateOptions.SaveTree)) {
					entity.getChildren().add(daoTemplate.load(i.getId()));
				} else {
					entity.getChildren().add(toEntity(i, options));
				}
			}
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
