package com.nm.tests.triggers;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.MutableDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.triggers.DaoTrigger;
import com.nm.app.triggers.DtoTriggerDefault;
import com.nm.app.triggers.DtoTriggerDefault.DtoTriggerDefaultWhen;
import com.nm.app.triggers.EventTriggerOnEvent;
import com.nm.app.triggers.SchedulerTriggerContext;
import com.nm.app.triggers.SchedulerTriggerCron;
import com.nm.app.triggers.SchedulerTriggerLauncher;
import com.nm.app.triggers.SoaTrigger;
import com.nm.app.triggers.Trigger;
import com.nm.app.triggers.TriggerCron;
import com.nm.app.triggers.TriggerDate;
import com.nm.app.triggers.TriggerEvent;
import com.nm.app.triggers.TriggerEventEnum.TriggerEventEnumDefault;
import com.nm.app.triggers.TriggerException;
import com.nm.tests.ConfigurationTestApplication;
import com.nm.utils.ApplicationUtils;
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
@ContextConfiguration(classes = ConfigurationTestApplication.class)
public class TestTrigger {

	//
	protected Log log = LogFactory.getLog(getClass());
	@Autowired
	private SoaTrigger soaTrigger;
	@Autowired
	private DaoTrigger daoTrigger;
	@Autowired
	private SchedulerTriggerLauncher launcher;
	@Autowired
	private ProcessorTriggerSubjectTest processor;
	@Autowired
	private ApplicationEventPublisher publisher;
	//
	private TriggerSubjectTest subject;

	@Before
	public void setup() {
		subject = new TriggerSubjectTest();
		AbstractGenericDao.get(TriggerSubjectTest.class).save(subject);
	}

