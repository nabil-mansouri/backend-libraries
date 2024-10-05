package com.nm.app.triggers;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface ProcessorTriggerSubject {
	public boolean accept(TriggerSubject subject);

	public void process(TriggerSubject subject) throws Exception;
}
