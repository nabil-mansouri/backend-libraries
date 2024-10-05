package com.nm.app.triggers;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationListener;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class SchedulerTriggerEvent extends SchedulerTrigger implements ApplicationListener<EventTriggerOnEvent> {
	private SchedulerTriggerLauncher launcher;

	public void setLauncher(SchedulerTriggerLauncher launcher) {
		this.launcher = launcher;
	}

	@Override
	public boolean canSchedule(SchedulerTriggerContext context) {
		return context.getTrigger() instanceof TriggerEvent && context.getEvent() instanceof EventTriggerOnEvent;
	}

	@Override
	public boolean canExecute(Trigger context) {
		return context instanceof TriggerEvent;
	}

	public void schedule(SchedulerTriggerContext context) {
		TriggerEvent cron = (TriggerEvent) context.getTrigger();
		cron.setScheduledAt(new Date());
	}

	@Override
	protected void onExecuteFailed(Trigger trigger, Exception e) {

	}

	@Override
	protected void onExecuteSuccess(Trigger trigger) {

	}

	public void onApplicationEvent(EventTriggerOnEvent event) {
		QueryBuilderTrigger query = QueryBuilderTrigger.getEvent().withEvent(event.getEvent());
		Collection<TriggerEvent> triggers = AbstractGenericDao.get(TriggerEvent.class).find(query);
		for (TriggerEvent t : triggers) {
			t.setScheduledAt(new Date());
		}
		launcher.createOrUpdateJob();
	}
}
