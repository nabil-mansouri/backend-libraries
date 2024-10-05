package com.nm.templates.dtos;

import com.nm.templates.constants.TemplateNameEnum;
import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface TemplateDto extends Dto {
	public Long getId();

	public TemplateNameEnum getName();

	public String getCriteria();

}
