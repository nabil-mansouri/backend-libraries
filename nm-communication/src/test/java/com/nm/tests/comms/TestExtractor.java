package com.nm.tests.comms;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.nm.app.triggers.DtoTriggerDefault;
import com.nm.comms.constants.MessageMetaExtractorKey.MessageMetaExtractorKeyDefault;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.extractors.MessageExtract;
import com.nm.comms.extractors.MessageExtractBuilder;
import com.nm.comms.extractors.MessageExtractNode;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.models.CommunicationSubjectSimple;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContentFile;
import com.nm.comms.models.MessageContentTemplate;
import com.nm.comms.models.MessageContentText;
import com.nm.comms.soa.CommunicationAdapter;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.templates.SoaTemplate;
import com.nm.templates.constants.TemplateType.TemplateTypeDefault;
import com.nm.templates.dtos.TemplateDtoImpl;
import com.nm.templates.models.Template;
import com.nm.tests.bridge.CommunicationAdapterTest;
import com.nm.tests.bridge.ConfigurationTestCommunication;
import com.nm.tests.bridge.TemplateArgsEnumTest;
import com.nm.tests.bridge.TemplateNameEnumTest;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.ByteUtils;
import com.nm.utils.dtos.OptionsList;
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
public class TestExtractor {
	//

	@Autowired
	protected DaoCommunication daoCommunication;
	@Autowired
	protected SoaAppData datasoa;

	protected DtoTriggerDefault trigger = null;
	protected CommunicationAdapter adapter = new CommunicationAdapterTest();

	@org.junit.Before
	public void setup() throws Exception {

	}

	@Test
	@Transactional
	public void testShouldExtractMetaSubject() throws Exception {
		MessageExtractBuilder builder = ApplicationUtils.getBean(MessageExtractBuilder.class);
		Message message = new Message();
		message.setCommunication(new Communication());
		message.getCommunication()
				.setAbout(new CommunicationSubjectSimple().setContent(new MessageContentText().setText("OK")));
		//
		Map<Long, CommunicationActor> actors = Maps.newConcurrentMap();
		for (long i = 0; i < 2; i++) {
			actors.put(i, new CommunicationActorMail().setEmail("nab@nab.com" + i).setId(i));
			message.getReceivers().add(actors.get(i));
		}
		//
		MessageExtract extract = builder.build(message);
		MessageExtractNode node = extract.getOrCreateMeta(actors.get(0l))
				.getBy(MessageMetaExtractorKeyDefault.SubjectNode);
		Assert.assertNotNull(node);
		Assert.assertEquals("OK", ByteUtils.toStrings(node.getContent()));
	}

	@Test
	@Transactional
	public void testShouldExtractMetaMailTo() throws Exception {
		MessageExtractBuilder builder = ApplicationUtils.getBean(MessageExtractBuilder.class);
		Message message = new Message();
		message.setCommunication(new Communication());
		Map<Long, CommunicationActor> actors = Maps.newConcurrentMap();
		for (long i = 0; i < 2; i++) {
			actors.put(i, new CommunicationActorMail().setEmail("nab@nab.com" + i).setId(i));
			message.getReceivers().add(actors.get(i));
		}
		message.getReceivers().add(new CommunicationActorMail().setEmail("nab@nab.com2").setId(2l));
		message.getCommunication()
				.setAbout(new CommunicationSubjectSimple().setContent(new MessageContentText().setText("OK")));
		MessageExtract extract = builder.build(message);
		for (long i = 0; i < 2; i++) {
			String node = extract.getOrCreateMeta(actors.get(i)).getBy(MessageMetaExtractorKeyDefault.MailTo);
			Assert.assertEquals("nab@nab.com" + i, node);
		}
	}

	@Test
	@Transactional
	public void testShouldExtractContentText() throws Exception {
		MessageExtractBuilder builder = ApplicationUtils.getBean(MessageExtractBuilder.class);
		Message message = new Message();
		message.setCommunication(new Communication());
		message.getContents().add(new MessageContentText().setText("TITLE").setType(MessagePartTypeDefault.Title));
		message.getContents().add(new MessageContentText().setText("CONTENT").setType(MessagePartTypeDefault.Content));
		//
		CommunicationActor actor = new CommunicationActorMail().setEmail("nab@nab.com").setId(0l);
		message.getReceivers().add(actor);
		//
		MessageExtract extract = builder.build(message);
		Assert.assertEquals("TITLE",
				ByteUtils.toStrings(extract.getContent(MessagePartTypeDefault.Title).getBy(actor).getContent()));
		Assert.assertEquals("CONTENT",
				ByteUtils.toStrings(extract.getContent(MessagePartTypeDefault.Content).getBy(actor).getContent()));
	}

	@Test
	@Transactional
	public void testShouldExtractContentFile() throws Exception {
		MessageExtractBuilder builder = ApplicationUtils.getBean(MessageExtractBuilder.class);
		Message message = new Message();
		message.setCommunication(new Communication());
		AppDataDto dtoData = new AppDataDtoImpl().setName("test").setFile("TITLE".getBytes()).setType("text/plain");
		AppData data = datasoa.save(dtoData, new OptionsList());
		message.getContents().add(new MessageContentFile().setData(data).setType(MessagePartTypeDefault.Title));
		//
		dtoData = new AppDataDtoImpl().setName("test").setFile("CONTENT".getBytes()).setType("text/plain");
		data = datasoa.save(dtoData, new OptionsList());
		message.getContents().add(new MessageContentFile().setData(data).setType(MessagePartTypeDefault.Content));
		//
		CommunicationActor actor = new CommunicationActorMail().setEmail("nab@nab.com").setId(0l);
		message.getReceivers().add(actor);
		//
		MessageExtract extract = builder.build(message);
		Assert.assertEquals("TITLE",
				ByteUtils.toStrings(extract.getContent(MessagePartTypeDefault.Title).getBy(actor).getContent()));
		Assert.assertEquals("CONTENT",
				ByteUtils.toStrings(extract.getContent(MessagePartTypeDefault.Content).getBy(actor).getContent()));
	}

