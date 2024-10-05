package com.nm.plannings.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Sets;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningQuery.OperationType;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class PlanningOperationDelete implements PlanningOperation {
	private PlanningOperationEdit edit;

	public void setEdit(PlanningOperationEdit edit) {
		this.edit = edit;
	}

	public DtoPlanningResult operation(DtoPlanningQuery queryDto, OptionsList options) throws Exception {
		IGenericDao<TimeSlot, Long> daoTimeSlot = AbstractGenericDao.get(TimeSlot.class);
		DtoPlanningResult editResult = edit.operation(queryDto, options);
		//
		Collection<Long> idSlots = Sets.newHashSet();
		for (DtoSlotOccurrence ev : editResult.getEvents()) {
			idSlots.add(Long.valueOf(ev.getId()));
		}
		//
		Collection<TimeSlot> slots = daoTimeSlot.findByIds(idSlots);
		List<TimeSlot> res = new ArrayList<TimeSlot>();
		//
		for (TimeSlot first : slots) {
			TimeSlot second = first.clone();
			// MUST BE AFTER
			first.setEndPlan(queryDto.getFrom());
			if (first instanceof TimeSlotRecurrent) {
				((TimeSlotRecurrent) first).setNoEndPlan(false);
			}
			daoTimeSlot.saveOrUpdate(first);
			res.add(first);
			//
			if (second.getEndPlan().after(queryDto.getTo())) {
				second.setBeginPlan((queryDto.getTo()));
				daoTimeSlot.saveOrUpdate(second);
				res.add(second);
			}
		}
		daoTimeSlot.saveOrUpdate(res);
		//
		List<DtoSlotOccurrence> dtos = new ArrayList<DtoSlotOccurrence>();
		for (TimeSlot first : res) {
			dtos.add(new DtoSlotOccurrence().setId(first.getId().toString()));
		}
		DtoPlanningResult result = new DtoPlanningResult();
		result.setEvents(dtos);
		return result;
	}

	public boolean accept(DtoPlanningQuery query) {
		return query.getType().equals(OperationType.ComputeDelete);
	}

}
