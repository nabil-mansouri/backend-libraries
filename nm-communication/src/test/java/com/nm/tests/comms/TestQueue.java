package com.nm.tests.comms;

import org.joda.time.MutableDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.history.History;
import com.nm.app.triggers.DtoTriggerDefault;
import com.nm.app.triggers.DtoTriggerDefault.DtoTriggerDefaultWhen;
import com.nm.app.triggers.SchedulerTriggerLauncher;
import com.nm.app.triggers.TriggerCron;
import com.nm.comms.constants.CommunicationType.CommunicationTypeDefault;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.CommunicationSubjectDtoImpl;
import com.nm.comms.dtos.MessageContentDtoImpl;
import com.nm.comms.dtos.MessageContentDtoImpl.CommunicationContentType;
import com.nm.comms.dtos.MessageDtoAsync;
import com.nm.comms.models.Communication;
import com.nm.comms.models.Message;
import com.nm.comms.soa.CommunicationAdapter;
import com.nm.comms.soa.CommunicationAdapterDefault;
import com.nm.comms.soa.SoaCommunication;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.templates.constants.TemplateArgsEnum.TemplateArgsEnumDefault;
import com.nm.templates.constants.TemplateType.TemplateTypeDefault;
import com.nm.templates.dtos.TemplateDtoImpl;
import com.nm.tests.bridge.CommunicationAdapterTest;
import com.nm.tests.bridge.ConfigurationTestCommunication;
import com.nm.tests.bridge.TemplateArgsEnumTest;
import com.nm.tests.bridge.TemplateNameEnumTest;
import com.nm.utils.dtos.OptionsList;
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
public class TestQueue {
	//

	@Autowired
	private SoaCommunication soaCommunication;
	@Autowired
	protected DaoCommunication daoCommunication;
	@Autowired
	private SchedulerTriggerLauncher launcher;
	@Autowired
	protected SoaAppData datasoa;

	private MessageDtoAsync reporting;
	private CommunicationActorDtoImpl owner;
	private CommunicationSubjectDtoImpl subject;
	private DtoTriggerDefault trigger = null;
	private IGenericDao<History, Long> dao;
	private CommunicationAdapter adapter = new CommunicationAdapterTest();

	@org.junit.Before
	public void setup() throws Exception {
		reporting = new MessageDtoAsync();
		// SEND NOW A MESSAGE
		reporting.setTrigger(trigger = new DtoTriggerDefault().setWhen(DtoTriggerDefaultWhen.Now));
		//
		{
			MessageContentDtoImpl dto = new MessageContentDtoImpl();
			dto.setType(CommunicationContentType.Template);
			AppDataDto dtoData = new AppDataDtoImpl().setName("test")
					.setFile("Hello ${testdate} ${testname} ${testactor}".getBytes()).setType("text/plain");
			AppData data = datasoa.save(dtoData, new OptionsList());
			TemplateDtoImpl template = new TemplateDtoImpl();
			template.setContent(new AppDataDtoImpl(data.getId()));
			dto.setTemplate(template);
			template.setTemplateType(TemplateTypeDefault.Velocity);
			template.setName(TemplateNameEnumTest.EmailDailyReport);
			template.setType(AppDataMediaType.Html);
			template.getArguments().add(TemplateArgsEnumTest.ReceptorName);
			template.getArguments().add(TemplateArgsEnumTest.ReceptorCompany);
			template.getArguments().add(TemplateArgsEnumDefault.CurrentFullDate);
			template.getArguments().add(TemplateArgsEnumDefault.CurrentShortHour);
			reporting.setContent(dto);
		}
		//
		{
			MessageContentDtoImpl dto = new MessageContentDtoImpl();
			dto.setType(CommunicationContentType.Text);
			dto.setContentText("JOINDED FILE");
			reporting.getJoinded().add(dto);
		}
		{
			MessageContentDtoImpl dto = new MessageContentDtoImpl();
			dto.setType(CommunicationContentType.File);
			AppDataDto dtoData = new AppDataDtoImpl().setName("test").setFile("Hello ${name} ${fname}".getBytes())
					.setType("text/plain");
			AppData data = datasoa.save(dtoData, new OptionsList());
			dto.setContent(new AppDataDtoImpl(data.getId()));
			reporting.getJoinded().add(dto);
		}
		//
		reporting.getType().add(CommunicationTypeDefault.Web);
		reporting.getReceivers().add(new CommunicationActorDtoImpl().setMail("company1@ccas.com"));
		//
		owner = new CommunicationActorDtoImpl().setMail("mine@owner.com");
		subject = new CommunicationSubjectDtoImpl().setContent(
				new MessageContentDtoImpl().setType(CommunicationContentType.Text).setContentText("SUBJECT"));
		//
		dao = AbstractGenericDao.get(History.class);
	}

	@Test
	@Transactional
	public void testShouldProgramForNextHour() throws Exception {
		trigger.setCron("0 0 * * * *").setWhen(DtoTriggerDefaultWhen.Cron);
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		soaCommunication.push(channelDto, reporting, adapter);
		//
		IGenericDao<Communication, Long> dao = AbstractGenericDao.get(Communication.class);
		dao.flush();
		dao.clear();
		Communication chan = dao.get(channelDto.getId());
		Message last = chan.getLast();
		TriggerCron cron = (TriggerCron) last.getTrigger().getTrigger();
		MutableDateTime mut = new MutableDateTime();
		mut.addHours(1);
		mut.setMinuteOfHour(0);
		mut.setSecondOfMinute(0);
		mut.setMillisOfSecond(0);
		Assert.assertEquals(mut.toDate(), cron.getScheduledAt());
	}

	@Test
	@Transactional
	public void testShouldNotFetchQueueCron() throws Exception {
		// SEND EVERY DAY
		trigger.setCron("0 0 0 * * *").setWhen(DtoTriggerDefaultWhen.Cron);
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		CommunicationDto modifiedDto = soaCommunication.push(channelDto, reporting, adapter);
		Assert.assertEquals(channelDto.getId(), modifiedDto.getId());
		Thread.sleep(1000);
		//
		launcher.executeAll();
		// SHOULD NOT SEND ANYTHING
		Assert.assertEquals(0, dao.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldFetchQueueCron() throws Exception {
		// SHOULD SEND EVERY SECOND
		trigger.setWhen(DtoTriggerDefaultWhen.Cron).setCron("* * * * * *");
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		soaCommunication.push(channelDto, reporting, adapter);
		Thread.sleep(1000l);
		//
		launcher.executeAll();
		Assert.assertEquals(2, dao.findAll().size());
		// WAIT ANOTHER SEC
		Thread.sleep(1000l);
		launcher.executeAll();
		Assert.assertEquals(4, dao.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldNotFetchQueueNow() throws Exception {
		MutableDateTime da = new MutableDateTime();
		da.addHours(1);
		trigger.setWhen(DtoTriggerDefaultWhen.Date).setDate(da.toDate());
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		soaCommunication.push(channelDto, reporting, adapter);
		//
		launcher.executeAll();
		Assert.assertEquals(0, dao.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldFetchQueueNow() throws Exception {
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		trigger.setWhen(DtoTriggerDefaultWhen.Now);
		soaCommunication.push(channelDto, reporting, adapter);
		//
		launcher.executeAll();
		Assert.assertEquals(2, dao.findAll().size());
		//
		launcher.executeAll();
		Assert.assertEquals(2, dao.findAll().size());
	}
}
