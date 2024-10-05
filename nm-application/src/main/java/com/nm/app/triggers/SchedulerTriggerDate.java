package com.nm.app.triggers;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */ 
public class SchedulerTriggerDate extends SchedulerTrigger {
	@Override
	public boolean canSchedule(SchedulerTriggerContext context) {
		return context.getTrigger() instanceof TriggerDate;
	}

	@Override
	public boolean canExecute(Trigger context) {
		return context instanceof TriggerDate;
	}

	public void schedule(SchedulerTriggerContext trigger) {
		//
	}

	@Override
	protected void onExecuteSuccess(Trigger trigger) {

	}

	@Override
	protected void onExecuteFailed(Trigger trigger, Exception e) {
	}
}
