package com.nm.tests.comms;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.nm.comms.constants.CommunicationType.CommunicationTypeDefault;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.CommunicationSubjectDtoImpl;
import com.nm.comms.dtos.MessageContentDtoImpl;
import com.nm.comms.dtos.MessageContentDtoImpl.CommunicationContentType;
import com.nm.comms.dtos.MessageDtoAsync;
import com.nm.comms.history.HistorySubjectComm;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContentTemplate;
import com.nm.comms.soa.CommunicationAdapter;
import com.nm.comms.soa.CommunicationAdapterDefault;
import com.nm.comms.soa.SoaCommunication;
import com.nm.datas.AppDataException;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.datas.constants.AppDataOptions;
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
public class TestCommunication {
	//

	@Autowired
	private SoaCommunication soaCommunication;
	@Autowired
	protected DaoCommunication daoCommunication;
	@Autowired
	protected SoaAppData datasoa;
	@Autowired
	private SchedulerTriggerLauncher launcher;

	private MessageDtoAsync reporting;
	private CommunicationActorDtoImpl owner;
	private CommunicationSubjectDtoImpl subject;
	private DtoTriggerDefault trigger = null;
	private CommunicationAdapter adapter = new CommunicationAdapterTest();

	@org.junit.Before
	public void setup() throws Exception {
		reporting = new MessageDtoAsync();
		// EVERY HOUR
		reporting.setTrigger(
				trigger = new DtoTriggerDefault().setCron("0 0 * * * *").setWhen(DtoTriggerDefaultWhen.Cron));
		//
		{
			MessageContentDtoImpl dto = new MessageContentDtoImpl();
			dto.setType(CommunicationContentType.Template);
			AppDataDto dtoData = new AppDataDtoImpl().setName("yeah")
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
			AppDataDto dtoData = new AppDataDtoImpl().setName("yeah").setFile("Hello ${name} ${fname}".getBytes())
					.setType("text/plain");
			AppData data = datasoa.save(dtoData, new OptionsList());
			dto.setContent(new AppDataDtoImpl(data.getId()));
			reporting.getJoinded().add(dto);
		}
		//
		reporting.getType().add(CommunicationTypeDefault.Web);
		reporting.getReceivers().add(new CommunicationActorDtoImpl().setMail("company1@ccas.com"));
		reporting.getReceivers().add(new CommunicationActorDtoImpl().setMail("company2@ccas.com"));
		reporting.getReceivers().add(new CommunicationActorDtoImpl().setMail("company3@ccas.com"));
		//
		owner = new CommunicationActorDtoImpl().setMail("mine@owner.com");
		subject = new CommunicationSubjectDtoImpl().setContent(
				new MessageContentDtoImpl().setType(CommunicationContentType.Text).setContentText("SUBJECT"));
	}

	@Test
	@Transactional
	public void testShouldCreateChannel() throws Exception {
		CommunicationDto channelDto = soaCommunication.create(owner, subject, adapter);
		Assert.assertNotNull(channelDto.getId());
		IGenericDao<Communication, Long> dao = AbstractGenericDao.get(Communication.class);
		dao.flush();
		Communication chan = dao.get(channelDto.getId());
		CommunicationActorMail ac = (CommunicationActorMail) chan.getOwner();
		Assert.assertEquals(owner.getMail(), ac.getEmail());
	}

	@Test
	@Transactional
	public void testSaveCommunicationAsync() throws Exception {
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		CommunicationDto modifiedDto = soaCommunication.push(channelDto, reporting, adapter);
		Assert.assertEquals(channelDto.getId(), modifiedDto.getId());
		//
		IGenericDao<Communication, Long> dao = AbstractGenericDao.get(Communication.class);
		dao.flush();
		dao.clear();
		Communication chan = dao.get(channelDto.getId());
		Assert.assertEquals(1, chan.getMessages().size());
		Message com = chan.getMessages().iterator().next();
		Assert.assertEquals(3, com.getReceivers().size());
		Assert.assertEquals(2, com.findAll(MessagePartTypeDefault.Joined).size());
		Assert.assertFalse(((MessageContentTemplate) com.findFirst(MessagePartTypeDefault.Content)).getTemplate()
				.getArguments().isEmpty());
	}

	@Test
	@Transactional
	public void testShouldSend() throws Exception {
		IGenericDao<History, Long> dao = AbstractGenericDao.get(History.class);
		List<History> mess = dao.findAll();
		Assert.assertEquals(0, mess.size());
		trigger.setWhen(DtoTriggerDefaultWhen.Now);
		CommunicationDto channelDto = soaCommunication.create(owner, subject, new CommunicationAdapterDefault());
		CommunicationDto modifiedDto = soaCommunication.push(channelDto, reporting, adapter);
		Assert.assertEquals(channelDto.getId(), modifiedDto.getId());
		//
		launcher.executeAll();
		//
		dao.flush();
		mess = dao.findAll();
		Collections.sort(mess, new Comparator<History>() {

			public int compare(History o1, History o2) {
				return o1.getId().compareTo(o2.getId());
			}

		});
		Assert.assertEquals(6, mess.size());
		int nb = 0;
		for (AppData t : ((HistorySubjectComm) mess.get(0).getSubject()).getContent()) {
			if (StringUtils.containsIgnoreCase(getData(t), "Name")) {
				nb++;
			}
		}
		Assert.assertEquals(1, nb);
	}

	@Autowired
	private SoaAppData soaAppData;

	private String getData(AppData data) throws AppDataException {
		OptionsList ops = new OptionsList().withOption(AppDataOptions.Content);
		return soaAppData.fetch(data.getId(), ops).getText();
	}
	// TODO si sender meme message => grouper (test par hash dans
	// messageextractor)=> creer test unitaire
}
