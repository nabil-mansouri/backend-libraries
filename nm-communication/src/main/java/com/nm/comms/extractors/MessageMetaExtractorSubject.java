package com.nm.comms.extractors;

import com.nm.comms.constants.MessageMetaExtractorKey.MessageMetaExtractorKeyDefault;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationSubjectSimple;
import com.nm.comms.models.Message;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageMetaExtractorSubject implements MessageMetaExtractor {

	public void extract(MessageExtract extr, Message mess, CommunicationActor actor) throws Exception {
		if (mess.getCommunication().getAbout() instanceof CommunicationSubjectSimple) {
			CommunicationSubjectSimple s = (CommunicationSubjectSimple) mess.getCommunication().getAbout();
			extr.getOrCreateMeta(actor).put(MessageMetaExtractorKeyDefault.Subject, s.getContent());
			//
			MessageExtract sub = new MessageExtract();
			MessageExtractor founded = MessageExtractor.find(s.getContent());
			founded.extract(sub.getOrCreatePart(MessagePartTypeDefault.Title), mess, s.getContent());
			extr.getOrCreateMeta(actor).put(MessageMetaExtractorKeyDefault.SubjectNode,
					sub.getContent(MessagePartTypeDefault.Title).get());
		}
	}

	public void extract(MessageExtract extr, Message mess) throws Exception {
		if (mess.getCommunication().getAbout() instanceof CommunicationSubjectSimple) {
			CommunicationSubjectSimple s = (CommunicationSubjectSimple) mess.getCommunication().getAbout();
			extr.getOrCreateMeta().put(MessageMetaExtractorKeyDefault.Subject, s.getContent());
			//
			MessageExtract sub = new MessageExtract();
			MessageExtractor founded = MessageExtractor.find(s.getContent());
			founded.extract(sub.getOrCreatePart(MessagePartTypeDefault.Title), mess, s.getContent());
			extr.getOrCreateMeta().put(MessageMetaExtractorKeyDefault.SubjectNode,
					sub.getContent(MessagePartTypeDefault.Title).get());
		}
	}

}
