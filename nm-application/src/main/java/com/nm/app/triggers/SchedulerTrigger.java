package com.nm.app.triggers;

import java.util.Collection;
import java.util.Date;

import org.springframework.util.Assert;

import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public abstract class SchedulerTrigger {

	public abstract boolean canSchedule(SchedulerTriggerContext context);

	public abstract boolean canExecute(Trigger context);

	public abstract void schedule(SchedulerTriggerContext cron);

	public final void execute(Trigger trigger) {
		try {
			Date now = new Date();
			trigger.setCountAttempt(trigger.getCountAttempt() + 1);
			//
			Collection<ProcessorTriggerSubject> processors = ApplicationUtils.getBeansCollection(ProcessorTriggerSubject.class);
			int i = 0;
			TriggerSubject s = trigger.getSubject();
			{
				for (ProcessorTriggerSubject p : processors) {
					if (p.accept(s)) {
						i++;
						p.process(s);
					}
				}
			}
			Assert.isTrue(i > 0);
			trigger.setCountExecution(trigger.getCountExecution() + 1);
			trigger.setLastExecution(now);
			onExecuteSuccess(trigger);
		} catch (Exception e) {
			e.printStackTrace();
			onExecuteFailed(trigger, e);
		}
	}

	protected abstract void onExecuteFailed(Trigger trigger, Exception e);

	protected abstract void onExecuteSuccess(Trigger trigger);
}
