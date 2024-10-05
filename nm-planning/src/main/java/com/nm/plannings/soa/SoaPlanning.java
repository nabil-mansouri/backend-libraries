package com.nm.plannings.soa;

import java.util.Collection;

import com.nm.plannings.dao.impl.PlanningQueryBuilder;
import com.nm.plannings.dao.impl.TimeSlotQueryBuilder;
import com.nm.plannings.dtos.DtoPlanning;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.plannings.dtos.DtoTimeSlot;
import com.nm.plannings.model.Planning;
import com.nm.plannings.model.TimeSlot;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaPlanning {
	public Collection<DtoPlanning> fetch(PlanningQueryBuilder query, OptionsList options) throws PlanningException;

	public Collection<DtoTimeSlot> fetch(TimeSlotQueryBuilder query, OptionsList options) throws PlanningException;

	public Planning saveOrUpdate(DtoPlanning node, OptionsList options) throws PlanningException;

	public Collection<TimeSlot> saveOrUpdateSlot(DtoPlanning planning, DtoTimeSlot form, OptionsList options) throws PlanningException;

	public void remove(PlanningQueryBuilder query, OptionsList options) throws PlanningException;

	public Planning remove(DtoPlanning planning, OptionsList options) throws PlanningException;

	public TimeSlot removeSlot(DtoTimeSlot form, OptionsList options) throws PlanningException;

	public DtoPlanningResult operation(DtoPlanningQuery query, OptionsList options) throws PlanningException;

}
