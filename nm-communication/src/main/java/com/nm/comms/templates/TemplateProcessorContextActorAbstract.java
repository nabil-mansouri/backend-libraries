package com.nm.comms.templates;


/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public abstract class TemplateProcessorContextActorAbstract implements TemplateProcessorContextActor {

	public int priority() {
		return 0;
	}

	public boolean hasDependencies() {
		return false;
	}

}
