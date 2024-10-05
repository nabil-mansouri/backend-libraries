package com.nm.comms.extractors;

import java.util.HashMap;

import com.nm.comms.constants.MessageMetaExtractorKey;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@SuppressWarnings("serial")
public class MessageMetaList extends HashMap<MessageMetaExtractorKey, Object> {
	@SuppressWarnings("unchecked")
	public <T, T1 extends MessageMetaExtractorKey> T getBy(T1 key) {
		return (T) this.get(key);
	}
}
