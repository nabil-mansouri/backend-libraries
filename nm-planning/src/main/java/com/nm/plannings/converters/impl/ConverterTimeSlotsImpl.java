package com.nm.plannings.converters.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.cfg.NotYetImplementedException;

import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.PlanningDays;
import com.nm.plannings.dtos.DtoTimeSlot;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotExceptionnal;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.plannings.model.TimeSlots;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class ConverterTimeSlotsImpl extends DtoConverterDefault<DtoTimeSlot, TimeSlots> {

	public Collection<Class<? extends DtoTimeSlot>> managed() {
		return ListUtils.all(DtoTimeSlot.class);
	}

	public Collection<Class<? extends TimeSlots>> managedEntity() {
		return ListUtils.all(TimeSlots.class);
	}

	public DtoTimeSlot toDto(DtoTimeSlot dto, TimeSlots entity, OptionsList options) throws DtoConvertException {
		throw new NotYetImplementedException("Should not exists");
	}

	public TimeSlots toEntity(DtoTimeSlot dto, OptionsList options) throws DtoConvertException {
		try {
			TimeSlots slots = new TimeSlots();
			//
			if (dto.getType().equals(SlotRepeatKind.Exceptionnal)) {
				TimeSlot slot = new TimeSlotExceptionnal().setCause(dto.getCause());
				slot.setBeginPlan(dto.getDateBeginPlan()).setEndPlan(dto.getDateEndPlan());
				slot.setType(dto.getTypeOfSlot()).setId(dto.getId());
				// MUST BE AFTER
				slots.add(slot);
			} else {
				if (dto.getPlanningDays().contains(PlanningDays.AllDays)) {
					TimeSlotRecurrent slot = new TimeSlotRecurrent();
					slot.setBeginHoraire(dto.getDateBeginHoraire()).setEndHoraire(dto.getDateEndHoraire());
					slot.setDays(PlanningDays.AllDays).setId(dto.getId()).setType(dto.getTypeOfSlot());
					slot.setBeginPlan(dto.getDateBeginPlan()).setEndPlan(dto.getDateEndPlan());
					// MUST BE AFTER
					slot.withNoEndPlan(dto.isHasNoLimit());
					slots.add(slot);
				} else {
					// Unicity
					Set<PlanningDays> days = new HashSet<PlanningDays>(dto.getPlanningDays());
					for (PlanningDays day : days) {
						TimeSlotRecurrent slot = new TimeSlotRecurrent();
						slot.setBeginHoraire(dto.getDateBeginHoraire()).setEndHoraire(dto.getDateEndHoraire());
						slot.setDays(day).setId(dto.getId()).setType(dto.getTypeOfSlot());
						slot.setBeginPlan(dto.getDateBeginPlan()).setEndPlan(dto.getDateEndPlan());
						// MUST BE AFTER
						slot.withNoEndPlan(dto.isHasNoLimit());
						slots.add(slot);
					}

				}
			}
			//
			return slots;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
