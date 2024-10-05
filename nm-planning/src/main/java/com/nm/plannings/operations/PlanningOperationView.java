package com.nm.plannings.operations;

import java.util.Collection;

import com.nm.plannings.converters.EventDtoConverter;
import com.nm.plannings.converters.EventDtoConverterFactory;
import com.nm.plannings.converters.EventDtoConverterFactory.AllDaysStrategy;
import com.nm.plannings.dao.impl.TimeSlotQueryBuilder;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningQuery.OperationType;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.rules.EventRulesContext;
import com.nm.plannings.rules.EventRulesProcessor;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class PlanningOperationView implements PlanningOperation {
	private EventRulesProcessor rulesProcessor;

	public void setRulesProcessor(EventRulesProcessor rulesProcessor) {
		this.rulesProcessor = rulesProcessor;
	}

	public DtoPlanningResult operation(DtoPlanningQuery queryDto, OptionsList options) throws Exception {
		if (queryDto.getType().equals(OperationType.AssertSlotType)) {
			queryDto.setSubstract(true);
		}
		//
		TimeSlotQueryBuilder query = TimeSlotQueryBuilder.get()
				.withIntersectInterval(queryDto.getFrom(), queryDto.getTo())
				.withPlanning(queryDto.getPlanning().getId());
		Collection<TimeSlot> slots = AbstractGenericDao.get(TimeSlot.class).find(query);
		//
		DtoSlotOccurrenceGroup events = new DtoSlotOccurrenceGroup();
		for (TimeSlot s : slots) {
			EventDtoConverter converter = EventDtoConverterFactory.get(s, AllDaysStrategy.Split);
			events.addAll(converter.toEvents(s, queryDto.getFrom(), queryDto.getTo()));
		}
		//
		EventRulesContext context = new EventRulesContext();
		context.setFilter(queryDto.getFilter());
		context.getStrongers().addAll(queryDto.getStrongers());
		if (queryDto.isSubstract()) {
			rulesProcessor.processAndFilter(events, context);
		} else {
			rulesProcessor.process(events, context);
		}
		DtoPlanningResult result = new DtoPlanningResult();
		result.setEvents(events);
		result.setAssertion(!events.isEmpty());
		return result;
	}

	public boolean accept(DtoPlanningQuery query) {
		return query.getType().equals(OperationType.ComputeView)
				|| query.getType().equals(OperationType.AssertSlotType);
	}

}
