package com.nm.comms.history;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.collect.Sets;
import com.nm.app.history.HistorySubject;
import com.nm.comms.constants.MessageBoxType;
import com.nm.comms.models.Message;
import com.nm.datas.models.AppData;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
@Entity
@Table(name = "nm_communication_history_subject")
public class HistorySubjectComm extends HistorySubject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(optional = false)
	private Message message;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MessageBoxType type;
	@ManyToMany()
	@JoinTable(name = "nm_communication_history_subject_content")
	private Collection<AppData> content = Sets.newHashSet();
	@ManyToMany()
	@JoinTable(name = "nm_communication_history_subject_joined")
	private Collection<AppData> joined = Sets.newHashSet();

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageBoxType getType() {
		return type;
	}

	public void setType(MessageBoxType type) {
		this.type = type;
	}

	public Collection<AppData> getContent() {
		return content;
	}

	public void setContent(Collection<AppData> content) {
		this.content = content;
	}

	public Collection<AppData> getJoined() {
		return joined;
	}

	public void setJoined(Collection<AppData> joined) {
		this.joined = joined;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
