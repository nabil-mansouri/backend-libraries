package com.nm.comms.extractors;

import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContent;
import com.nm.comms.models.MessageContentFile;
import com.nm.datas.AppDataException;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataOptions;
import com.nm.datas.models.AppData;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageExtractorFile extends MessageExtractor {
	private SoaAppData soaAppData;

	public void setSoaAppData(SoaAppData soaAppData) {
		this.soaAppData = soaAppData;
	}

	public boolean accept(MessageContent content) {
		return content instanceof MessageContentFile;
	}

	private byte[] getData(AppData data) throws AppDataException {
		OptionsList ops = new OptionsList().withOption(AppDataOptions.Content);
		return soaAppData.fetch(data.getId(), ops).getFile();
	}

	public void extract(MessageExtractPart part, Message communication, MessageContent content) throws Exception {
		MessageContentFile f = (MessageContentFile) content;
		part.create(getData(f.getData()));
	}

	public void extract(MessageExtractPart part, Message communication, MessageContent content,
			CommunicationActor actor) throws Exception {
		MessageContentFile f = (MessageContentFile) content;
		part.create(getData(f.getData()), actor);
	}
}
