package com.nm.tests.comms;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.models.Message;
import com.nm.comms.templates.TemplateProcessorImplCommunication;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.templates.SoaTemplate;
import com.nm.templates.constants.TemplateArgsEnum.TemplateArgsEnumDefault;
import com.nm.templates.constants.TemplateType.TemplateTypeDefault;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.dtos.TemplateDtoImpl;
import com.nm.templates.models.Template;
import com.nm.templates.processors.TemplateProcessor;
import com.nm.templates.processors.TemplateProcessorImplSimple;
import com.nm.templates.processors.TemplateProcessorListener;
import com.nm.templates.processors.strategies.TemplateProcessorStrategyFactory;
import com.nm.tests.bridge.ConfigurationTestCommunication;
import com.nm.tests.bridge.TemplateArgsEnumTest;
import com.nm.tests.bridge.TemplateNameEnumTest;
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
public class TestTemplating {
	//

	@Autowired
	private SoaTemplate soaTemplate;
	@Autowired
	protected DaoCommunication daoCommunication;
	@Autowired
	protected SoaAppData datasoa;

	private TemplateDtoImpl template;
	private OptionsList options = new OptionsList();

	@org.junit.Before
	public void setup() throws Exception {
		{
			AppDataDto dtoData = new AppDataDtoImpl().setName("test")
					.setFile("Hello ${testname} ${testdate} ${testEmailActor}".getBytes()).setType("text/plain");
			AppData data = datasoa.save(dtoData, new OptionsList());
			template = new TemplateDtoImpl();
			template.setContent(new AppDataDtoImpl(data.getId()));
			template.setTemplateType(TemplateTypeDefault.Velocity);
			template.setName(TemplateNameEnumTest.EmailDailyReport);
			template.setType(AppDataMediaType.Html);
			template.getArguments().add(TemplateArgsEnumTest.ReceptorName);
			template.getArguments().add(TemplateArgsEnumTest.ReceptorCompany);
			template.getArguments().add(TemplateArgsEnumDefault.CurrentFullDate);
			template.getArguments().add(TemplateArgsEnumDefault.CurrentShortHour);
		}
	}

	@Test
	@Transactional
	public void testShouldSaveVelocityTemplate() throws Exception {
		TemplateDtoImpl saved = (TemplateDtoImpl) soaTemplate.saveTemplate(template, options);
		Assert.assertNotNull(saved.getId());
		daoCommunication.flush();
		daoCommunication.clear();
		Template t = AbstractGenericDao.get(Template.class).get(saved.getId());
		Assert.assertFalse(t.getArguments().isEmpty());
	}

	@Test
	@Transactional
	public void testShouldSaveVelocityTemplateOnlyOnce() throws Exception {
		TemplateDtoImpl saved = (TemplateDtoImpl) soaTemplate.saveOrReplaceTemplate(template, options);
		Assert.assertNotNull(saved.getId());
		daoCommunication.flush();
		daoCommunication.clear();
		template.setId(null);
		saved = (TemplateDtoImpl) soaTemplate.saveOrReplaceTemplate(template, options);
		Assert.assertNotNull(saved.getId());
		daoCommunication.flush();
		daoCommunication.clear();
		Assert.assertEquals(1, AbstractGenericDao.get(Template.class).count().intValue());
	}

	@Test
	@Transactional
	public void testShouldSaveVelocityTemplateTwice() throws Exception {
		TemplateDtoImpl saved = (TemplateDtoImpl) soaTemplate.saveOrReplaceTemplate(template, options);
		Assert.assertNotNull(saved.getId());
		daoCommunication.flush();
		daoCommunication.clear();
		template.setId(null);
		template.setCriteria("aaaa");
		saved = (TemplateDtoImpl) soaTemplate.saveOrReplaceTemplate(template, options);
		Assert.assertNotNull(saved.getId());
		daoCommunication.flush();
		daoCommunication.clear();
		Assert.assertEquals(2, AbstractGenericDao.get(Template.class).count().intValue());
	}

	private static class TemplateProcessorListenerMine implements TemplateProcessorListener {
		OutputStream out;
		TemplateProcessor p;
		int nb = 0;

		public void onBuildContext(TemplateContext original) {

		}

		public void generate(TemplateProcessor a, OutputStream out) {
			this.out = out;
			this.p = a;
			this.nb++;
		}
	}

	@Test
	@Transactional
	public void testShouldProcessSimpleVelocity() throws Exception {
		TemplateDtoImpl saved = (TemplateDtoImpl) soaTemplate.saveTemplate(template, options);
		Template t = AbstractGenericDao.get(Template.class).get(saved.getId());
		//
		TemplateProcessorListenerMine list = new TemplateProcessorListenerMine();
		TemplateProcessorImplSimple simple = new TemplateProcessorImplSimple(list);
		simple.generate(TemplateProcessorStrategyFactory.defaultStrategy(t));
		Assert.assertNotNull(list.p);
		Assert.assertNotNull(list.out);
		ByteArrayOutputStream oo = (ByteArrayOutputStream) list.out;
		String result = ByteUtils.toStrings(oo.toByteArray());
		System.out.println("-------------------------------------");
		System.out.println(result);
		Assert.assertTrue(StringUtils.containsIgnoreCase(result, "01-01-2000"));
		Assert.assertTrue(StringUtils.containsIgnoreCase(result, "Name"));
	}

	@Test
	@Transactional
	public void testShouldProcessActorVelocity() throws Exception {
		Message message = new Message();
		message.setCommunication(new Communication());
		Map<Long, CommunicationActor> actors = Maps.newConcurrentMap();
		for (long i = 0; i < 2; i++) {
			actors.put(i, new CommunicationActorMail().setEmail("nab@nab.com" + i).setId(i));
			message.getReceivers().add(actors.get(i));
		}
		//
		TemplateDtoImpl saved = (TemplateDtoImpl) soaTemplate.saveTemplate(template, options);
		Template t = AbstractGenericDao.get(Template.class).get(saved.getId());
		//
		TemplateProcessorListenerMine list = new TemplateProcessorListenerMine();
		TemplateProcessorImplCommunication simple = new TemplateProcessorImplCommunication(list, message);
		simple.generate(TemplateProcessorStrategyFactory.defaultStrategy(t));
		Assert.assertNotNull(list.p);
		Assert.assertNotNull(list.out);
		ByteArrayOutputStream oo = (ByteArrayOutputStream) list.out;
		String result = ByteUtils.toStrings(oo.toByteArray());
		System.out.println("-------------------------------------");
		System.out.println(result);
		Assert.assertTrue(StringUtils.containsIgnoreCase(result, "01-01-2000"));
		Assert.assertTrue(StringUtils.containsIgnoreCase(result, "Name"));
		Assert.assertTrue(StringUtils.containsIgnoreCase(result, "nab.com"));
		Assert.assertEquals(2, list.nb);
	}
}
