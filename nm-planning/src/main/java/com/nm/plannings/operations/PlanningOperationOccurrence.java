package com.nm.plannings.operations;

import java.util.Collection;

import org.springframework.util.Assert;

import com.nm.plannings.converters.EventDtoConverter;
import com.nm.plannings.converters.EventDtoConverterFactory;
import com.nm.plannings.converters.EventDtoConverterFactory.AllDaysStrategy;
import com.nm.plannings.dao.impl.TimeSlotQueryBuilder;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningQuery.OperationType;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.plannings.model.TimeSlot;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class PlanningOperationOccurrence implements PlanningOperation {

	public DtoPlanningResult operation(DtoPlanningQuery queryDto, OptionsList options) throws Exception {
		Assert.notNull(queryDto.getSlotFilter());
		Assert.notNull(queryDto.getFrom());
		Assert.notNull(queryDto.getTo());
		Collection<TimeSlot> slots = AbstractGenericDao.get(TimeSlot.class)
				.find(TimeSlotQueryBuilder.get().withFilter(queryDto.getSlotFilter()));
		//
		DtoSlotOccurrenceGroup events = new DtoSlotOccurrenceGroup();
		for (TimeSlot s : slots) {
			EventDtoConverter converter = EventDtoConverterFactory.get(s, AllDaysStrategy.Keep);
			events.addAll(converter.toEvents(s, queryDto.getFrom(), queryDto.getTo()));
		}
		//
		DtoPlanningResult result = new DtoPlanningResult();
		result.setEvents(events);
		result.setAssertion(!events.isEmpty());
		return result;
	}

	public boolean accept(DtoPlanningQuery query) {
		return query.getType().equals(OperationType.ComputeOccurrence);
	}

}
