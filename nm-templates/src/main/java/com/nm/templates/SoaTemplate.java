package com.nm.templates;

import java.util.Collection;

import com.nm.templates.daos.TemplateQueryBuilder;
import com.nm.templates.dtos.TemplateDto;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaTemplate {

	public Collection<TemplateDto> fetchTemplates(TemplateQueryBuilder query, OptionsList options) throws TemplateException;

	public TemplateDto saveOrReplaceTemplate(TemplateDto templateDto, OptionsList options) throws TemplateException;

	public TemplateDto saveTemplate(TemplateDto templateDto, OptionsList options) throws TemplateException;

}
