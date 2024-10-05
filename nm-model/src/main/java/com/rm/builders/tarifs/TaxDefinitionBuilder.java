package com.rm.builders.tarifs;

import java.util.Collection;

import com.rm.contract.prices.constants.TaxeApplicability;
import com.rm.contract.prices.constants.TaxeEvents;
import com.rm.contract.prices.constants.TaxeType;
import com.rm.model.taxs.TaxDefinition;

/**
 * 
 * @author Nabil
 * 
 */
public class TaxDefinitionBuilder {
	public TaxDefinitionBuilder withName(String name) {
		taxDefinition.setName(name);
		return this;
	}

	public static TaxDefinitionBuilder get() {
		return new TaxDefinitionBuilder(new TaxDefinition());
	}

	private final TaxDefinition taxDefinition;

	private TaxDefinitionBuilder(TaxDefinition taxDefinition) {
		this.taxDefinition = taxDefinition;
	}

	public TaxDefinition build() {
		return taxDefinition;
	}

	public TaxDefinitionBuilder withApplicabilities(Collection<TaxeApplicability> applicabilities) {
		taxDefinition.setApplicabilities(applicabilities);
		return this;
	}

	public TaxDefinitionBuilder withApplicabilities(TaxeApplicability applicabilities) {
		taxDefinition.getApplicabilities().add(applicabilities);
		return this;
	}

	
	public TaxDefinitionBuilder withEvents(Collection<TaxeEvents> events) {
		taxDefinition.setEvents(events);
		return this;
	}

	public TaxDefinitionBuilder withEvents(TaxeEvents events) {
		taxDefinition.getEvents().add(events);
		return this;
	}
	
	public TaxDefinitionBuilder withDenominateur(double denominateur) {
		taxDefinition.setDenominateur(denominateur);
		return this;
	}

	public TaxDefinitionBuilder withId(Long id) {
		taxDefinition.setId(id);
		return this;
	}

	public TaxDefinitionBuilder withNominateur(double nominateur) {
		taxDefinition.setNominateur(nominateur);
		return this;
	}

	public TaxDefinitionBuilder withTaxeType(TaxeType taxeType) {
		taxDefinition.setTaxeType(taxeType);
		return this;
	}

}
