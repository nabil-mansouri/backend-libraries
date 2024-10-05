package com.nm.templates.dtos;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.datas.dtos.AppDataDto;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.constants.TemplateNameEnum;
import com.nm.templates.constants.TemplateType;

/**
 * 
 * @author nabilmansouri
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class TemplateDtoImpl implements TemplateDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String criteria;
	private AppDataDto content;
	private AppDataMediaType type;
	private TemplateNameEnum name;
	private TemplateType templateType;
	private Collection<TemplateArgsEnum> arguments = Sets.newHashSet();
	private Collection<TemplateDtoImpl> children = Lists.newArrayList();
	private Long id;

	public Collection<TemplateDtoImpl> getChildren() {
		return children;
	}

	public void setChildren(Collection<TemplateDtoImpl> children) {
		this.children = children;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppDataDto getContent() {
		return content;
	}

	public void setContent(AppDataDto content) {
		this.content = content;
	}

	public AppDataMediaType getType() {
		return type;
	}

	public void setType(AppDataMediaType type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TemplateNameEnum getName() {
		return name;
	}

	public void setName(TemplateNameEnum name) {
		this.name = name;
	}

	public Collection<TemplateArgsEnum> getArguments() {
		return arguments;
	}

	public void setArguments(Collection<TemplateArgsEnum> arguments) {
		this.arguments = arguments;
	}

}