	@Test
	@Transactional
	public void testShouldExtractContentTemplate() throws Exception {
		MessageExtractBuilder builder = ApplicationUtils.getBean(MessageExtractBuilder.class);
		Message message = new Message();
		message.setCommunication(new Communication());
		SoaTemplate soaT = ApplicationUtils.getBean(SoaTemplate.class);
		//
		AppDataDto dtoData = new AppDataDtoImpl().setName("test").setFile("CONTENT".getBytes()).setType("text/plain");
		AppData data = datasoa.save(dtoData, new OptionsList());
		TemplateDtoImpl template = new TemplateDtoImpl();
		template.setContent(new AppDataDtoImpl(data.getId()));
		template.setTemplateType(TemplateTypeDefault.Velocity);
		template.setName(TemplateNameEnumTest.EmailDailyReport);
		template.setType(AppDataMediaType.Html);
		//
		Template t = AbstractGenericDao.get(Template.class).get(soaT.saveTemplate(template, new OptionsList()).getId());
		message.getContents().add(new MessageContentTemplate().setTemplate(t).setType(MessagePartTypeDefault.Content));
		//
		CommunicationActor actor = new CommunicationActorMail().setEmail("nab@nab.com").setId(0l);
		message.getReceivers().add(actor);
		//
		MessageExtract extract = builder.build(message);
		Assert.assertEquals("CONTENT",
				ByteUtils.toStrings(extract.getContent(MessagePartTypeDefault.Content).getBy(actor).getContent()));
	}

	@Test
	@Transactional
	public void testShouldExtractContentTemplateByActor() throws Exception {
		MessageExtractBuilder builder = ApplicationUtils.getBean(MessageExtractBuilder.class);
		Message message = new Message();
		message.setCommunication(new Communication());
		SoaTemplate soaT = ApplicationUtils.getBean(SoaTemplate.class);
		//
		AppDataDto dtoData = new AppDataDtoImpl().setName("test").setFile("CONTENT ${testEmailActor}".getBytes())
				.setType("text/plain");
		AppData data = datasoa.save(dtoData, new OptionsList());
		TemplateDtoImpl template = new TemplateDtoImpl();
		template.setContent(new AppDataDtoImpl(data.getId()));
		template.setTemplateType(TemplateTypeDefault.Velocity);
		template.setName(TemplateNameEnumTest.EmailDailyReport);
		template.setType(AppDataMediaType.Html);
		template.getArguments().add(TemplateArgsEnumTest.TestEmail);
		//
		Template t = AbstractGenericDao.get(Template.class).get(soaT.saveTemplate(template, new OptionsList()).getId());
		message.getContents().add(new MessageContentTemplate().setTemplate(t).setType(MessagePartTypeDefault.Content));
		//
		Map<Long, CommunicationActorMail> actors = Maps.newConcurrentMap();
		for (long i = 0; i < 2; i++) {
			CommunicationActor actor = new CommunicationActorMail().setEmail("nab@nab.com" + i).setId(i);
			actors.put(i, (CommunicationActorMail) actor);
			message.getReceivers().add(actor);
		}
		//
		MessageExtract extract = builder.build(message);
		for (long i = 0; i < 2; i++) {
			Assert.assertEquals(String.format("CONTENT %s", actors.get(i).getEmail()), ByteUtils
					.toStrings(extract.getContent(MessagePartTypeDefault.Content).getBy(actors.get(i)).getContent()));
		}
	}

	@Test
	@Transactional
	public void testShouldExtractContentTemplateByActorOnlyOne() throws Exception {
		MessageExtractBuilder builder = ApplicationUtils.getBean(MessageExtractBuilder.class);
		Message message = new Message();
		message.setCommunication(new Communication());
		SoaTemplate soaT = ApplicationUtils.getBean(SoaTemplate.class);
		//
		AppDataDto dtoData = new AppDataDtoImpl().setName("test").setFile("CONTENT ${testEmailActor}".getBytes())
				.setType("text/plain");
		AppData data = datasoa.save(dtoData, new OptionsList());
		TemplateDtoImpl template = new TemplateDtoImpl();
		template.setContent(new AppDataDtoImpl(data.getId()));
		template.setTemplateType(TemplateTypeDefault.Velocity);
		template.setName(TemplateNameEnumTest.EmailDailyReport);
		template.setType(AppDataMediaType.Html);
		template.getArguments().add(TemplateArgsEnumTest.TestEmail);
		//
		Template t = AbstractGenericDao.get(Template.class).get(soaT.saveTemplate(template, new OptionsList()).getId());
		message.getContents().add(new MessageContentTemplate().setTemplate(t).setType(MessagePartTypeDefault.Content));
		//
		Map<Long, CommunicationActorMail> actors = Maps.newConcurrentMap();
		for (long i = 0; i < 2; i++) {
			CommunicationActor actor = new CommunicationActorMail().setEmail("nab@nab.com" + i).setId(i);
			actors.put(i, (CommunicationActorMail) actor);
			message.getReceivers().add(actor);
		}
		//
		MessageExtract extract = builder.build(message, actors.get(0));
		for (long i = 0; i < 1; i++) {
			Assert.assertEquals(String.format("CONTENT %s", actors.get(i).getEmail()), ByteUtils
					.toStrings(extract.getContent(MessagePartTypeDefault.Content).getBy(actors.get(i)).getContent()));
		}
	}
}
