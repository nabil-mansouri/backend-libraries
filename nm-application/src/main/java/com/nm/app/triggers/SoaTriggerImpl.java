package com.nm.app.triggers;

import org.springframework.context.ApplicationEventPublisher;

import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaTriggerImpl implements SoaTrigger {
	private DaoTrigger daoTrigger;
	private ApplicationEventPublisher publish;
	private DtoConverterRegistry registry;

	public void setDaoTrigger(DaoTrigger daoTrigger) {
		this.daoTrigger = daoTrigger;
	}

	public void setPublish(ApplicationEventPublisher publish) {
		this.publish = publish;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Trigger saveOrUpdate(TriggerSubject subject, DtoTrigger dto, OptionsList options) throws TriggerException {
		try {
			Trigger trigger = registry.search(dto, Trigger.class).toEntity(dto, options);
			trigger.setSubject(subject);
			daoTrigger.saveOrUpdate(trigger);
			daoTrigger.flush();
			publish.publishEvent(new EventTriggerSchedule(this, trigger));
			return trigger;
		} catch (Exception e) {
			throw new TriggerException(e);
		}

	}

}
