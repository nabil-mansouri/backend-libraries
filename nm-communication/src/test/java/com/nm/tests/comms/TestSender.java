package com.nm.tests.comms;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.history.History;
import com.nm.app.mail.EmailBuilderContract;
import com.nm.app.mail.SoaEmail;
import com.nm.app.triggers.DtoTriggerDefault;
import com.nm.app.triggers.DtoTriggerDefault.DtoTriggerDefaultWhen;
import com.nm.comms.constants.CommunicationType.CommunicationTypeDefault;
import com.nm.comms.constants.MessageBoxType;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.CommunicationSubjectDtoImpl;
import com.nm.comms.dtos.MessageContentDtoImpl;
import com.nm.comms.dtos.MessageContentDtoImpl.CommunicationContentType;
import com.nm.comms.dtos.MessageDtoAsync;
import com.nm.comms.extractors.MessageExtract;
import com.nm.comms.history.HistoryActorComm;
import com.nm.comms.history.HistorySubjectComm;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationMedium;
import com.nm.comms.models.Message;
import com.nm.comms.senders.MessageSender;
import com.nm.comms.senders.MessageSenderEmail;
import com.nm.comms.senders.MessageSenderFactory;
import com.nm.comms.senders.MessageSenderWeb;
import com.nm.comms.soa.CommunicationAdapter;
import com.nm.comms.soa.CommunicationAdapterDefault;
import com.nm.comms.soa.SoaCommunication;
import com.nm.datas.SoaAppData;
import com.nm.tests.bridge.CommunicationAdapterTest;
import com.nm.tests.bridge.ConfigurationTestCommunication;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.ByteUtils;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestCommunication.class)
public class TestSender {
	//

	@Autowired
	private SoaCommunication soaCommunication;
	@Autowired
	protected DaoCommunication daoCommunication;
	@Autowired
	protected SoaAppData datasoa;
	//
	@InjectMocks
	private MessageSenderEmail email;
	@Mock
	private SoaEmail soaEmail;

	//

	private MessageDtoAsync reporting;
	private CommunicationActorDtoImpl owner;
	private CommunicationSubjectDtoImpl subject;
	private CommunicationAdapter adapter = new CommunicationAdapterTest();
	private EmailBuilderContract contract;

	@org.junit.Before
	public void setup() throws Exception {
		reporting = new MessageDtoAsync();
		// EVERY HOUR
		reporting.setTrigger(new DtoTriggerDefault().setWhen(DtoTriggerDefaultWhen.Cron).setCron("0 0 * * * *"));
		//
		{
			MessageContentDtoImpl dto = new MessageContentDtoImpl();
			dto.setType(CommunicationContentType.Text);
			dto.setContentText("JOINDED FILE");
			reporting.setContent(dto);
		}
		//
		reporting.getType().add(CommunicationTypeDefault.Email);
		reporting.getReceivers().add(new CommunicationActorDtoImpl().setMail("company1@ccas.com"));
		//
		owner = new CommunicationActorDtoImpl().setMail("mine@owner.com");
		subject = new CommunicationSubjectDtoImpl().setContent(
				new MessageContentDtoImpl().setType(CommunicationContentType.Text).setContentText("SUBJECT"));
		//
		MockitoAnnotations.initMocks(this);
		Mockito.doAnswer(new Answer<Void>() {

			public Void answer(InvocationOnMock invocation) throws Throwable {
				contract = invocation.getArgumentAt(0, EmailBuilderContract.class);
				return null;
			}
		}).when(soaEmail).send(Mockito.any(EmailBuilderContract.class));
	}

	@Test
	@Transactional
	public void testShouldFindByMediumWeb() throws Exception {
		Message message = new Message();
		message.setCommunication(new Communication());
		message.getMediums().add(new CommunicationMedium().setType(CommunicationTypeDefault.Web));
		//
		Collection<MessageSender> sender = MessageSenderFactory.find(message);
		Assert.assertEquals(1, sender.size());
	}

	@Test
	@Transactional
	public void testShouldSendWeb() throws Exception {
		IGenericDao<History, Long> dao = AbstractGenericDao.get(History.class);
		List<History> mess = dao.findAll();
		Assert.assertEquals(0, mess.size());
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		soaCommunication.push(channelDto, reporting, adapter);
		//
		IGenericDao<Communication, Long> daos = AbstractGenericDao.get(Communication.class);
		daos.flush();
		daos.clear();
		Communication chan = daos.get(channelDto.getId());
		//
		Message message = chan.getLast();
		CommunicationActor sender = message.getSender();
		CommunicationActor receiver = message.getReceivers().iterator().next();
		//
		MessageExtract extract = new MessageExtract();
		//
		extract.getOrCreatePart(MessagePartTypeDefault.Content).create(ByteUtils.toBytes("CONTENT"), receiver); //
		extract.getOrCreatePart(MessagePartTypeDefault.Title).create(ByteUtils.toBytes("TITLE"), receiver); //
		MessageSenderWeb web = ApplicationUtils.getBean(MessageSenderWeb.class);
		web.send(extract, message, receiver);
		//
		dao.flush();
		mess = dao.findAll();
		Collections.sort(mess, new Comparator<History>() {

			public int compare(History o1, History o2) {
				return o1.getId().compareTo(o2.getId());
			}

		});
		Assert.assertEquals(2, mess.size());
		Assert.assertEquals(MessageBoxType.Out, ((HistorySubjectComm) mess.get(0).getSubject()).getType());
		Assert.assertEquals(sender.getId(), ((HistoryActorComm) mess.get(0).getActor()).getActor().getId());
		Assert.assertEquals(MessageBoxType.In, ((HistorySubjectComm) mess.get(1).getSubject()).getType());
		Assert.assertEquals(receiver.getId(), ((HistoryActorComm) mess.get(1).getActor()).getActor().getId());
	}

	@Test
	@Transactional
	public void testShouldSendEmail() throws Exception {
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		soaCommunication.push(channelDto, reporting, adapter);
		//
		IGenericDao<Communication, Long> daos = AbstractGenericDao.get(Communication.class);
		daos.flush();
		daos.clear();
		Communication chan = daos.get(channelDto.getId());
		//
		Message message = chan.getLast();
		CommunicationActor receiver = message.getReceivers().iterator().next();
		//
		MessageExtract extract = new MessageExtract();
		//
		extract.getOrCreatePart(MessagePartTypeDefault.Content).create(ByteUtils.toBytes("CONTENT"), receiver); //
		extract.getOrCreatePart(MessagePartTypeDefault.Title).create(ByteUtils.toBytes("TITLE"), receiver); //
		email.send(extract, message, receiver);
		//
		Assert.assertNotNull(contract);
		Assert.assertEquals("CONTENT", contract.body());
		Assert.assertEquals("TITLE", contract.subject());
	}

	@Test
	@Transactional
	public void testShouldSendSms() throws Exception {
		// TODO send sms
	}
}
