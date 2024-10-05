package com.nm.comms.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nm.datas.dtos.AppDataDto;
import com.nm.templates.dtos.TemplateDto;
import com.nm.templates.dtos.TemplateDtoImpl;

/**
 * 
 * @author nabilmansouri
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class MessageContentDtoImpl implements MessageContentDto {
	public static enum CommunicationContentType {
		File, Text, Template
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AppDataDto content;
	private String contentText;
	private TemplateDto template = new TemplateDtoImpl();
	private Long id;
	private CommunicationContentType type;

	public CommunicationContentType getType() {
		return type;
	}

	public MessageContentDtoImpl setType(CommunicationContentType type) {
		this.type = type;
		return this;
	}

	public Long getId() {
		return id;
	}

	public MessageContentDtoImpl setId(Long id) {
		this.id = id;
		return this;
	}

	public String getContentText() {
		return contentText;
	}

	public MessageContentDtoImpl setContentText(String contentText) {
		this.contentText = contentText;
		return this;
	}

	public AppDataDto getContent() {
		return content;
	}

	public MessageContentDtoImpl setContent(AppDataDto content) {
		this.content = content;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TemplateDto getTemplate() {
		return template;
	}

	public MessageContentDtoImpl setTemplate(TemplateDto template) {
		this.template = template;
		return this;
	}

}
