package com.nm.comms.history;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.app.history.DtoHistorySubject;
import com.nm.comms.constants.MessageBoxType;
import com.nm.comms.constants.MessagePartType;
import com.nm.comms.dtos.MessageDto;
import com.nm.datas.dtos.AppDataDtoImpl;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DtoHistorySubjectComm implements DtoHistorySubject {
	private Long id;
	private MessageDto message;
	private MessageBoxType type;
	private Map<MessagePartType, AppDataDtoImpl> content = Maps.newHashMap();
	private Collection<AppDataDtoImpl> joined = Lists.newArrayList();

	public MessageBoxType getType() {
		return type;
	}

	public DtoHistorySubjectComm setType(MessageBoxType type) {
		this.type = type;
		return this;
	}

	public Map<MessagePartType, AppDataDtoImpl> getContent() {
		return content;
	}

	public void setContent(Map<MessagePartType, AppDataDtoImpl> content) {
		this.content = content;
	}

	public Collection<AppDataDtoImpl> getJoined() {
		return joined;
	}

	public void setJoined(Collection<AppDataDtoImpl> joined) {
		this.joined = joined;
	}

	public MessageDto getMessage() {
		return message;
	}

	public DtoHistorySubjectComm setMessage(MessageDto message) {
		this.message = message;return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
