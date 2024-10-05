package com.nm.app.triggers;

import java.util.Collection;
import java.util.Date;

import com.nm.app.triggers.DtoTriggerDefault.DtoTriggerDefaultWhen;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class ConverterTriggerDefault extends DtoConverterDefault<DtoTriggerDefault, Trigger> {

	public Collection<Class<? extends Trigger>> managedEntity() {
		return ListUtils.all(Trigger.class, TriggerCron.class, TriggerDate.class);
	}

	public DtoTriggerDefault toDto(Trigger entity, OptionsList options) {
		DtoTriggerDefault dto = new DtoTriggerDefault();
		dto.setId(entity.getId());
		if (entity instanceof TriggerCron) {
			dto.setCron(((TriggerCron) entity).getCron());
			dto.setWhen(DtoTriggerDefaultWhen.Cron);
		} else if (entity instanceof TriggerDate) {
			dto.setDate(entity.getScheduledAt());
			dto.setWhen(DtoTriggerDefaultWhen.Date);
		} else if (entity instanceof TriggerEvent) {
			dto.setEvent(((TriggerEvent) entity).getEvent());
			dto.setWhen(DtoTriggerDefaultWhen.Event);
		}
		return dto;
	}

	public DtoTriggerDefault toDto(DtoTriggerDefault dto, Trigger entity, OptionsList options)
			throws DtoConvertException {
		return toDto(entity, options);
	}

	public Trigger toEntity(DtoTriggerDefault dto, OptionsList options) throws DtoConvertException {
		try {
			switch (dto.getWhen()) {
			case Cron: {
				TriggerCron any = new TriggerCron();
				any.setCron(dto.getCron());
				return any;
			}
			case Date: {
				TriggerDate any = new TriggerDate();
				any.setScheduledAt(dto.getDate());
				return any;
			}
			case Event: {
				TriggerEvent any = new TriggerEvent();
				any.setEvent(dto.getEvent());
				return any;
			}
			case Now:
			default: {
				TriggerDate any = new TriggerDate();
				any.setScheduledAt(new Date());
				return any;
			}

			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}
}
