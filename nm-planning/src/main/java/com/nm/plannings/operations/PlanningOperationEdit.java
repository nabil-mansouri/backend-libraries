package com.nm.plannings.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.converters.EventDtoConverter;
import com.nm.plannings.converters.EventDtoConverterFactory;
import com.nm.plannings.converters.EventDtoConverterFactory.AllDaysStrategy;
import com.nm.plannings.dao.impl.TimeSlotQueryBuilder;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningQuery.OperationType;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.plannings.model.Planning;
import com.nm.plannings.model.TimeSlot;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class PlanningOperationEdit implements PlanningOperation {

	public DtoPlanningResult operation(DtoPlanningQuery queryDto, OptionsList options) throws Exception {
		IGenericDao<Planning, Long> daoPlanning = AbstractGenericDao.get(Planning.class);
		IGenericDao<TimeSlot, Long> daoTimeSlot = AbstractGenericDao.get(TimeSlot.class);
		Planning planning = daoPlanning.loadById(queryDto.getPlanning().getId());
		TimeSlotQueryBuilder query = TimeSlotQueryBuilder.get()
				.withIntersectInterval(queryDto.getFrom(), queryDto.getTo()).withPlanning(planning)
				.withType(SlotRepeatKind.Exceptionnal).withType(queryDto.getToEdit());
		Collection<TimeSlot> exceptionnalSlots = daoTimeSlot.find(query.getQuery());
		//
		query = TimeSlotQueryBuilder.get().withIntersectInterval(queryDto.getFrom(), queryDto.getTo())
				.withPlanning(planning).withType(SlotRepeatKind.Recurrent).withType(queryDto.getToEdit());
		Collection<TimeSlot> recurrentSlots = daoTimeSlot.find(query.getQuery());
		//
		List<DtoSlotOccurrence> response = new ArrayList<DtoSlotOccurrence>();
		EventDtoConverter converter = EventDtoConverterFactory.getExceptionnal();
		for (TimeSlot exc : exceptionnalSlots) {
			response.addAll(converter.toEvents(exc, queryDto.getFrom(), queryDto.getTo()));
		}
		//
		converter = EventDtoConverterFactory.getRecurrent(AllDaysStrategy.Split);
		for (TimeSlot rec : recurrentSlots) {
			response.addAll(converter.toEvents(rec, queryDto.getFrom(), queryDto.getTo()));
		}
		//
		DtoPlanningResult result = new DtoPlanningResult();
		result.setEvents(response);
		return result;
	}

	public boolean accept(DtoPlanningQuery query) {
		return query.getType().equals(OperationType.ComputeEdit);
	}

}
