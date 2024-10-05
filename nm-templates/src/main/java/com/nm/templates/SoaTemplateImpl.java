package com.nm.templates;

import java.util.Collection;

import com.nm.templates.daos.TemplateQueryBuilder;
import com.nm.templates.dtos.TemplateDto;
import com.nm.templates.dtos.TemplateDtoImpl;
import com.nm.templates.models.Template;
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
public class SoaTemplateImpl implements SoaTemplate {

	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Collection<TemplateDto> fetchTemplates(TemplateQueryBuilder query, OptionsList options)
			throws TemplateException {
		try {
			Class<TemplateDto> dtoClass = options.dtoForModel(Template.class, TemplateDtoImpl.class);
			Collection<Template> templates = AbstractGenericDao.get(Template.class).find(query);
			return registry.search(dtoClass, Template.class).toDto(templates, options);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	public TemplateDto saveOrReplaceTemplate(TemplateDto templateDto, OptionsList options) throws TemplateException {
		try {
			TemplateQueryBuilder query = TemplateQueryBuilder.get().withCriteria(templateDto.getCriteria())
					.withName(templateDto.getName());
			IGenericDao<Template, Long> dao = AbstractGenericDao.get(Template.class);
			Collection<Template> templates = dao.find(query);
			// REMOVE FROM PARENTS
			if (!templates.isEmpty()) {
				Collection<Template> parents = dao.find(TemplateQueryBuilder.get().withChildrenIn(templates));
				for (Template t : parents) {
					t.getChildren().removeAll(templates);
				}
			}
			//
			dao.deleteAll(templates);
			return saveTemplate(templateDto, options);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	public TemplateDto saveTemplate(TemplateDto templateDto, OptionsList options) throws TemplateException {
		try {
			DtoConverter<TemplateDto, Template> converter = registry.search(templateDto, Template.class);
			Template template = converter.toEntity(templateDto, options);
			AbstractGenericDao.get(Template.class).saveOrUpdate(template);
			return converter.toDto(template, options);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

}
