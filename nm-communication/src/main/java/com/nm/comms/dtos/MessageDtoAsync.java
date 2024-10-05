package com.nm.comms.dtos;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.app.triggers.DtoTrigger;
import com.nm.comms.constants.CommunicationType;

/**
 * 
 * @author nabilmansouri
 * 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class MessageDtoAsync implements MessageDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DtoTrigger trigger;
	private boolean hasTitle;
	private MessageContentDto title;
	private MessageContentDto content;
	private Collection<CommunicationType> type = Sets.newHashSet();
	private Collection<MessageContentDto> joinded = Lists.newArrayList();
	private Collection<CommunicationActorDto> receivers = Lists.newArrayList();
	private Long id;
	private Long channelId;

	public boolean isHasTitle() {
		return hasTitle;
	}

	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}

	public MessageContentDto getTitle() {
		return title;
	}

	public void setTitle(MessageContentDto title) {
		this.title = title;
	}

	public Long getChannelId() {
		return channelId;
	}

	public MessageDto setChannelId(Long id) {
		this.channelId = id;
		return this;
	}

	public Long getId() {
		return id;
	}

	public DtoTrigger getTrigger() {
		return trigger;
	}

	public MessageDtoAsync setTrigger(DtoTrigger trigger) {
		this.trigger = trigger;
		return this;
	}

	public MessageDto setId(Long id) {
		this.id = id;
		return this;
	}

	public Collection<CommunicationActorDto> getReceivers() {
		return receivers;
	}

	public MessageDtoAsync setReceivers(Collection<CommunicationActorDto> receivers) {
		this.receivers = receivers;
		return this;
	}

	public Collection<CommunicationType> getType() {
		return type;
	}

	public void setType(Collection<CommunicationType> type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MessageContentDto getContent() {
		return content;
	}

	public void setContent(MessageContentDto template) {
		this.content = template;
	}

	public Collection<MessageContentDto> getJoinded() {
		return joinded;
	}

	public void setJoinded(Collection<MessageContentDto> joinded) {
		this.joinded = joinded;
	}

}