	@Test
	@Transactional
	public void testShouldCreateJobAfterSave() throws Exception {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Date);
		dto.setDate(new Date());
		soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		daoTrigger.flush();
		Assert.assertNotNull(launcher.getFuture());
	}

	@Test
	@Transactional
	public void testShouldExecuteCron() throws Exception {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Cron);
		dto.setCron("* * * * * *");
		Trigger trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		daoTrigger.flush();
		Assert.assertNotNull(trigger.getId());
		Assert.assertTrue(trigger instanceof TriggerCron);
	}

	@Test
	@Transactional
	public void testShouldFindLeastDate() throws Exception {
		MutableDateTime date = new MutableDateTime();
		for (int i = 0; i < 2; i++) {
			DtoTriggerDefault dto = new DtoTriggerDefault();
			dto.setWhen(DtoTriggerDefaultWhen.Date);
			MutableDateTime copy = date.copy();
			copy.addHours(i);
			dto.setDate(copy.toDate());
			subject = new TriggerSubjectTest();
			AbstractGenericDao.get(TriggerSubjectTest.class).save(subject);
			soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		}
		daoTrigger.flush();
		Assert.assertEquals(date.toDate(), daoTrigger.leastScheduleDate());
	}

	@Test
	@Transactional
	public void testShouldFindTriggerCronToExecute() throws Exception {
		TriggerCron date = new TriggerCron();
		date.setCron("* * * * * *");
		date.setSubject(subject);
		daoTrigger.save(date);
		ApplicationUtils.getBean(SchedulerTriggerCron.class).schedule(new SchedulerTriggerContext(date, null));
		daoTrigger.flush();
		Assert.assertNotNull(date.getScheduledAt());
		Assert.assertNull(date.getLastExecution());
		System.out.println("------------------------------");
		System.out.println(date.getScheduledAt());
		daoTrigger.flush();
		Thread.sleep(1000);
		Assert.assertEquals(1, launcher.findToExecute().size());
	}

	@Test
	@Transactional
	public void testShouldFindTriggerDateToExecute() throws Exception {
		TriggerDate date = new TriggerDate();
		date.setScheduledAt(new Date());
		date.setSubject(subject);
		daoTrigger.save(date);
		daoTrigger.flush();
		Assert.assertEquals(1, launcher.findToExecute().size());
	}

	@Test
	@Transactional
	public void testShouldFindTriggerDateToExecuteOnlyOnce() throws Exception {
		TriggerDate date = new TriggerDate();
		date.setScheduledAt(new Date());
		date.setSubject(subject);
		daoTrigger.save(date);
		daoTrigger.flush();
		Assert.assertEquals(1, launcher.findToExecute().size());
		date.setLastExecution(new Date());
		daoTrigger.save(date);
		daoTrigger.flush();
		Assert.assertEquals(0, launcher.findToExecute().size());
	}

	@Test
	@Transactional
	public void testShouldLeastScheduledateBeNull() throws Exception {
		Assert.assertNull(daoTrigger.leastScheduleDate());
		Assert.assertNotNull(launcher.getLastJobUpdate());
	}

	@Test
	@Transactional
	public void testShouldSaveCron() throws Exception {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Cron);
		dto.setCron("* * * * * *");
		Trigger trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		daoTrigger.flush();
		Assert.assertNotNull(trigger.getId());
		Assert.assertTrue(trigger instanceof TriggerCron);
	}

	@Test
	@Transactional
	public void testShouldSaveDate() throws Exception {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Date);
		dto.setDate(new Date());
		Trigger trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		daoTrigger.flush();
		Assert.assertNotNull(trigger.getId());
		Assert.assertTrue(trigger instanceof TriggerDate);
	}

	@Test
	@Transactional
	public void testShouldSaveEvent() throws Exception {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Event);
		dto.setEvent(TriggerEventEnumDefault.Default);
		Trigger trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		daoTrigger.flush();
		Assert.assertNotNull(trigger.getId());
		Assert.assertTrue(trigger instanceof TriggerEvent);
	}

	@Test
	@Transactional
	public void testShouldSaveNow() throws Exception {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Now);
		Trigger trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		daoTrigger.flush();
		Assert.assertNotNull(trigger.getId());
		Assert.assertTrue(trigger instanceof TriggerDate);
	}

	@Test
	@Transactional
	public void testShouldScheduleCronAndProcess() throws Exception {
		int before = processor.getAll().size();
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Cron);
		dto.setCron("* * * * * *");
		Trigger trigger = null;
		try {
			trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		} catch (TriggerException e) {
		}
		Assert.assertNotNull(trigger.getScheduledAt());
		System.out.println("--------------------------------------");
		System.out.println(trigger.getScheduledAt());
		Thread.sleep(1000);
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		daoTrigger.flush();
	}

	@Test
	@Transactional
	public void testShouldScheduleEventAndProcess() throws Exception {
		int before = processor.getAll().size();
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Event);
		dto.setEvent(TriggerEventEnumDefault.Default);
		Trigger trigger = null;
		try {
			trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		} catch (TriggerException e) {
		}
		Assert.assertNull(trigger.getScheduledAt());
		publisher.publishEvent(new EventTriggerOnEvent(this, TriggerEventEnumDefault.Default));
		Assert.assertNotNull(trigger.getScheduledAt());
		System.out.println("--------------------------------------");
		System.out.println(trigger.getScheduledAt());
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		daoTrigger.flush();
	}

	@Test
	@Transactional
	public void testShouldScheduleEventAndProcessOnlyOnce() throws Exception {
		int before = processor.getAll().size();
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Event);
		dto.setEvent(TriggerEventEnumDefault.Default);
		Trigger trigger = null;
		try {
			trigger = soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		} catch (TriggerException e) {
		}
		Assert.assertNull(trigger.getScheduledAt());
		publisher.publishEvent(new EventTriggerOnEvent(this, TriggerEventEnumDefault.Default));
		Assert.assertNotNull(trigger.getScheduledAt());
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		daoTrigger.flush();
	}

	@Test
	@Transactional
	public void testShouldScheduleCronAndProcessTwice() throws Exception {
		int before = processor.getAll().size();
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Cron);
		dto.setCron("* * * * * *");
		try {
			soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		} catch (TriggerException e) {
		}
		Thread.sleep(1000);
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		Thread.sleep(1000);
		launcher.executeAll();
		Assert.assertEquals(before + 2, processor.getAll().size());
		daoTrigger.flush();
	}

	@Test
	@Transactional
	public void testShouldScheduleDate() throws Exception {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Date);
		dto.setDate(new Date());
		soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		daoTrigger.flush();
		Assert.assertEquals(dto.getDate(), daoTrigger.leastScheduleDate());
	}

	@Test
	@Transactional
	public void testShouldScheduleDateAndProcess() throws Exception {
		int before = processor.getAll().size();
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Date);
		dto.setDate(new Date());
		try {
			soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		} catch (TriggerException e) {
		}
		System.out.println("--------------------------------------");
		System.out.println(launcher.getFuture().getDelay(TimeUnit.SECONDS));
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		daoTrigger.flush();
	}

	@Test
	@Transactional
	public void testShouldScheduleDateAndProcessOnlyOnce() throws Exception {
		int before = processor.getAll().size();
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setWhen(DtoTriggerDefaultWhen.Date);
		dto.setDate(new Date());
		try {
			soaTrigger.saveOrUpdate(subject, dto, new OptionsList());
		} catch (TriggerException e) {
		}
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		launcher.executeAll();
		Assert.assertEquals(before + 1, processor.getAll().size());
		daoTrigger.flush();
	}

}
