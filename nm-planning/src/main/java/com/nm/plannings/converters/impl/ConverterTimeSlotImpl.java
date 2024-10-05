package com.nm.plannings.converters.impl;

import java.util.Collection;

import org.hibernate.cfg.NotYetImplementedException;

import com.nm.plannings.constants.SlotOptions;
import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.dtos.DtoTimeSlot;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotExceptionnal;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterTimeSlotImpl extends DtoConverterDefault<DtoTimeSlot, TimeSlot> {

	public Collection<Class<? extends DtoTimeSlot>> managed() {
		return ListUtils.all(DtoTimeSlot.class);
	}

	public Collection<Class<? extends TimeSlot>> managedEntity() {
		return ListUtils.all(TimeSlot.class);
	}

	public DtoTimeSlot toDto(DtoTimeSlot dto, TimeSlot entity, OptionsList options) throws DtoConvertException {
		try {
			DtoTimeSlot form = new DtoTimeSlot();
			form.setDateBeginPlan(entity.getBeginPlan());
			form.setDateEndPlan(entity.getEndPlan());
			form.setId(entity.getId());
			form.setTypeOfSlot(entity.getType());
			if (entity instanceof TimeSlotRecurrent) {
				TimeSlotRecurrent rec = (TimeSlotRecurrent) entity;
				form.setDateBeginHoraire(rec.getBeginHoraire());
				form.setDateEndHoraire(rec.getEndHoraire());
				form.addPlanningDays(rec.getDays());
				form.setType(SlotRepeatKind.Recurrent);
				form.setHasNoLimit(rec.isNoEndPlan());
				if (form.isHasNoLimit()) {
					form.setDateEndPlan(null);
				}
			} else if (entity instanceof TimeSlotExceptionnal) {
				TimeSlotExceptionnal exc = (TimeSlotExceptionnal) entity;
				form.setCause(exc.getCause());
				form.setType(SlotRepeatKind.Exceptionnal);
				form.setHasNoLimit(false);
			}
			if (options.contains(SlotOptions.AboutIdDto)) {
				form.setAboutId(entity.getPlanning().getAbout().getId());
			}
			return form;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public TimeSlot toEntity(DtoTimeSlot dto, OptionsList options) throws DtoConvertException {
		throw new NotYetImplementedException("Should not exists (see timeslots)");
	}

}
