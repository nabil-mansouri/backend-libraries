package com.nm.prices.dtos.builders;

import java.util.List;
import java.util.Map;

import com.nm.prices.dtos.constants.TaxeApplicability;
import com.nm.prices.dtos.constants.TaxeEvents;
import com.nm.prices.dtos.constants.TaxeType;
import com.nm.prices.dtos.forms.old.TaxDefFormBean;

/**
 * 
 * @author Nabil
 * 
 */
public class TaxDefFormBeanBuilder {

	public TaxDefFormBeanBuilder withName(String name) {
		bean.setName(name);
		return this;
	}

	public TaxDefFormBeanBuilder withEvents(Map<TaxeEvents,Boolean> events) {
		bean.setEvents(events);
		return this;
	}

	public TaxDefFormBeanBuilder withEvents(TaxeEvents events) {
		bean.getEvents().put(events,true);
		return this;
	}
	private TaxDefFormBean bean = new TaxDefFormBean();

	private TaxDefFormBeanBuilder() {

	}

	public static TaxDefFormBeanBuilder get() {
		return new TaxDefFormBeanBuilder();
	}

	public TaxDefFormBeanBuilder withId(Long id) {
		bean.setId(id);
		return this;
	}

	public TaxDefFormBeanBuilder withType(TaxeType type) {
		bean.setType(type);
		return this;
	}

	public TaxDefFormBeanBuilder withNominateur(double nominateur) {
		bean.setNominateur(nominateur);
		return this;
	}

	public TaxDefFormBeanBuilder withDenominateur(double denominateur) {
		bean.setDenominateur(denominateur);
		return this;
	}

	public TaxDefFormBeanBuilder withApplicabilities(List<TaxeApplicability> applicabilities) {
		bean.setApplicabilities(applicabilities);
		return this;
	}

	public TaxDefFormBeanBuilder withApplicabilities(TaxeApplicability app) {
		bean.getApplicabilities().add(app);
		return this;
	}

	public TaxDefFormBean build() {
		return bean;
	}
}
