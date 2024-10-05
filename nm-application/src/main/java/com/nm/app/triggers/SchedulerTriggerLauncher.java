package com.nm.app.triggers;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.scheduling.TaskScheduler;

import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.hibernate.impl.TransactionWrapper;
import com.nm.utils.hibernate.impl.TransactionWrapper.TransactionHandler;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class SchedulerTriggerLauncher implements ApplicationListener<ApplicationEvent> {
	private TaskScheduler scheduler;
	private DaoTrigger daoTrigger;
	private TransactionWrapper transaction;
	//
	private Date lastJobUpdate;
	private ScheduledFuture<?> future;

	public void setDaoTrigger(DaoTrigger daoTrigger) {
		this.daoTrigger = daoTrigger;
	}

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setTransaction(TransactionWrapper transaction) {
		this.transaction = transaction;
	}

	public void scheduleAll(SchedulerTriggerContext context) {
		Collection<SchedulerTrigger> schedulers = ApplicationUtils.getBeansCollection(SchedulerTrigger.class);
		for (SchedulerTrigger s : schedulers) {
			if (s.canSchedule(context)) {
				s.schedule(context);
			}
		}
		createOrUpdateJob();
	}

	public Collection<Trigger> findToExecute() {
		QueryBuilderTrigger query = QueryBuilderTrigger.get().withScheduleAtLe(new Date())
				.withScheduleAtGtLastExecution();
		return AbstractGenericDao.get(Trigger.class).find(query);
	}

	public Collection<Trigger> executeAll() {
		Collection<Trigger> triggers = findToExecute();
		Collection<SchedulerTrigger> schedulers = ApplicationUtils.getBeansCollection(SchedulerTrigger.class);
		for (Trigger t : triggers) {
			for (SchedulerTrigger s : schedulers) {
				if (s.canExecute(t)) {
					s.execute(t);
				}
			}
		}
		createOrUpdateJob();
		return triggers;
	}

	public synchronized void cancelJob() {
		if (future != null) {
			future.cancel(false);
		}
		future = null;
	}

	public synchronized void createOrUpdateJob() {
		lastJobUpdate = new Date();
		Date least = transaction.requireIfNotExists(new TransactionHandler<Date>() {

			public Date process() {
				return daoTrigger.leastScheduleDate();
			}
		});
		if (least != null) {
			cancelJob();
			future = scheduler.schedule(new Runnable() {

				public void run() {
					transaction.requireIfNotExists(new TransactionHandler<Collection<Trigger>>() {

						public Collection<Trigger> process() {
							return executeAll();
						}

					});
				}
			}, least);
		}
	}

	public Date getLastJobUpdate() {
		return lastJobUpdate;
	}

	public ScheduledFuture<?> getFuture() {
		return future;
	}

	public void onApplicationEvent(ApplicationEvent arg0) {
		if (arg0 instanceof EventTriggerSchedule) {
			scheduleAll(new SchedulerTriggerContext(((EventTriggerSchedule) arg0).getTrigger(), arg0));
		} else if (arg0 instanceof ContextRefreshedEvent) {
			createOrUpdateJob();
		} else if (arg0 instanceof ContextStartedEvent) {
			createOrUpdateJob();
		}
	}
}
