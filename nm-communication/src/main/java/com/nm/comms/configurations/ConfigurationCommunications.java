package com.nm.comms.configurations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.history.SoaHistory;
import com.nm.app.mail.SoaEmail;
import com.nm.app.triggers.SoaTrigger;
import com.nm.comms.constants.CommunicationType.CommunicationTypeDefault;
import com.nm.comms.constants.MessageMetaExtractorKey.MessageMetaExtractorKeyDefault;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.converters.CommunicationActorConverterImpl;
import com.nm.comms.converters.CommunicationActorMailConverterImpl;
import com.nm.comms.converters.CommunicationContentConverterImpl;
import com.nm.comms.converters.CommunicationConverterImpl;
import com.nm.comms.converters.CommunicationSubjectConverterImpl;
import com.nm.comms.converters.MessageAsyncConverterImpl;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.daos.DaoCommunicationImpl;
import com.nm.comms.extractors.MessageExtractBuilder;
import com.nm.comms.extractors.MessageExtractorFile;
import com.nm.comms.extractors.MessageExtractorTemplate;
import com.nm.comms.extractors.MessageExtractorText;
import com.nm.comms.extractors.MessageMetaExtractor;
import com.nm.comms.extractors.MessageMetaExtractorActor;
import com.nm.comms.extractors.MessageMetaExtractorSubject;
import com.nm.comms.history.ConverterHistoryActorComm;
import com.nm.comms.history.ConverterHistorySubjectComm;
import com.nm.comms.senders.MessageSenderEmail;
import com.nm.comms.senders.MessageSenderSms;
import com.nm.comms.senders.MessageSenderWeb;
import com.nm.comms.soa.SoaCommunication;
import com.nm.comms.soa.SoaCommunicationImpl;
import com.nm.comms.triggers.ProcessorTriggerCommunicationSubject;
import com.nm.datas.SoaAppData;
import com.nm.datas.daos.DaoAppData;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationCommunications {
	public static final String MODULE_NAME = "communications";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(MessagePartTypeDefault.class);
		reg.put(CommunicationTypeDefault.class);
		reg.put(MessageMetaExtractorKeyDefault.class);
	}

	@Bean
	public CommunicationActorConverterImpl communicationActorConverterImpl() {
		return new CommunicationActorConverterImpl();
	}

	@Bean
	public CommunicationActorMailConverterImpl communicationActorMailConverterImpl() {
		return new CommunicationActorMailConverterImpl();
	}

	@Bean
	public CommunicationContentConverterImpl communicationContentConverterImpl(DaoAppData dao) {
		CommunicationContentConverterImpl c = new CommunicationContentConverterImpl();
		c.setDao(dao);
		return c;
	}

	@Bean
	public CommunicationConverterImpl communicationConverterImpl() {
		return new CommunicationConverterImpl();
	}

	@Bean
	public CommunicationSubjectConverterImpl communicationSubjectConverterImpl() {
		return new CommunicationSubjectConverterImpl();
	}

	@Bean
	public MessageAsyncConverterImpl messageAsyncConverterImpl() {
		return new MessageAsyncConverterImpl();
	}

	@Bean
	public DaoCommunication daoCommunicationImpl(DatabaseTemplateFactory fac) {
		DaoCommunicationImpl d = new DaoCommunicationImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public MessageExtractBuilder messageExtractBuilder(Collection<MessageMetaExtractor> metas) {
		MessageExtractBuilder d = new MessageExtractBuilder();
		d.setMetas(metas);
		return d;
	}

	@Bean
	public MessageExtractorFile messageExtractorFile(SoaAppData soa) {
		MessageExtractorFile m = new MessageExtractorFile();
		m.setSoaAppData(soa);
		return m;
	}

	@Bean
	public MessageExtractorTemplate messageExtractorTemplate() {
		return new MessageExtractorTemplate();
	}

	@Bean
	public MessageExtractorText messageExtractorText() {
		return new MessageExtractorText();
	}

	@Bean
	public MessageMetaExtractorActor messageMetaExtractorActor() {
		return new MessageMetaExtractorActor();
	}

	@Bean
	public MessageMetaExtractorSubject messageMetaExtractorSubject() {
		return new MessageMetaExtractorSubject();
	}

	@Bean
	public ConverterHistoryActorComm converterHistoryActorComm() {
		return new ConverterHistoryActorComm();
	}

	@Bean
	public ConverterHistorySubjectComm ConverterHistorySubjectComm(SoaAppData soa) {
		ConverterHistorySubjectComm c = new ConverterHistorySubjectComm();
		c.setSoaData(soa);
		return c;
	}

	@Bean
	public MessageSenderEmail messageSenderEmail(SoaEmail soa) {
		MessageSenderEmail m = new MessageSenderEmail();
		m.setSoaEmail(soa);
		return m;
	}

	@Bean
	public MessageSenderSms messageSenderSms() {
		return new MessageSenderSms();
	}

	@Bean
	public MessageSenderWeb messageSenderWeb(SoaHistory soa) {
		MessageSenderWeb m = new MessageSenderWeb();
		m.setSoaHistory(soa);
		return m;
	}

	@Bean
	public SoaCommunication soaCommunicationImpl(SoaTrigger soa, DtoConverterRegistry d) {
		SoaCommunicationImpl s = new SoaCommunicationImpl();
		s.setSoaTrigger(soa);
		s.setRegistry(d);
		return s;
	}

	@Bean
	public ProcessorTriggerCommunicationSubject ProcessorTriggerCommunicationSubject(MessageExtractBuilder m) {
		ProcessorTriggerCommunicationSubject p = new ProcessorTriggerCommunicationSubject();
		p.setExtractor(m);
		return p;
	}
}
