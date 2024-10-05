package com.nm.plannings.configurations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.plannings.constants.SlotTypeDefault;
import com.nm.plannings.converters.impl.ConverterPlanningImpl;
import com.nm.plannings.converters.impl.ConverterTimeSlotImpl;
import com.nm.plannings.converters.impl.ConverterTimeSlotsImpl;
import com.nm.plannings.dao.DaoTimeSlot;
import com.nm.plannings.dao.impl.DaoTimeSlotImpl;
import com.nm.plannings.operations.PlanningOperation;
import com.nm.plannings.operations.PlanningOperationDelete;
import com.nm.plannings.operations.PlanningOperationOccurrence;
import com.nm.plannings.operations.PlanningOperationEdit;
import com.nm.plannings.operations.PlanningOperationView;
import com.nm.plannings.rules.EventRulesProcessor;
import com.nm.plannings.soa.SoaPlanning;
import com.nm.plannings.soa.SoaPlanningImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
@Configuration
public class ConfigurationPlanning {
	public static final String MODULE_NAME = "planning";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(SlotTypeDefault.class);
	}

	@Bean
	public DaoTimeSlot daoTimeSlotImpl(DatabaseTemplateFactory fac) {
		com.nm.plannings.dao.impl.DaoTimeSlotImpl d = new DaoTimeSlotImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public ConverterPlanningImpl converterPlanningImpl() {
		return new ConverterPlanningImpl();
	}

	@Bean
	public ConverterTimeSlotImpl converterTimeSlotImpl() {
		return new ConverterTimeSlotImpl();
	}

	@Bean
	public ConverterTimeSlotsImpl converterTimeSlotsImpl() {
		return new ConverterTimeSlotsImpl();
	}

	@Bean
	public SoaPlanning soaPlanningImpl(DtoConverterRegistry dto, Collection<PlanningOperation> prations) {
		SoaPlanningImpl p = new SoaPlanningImpl();
		p.setRegistry(dto);
		p.setOperations(prations);
		return p;
	}

	@Bean
	public EventRulesProcessor eventRulesProcessor() throws Exception {
		return new EventRulesProcessor();
	}

	@Bean
	public PlanningOperationDelete PlanningOperationDelete() {
		PlanningOperationDelete p = new PlanningOperationDelete();
		p.setEdit(planningOperationEdit());
		return p;
	}

	@Bean
	public PlanningOperationEdit planningOperationEdit() {
		PlanningOperationEdit p = new PlanningOperationEdit();
		return p;
	}

	@Bean
	public PlanningOperationOccurrence planningOperationDetails() {
		PlanningOperationOccurrence p = new PlanningOperationOccurrence();
		return p;
	}

	@Bean
	public PlanningOperationView planningOperationView() throws Exception {
		PlanningOperationView p = new PlanningOperationView();
		p.setRulesProcessor(eventRulesProcessor());
		return p;
	}

}
