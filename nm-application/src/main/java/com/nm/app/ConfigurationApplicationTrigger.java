package com.nm.app;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.nm.app.triggers.ConverterTriggerDefault;
import com.nm.app.triggers.DaoTrigger;
import com.nm.app.triggers.DaoTriggerImpl;
import com.nm.app.triggers.SchedulerTrigger;
import com.nm.app.triggers.SchedulerTriggerCron;
import com.nm.app.triggers.SchedulerTriggerDate;
import com.nm.app.triggers.SchedulerTriggerEvent;
import com.nm.app.triggers.SchedulerTriggerLauncher;
import com.nm.app.triggers.SoaTrigger;
import com.nm.app.triggers.SoaTriggerImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.hibernate.impl.TransactionWrapper;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationApplicationTrigger {

	@Bean
	public ConverterTriggerDefault converterTriggerDefault() {
		return new ConverterTriggerDefault();
	}

	@Bean
	public DaoTriggerImpl daoTriggerImpl(DatabaseTemplateFactory fac) {
		DaoTriggerImpl p = new DaoTriggerImpl();
		p.setHibernateTemplate(fac.hibernateResource(ConfigurationApplication.MODULE_NAME));
		return p;
	}

	@Bean
	public SchedulerTrigger SchedulerTriggerEvent(SchedulerTriggerLauncher fac) {
		SchedulerTriggerEvent p = new SchedulerTriggerEvent();
		p.setLauncher(fac);
		return p;
	}

	@Bean
	public SchedulerTrigger schedulerTriggerCron(DaoTrigger fac) {
		SchedulerTriggerCron p = new SchedulerTriggerCron();
		p.setDao(fac);
		return p;
	}

	@Bean
	public SchedulerTrigger schedulerTriggerDate(DaoTrigger fac) {
		return new SchedulerTriggerDate();
	}

	@Bean
	public SchedulerTriggerLauncher schedulerTriggerLauncher(DaoTrigger fac, TransactionWrapper w, //
			TaskScheduler scheduler) {
		SchedulerTriggerLauncher p = new SchedulerTriggerLauncher();
		p.setDaoTrigger(fac);
		p.setScheduler(scheduler);
		p.setTransaction(w);
		return p;
	}

	@Bean
	public TaskScheduler threadPoolTaskScheduler(Environment env) {
		ThreadPoolTaskScheduler p = new ThreadPoolTaskScheduler();
		p.setPoolSize(env.getProperty("scheduler.pool", Integer.class, 5));
		return p;
	}

	@Bean
	public SoaTrigger soaTriggerImpl(DaoTrigger fac, ApplicationEventPublisher w, //
			DtoConverterRegistry r) {
		SoaTriggerImpl p = new SoaTriggerImpl();
		p.setDaoTrigger(fac);
		p.setPublish(w);
		p.setRegistry(r);
		return p;
	}
}
