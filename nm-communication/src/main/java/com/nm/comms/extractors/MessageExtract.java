package com.nm.comms.extractors;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.comms.constants.MessagePartType;
import com.nm.comms.models.CommunicationActor;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageExtract {
	private Map<MessagePartType, MessageExtractPart> contents = Maps.newHashMap();
	private List<MessageExtractPart> joinded = Lists.newArrayList();
	private Map<Long, MessageMetaList> metas = Maps.newConcurrentMap();

	public MessageMetaList getOrCreateMeta(CommunicationActor actor) {
		if (this.metas.containsKey(actor.getId())) {
			return this.metas.get(actor.getId());
		} else {
			MessageMetaList meta = new MessageMetaList();
			this.metas.put(actor.getId(), meta);
			return meta;
		}
	}

	public Map<Long, MessageMetaList> getMetas() {
		return metas;
	}

	public MessageMetaList getOrCreateMeta() {
		if (this.metas.containsKey(-1l)) {
			return this.metas.get(-1l);
		} else {
			MessageMetaList meta = new MessageMetaList();
			this.metas.put(-1l, meta);
			return meta;
		}
	}

	public boolean hasContent(MessagePartType type) {
		return this.contents.containsKey(type);
	}

	public MessageExtractPart getContent(MessagePartType type) {
		return this.contents.get(type);
	}

	public Map<MessagePartType, MessageExtractPart> getContents() {
		return contents;
	}

	public void setContents(Map<MessagePartType, MessageExtractPart> contents) {
		this.contents = contents;
	}

	public MessageExtract putContents(MessagePartType type, MessageExtractPart contents) {
		this.contents.put(type, contents);
		return this;
	}

	public MessageExtractPart createJoined() {
		MessageExtractPart part = new MessageExtractPart();
		this.joinded.add(part);
		return part;
	}

	public MessageExtractPart getOrCreatePart(MessagePartType type) {
		if (this.contents.containsKey(type)) {
			return this.contents.get(type);
		}
		MessageExtractPart part = new MessageExtractPart();
		this.contents.put(type, part);
		return part;
	}

	public List<MessageExtractPart> getJoinded(CommunicationActor actor) {
		List<MessageExtractPart> copy = Lists.newArrayList();
		for (MessageExtractPart n : joinded) {
			if (n.getBy(actor) != null) {
				copy.add(n);
			}
		}
		return copy;
	}

	public void setJoinded(List<MessageExtractPart> joinded) {
		this.joinded = joinded;
	}

}
