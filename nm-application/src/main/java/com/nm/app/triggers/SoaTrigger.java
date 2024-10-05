package com.nm.app.triggers;

import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaTrigger {
	public Trigger saveOrUpdate(TriggerSubject subject, DtoTrigger dto, OptionsList options) throws TriggerException;
}
