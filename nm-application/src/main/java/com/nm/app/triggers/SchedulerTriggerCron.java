package com.nm.app.triggers;

import java.util.Date;

import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class SchedulerTriggerCron extends SchedulerTrigger {
	private DaoTrigger dao;

	public void setDao(DaoTrigger dao) {
		this.dao = dao;
	}

	@Override
	public boolean canSchedule(SchedulerTriggerContext context) {
		return context.getTrigger() instanceof TriggerCron;
	}

	@Override
	public boolean canExecute(Trigger context) {
		return context instanceof TriggerCron;
	}

	public void schedule(SchedulerTriggerContext context) {
		schedule(context.getTrigger());
	}

	protected void schedule(Trigger trigger) {
		TriggerCron cron = (TriggerCron) trigger;
		Date last = (cron.getLastExecution() != null) ? cron.getLastExecution() : new Date();
		CronSequenceGenerator gen = new CronSequenceGenerator(cron.getCron());
		Date next = gen.next(last);
		cron.setScheduledAt(next);
		cron.setCountAttempt(0);
		dao.saveOrUpdate(cron);
	}

	@Override
	protected void onExecuteFailed(Trigger trigger, Exception e) {

	}

	@Override
	protected void onExecuteSuccess(Trigger trigger) {
		schedule(trigger);
	}
}
