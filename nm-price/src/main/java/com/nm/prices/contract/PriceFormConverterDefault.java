package com.nm.prices.contract;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nm.app.currency.SoaDevise;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dtos.constants.PriceFilterEnum.PriceFilterEnumDefault;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.prices.dtos.forms.PriceFormFilterDto;
import com.nm.prices.model.Price;
import com.nm.prices.model.PriceComposed;
import com.nm.prices.model.filter.PriceFilter;
import com.nm.prices.model.filter.PriceFilterDate;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public abstract class PriceFormConverterDefault implements PriceFormConverter {
	protected static Log log = LogFactory.getLog(PriceFormConverter.class);
	//
	@Autowired
	private DaoPrice daoPrice;
	@Autowired
	private SoaDevise soaDevise;

	protected Collection<PriceFilter> _toEntityFilter(PriceFormFilterDto form, OptionsList options)
			throws NoDataFoundException {
		Collection<PriceFilter> filters = new ArrayList<PriceFilter>();
		//
		if (form.isHasFrom()) {
			PriceFilterDate filter = new PriceFilterDate();
			filter.setType(PriceFilterEnumDefault.LimitInTimeFrom);
			filter.setDate(form.getFrom());
			filters.add(filter);
		}
		if (form.isHasTo()) {
			PriceFilterDate filter = new PriceFilterDate();
			filter.setType(PriceFilterEnumDefault.LimitInTimeTo);
			filter.setDate(form.getTo());
			filters.add(filter);
		}
		filters.addAll(innerToEntityFilter(form, options));
		return filters;
	}

	protected abstract PriceFormDto innerToDto(Price price, OptionsList options) throws NoDataFoundException;

	protected abstract PriceFormDto innerToDto(PriceFormDto form, OptionsList options) throws NoDataFoundException;

	protected abstract PriceFormDto innerToDtoChildren(Price child, PriceFormDto form, OptionsList options)
			throws NoDataFoundException;

	protected abstract PriceFormFilterDto innerToDtoFilters(PriceFormFilterDto filter, Collection<PriceFilter> filters,
			OptionsList options) throws NoDataFoundException;

	protected abstract PriceFormDto innerToDtoSubject(PriceFormDto form, PriceSubject subject, OptionsList options)
			throws NoDataFoundException;

	protected abstract Collection<PriceFilter> innerToEntityFilter(PriceFormFilterDto form, OptionsList options)
			throws NoDataFoundException;

	protected abstract PriceFormDto innerToEntityValues(PriceComposed price, PriceFormDto form, OptionsList options)
			throws NoDataFoundException;

	public PriceFormDto toDto(Price price, OptionsList options) throws NoDataFoundException {
		PriceFormDto form = innerToDto(price, options);
		form.setId(price.getId());
		try {
			form.setCurrency(soaDevise.getDefault());
		} catch (NoDataFoundException e) {
			form.setNoCurrency(true);
		}
		PriceComposed composed = (PriceComposed) price;
		//
		form.getFilter().setHasFrom(false);
		form.getFilter().setHasTo(false);
		for (PriceFilter filter : composed.getFilter().values()) {
			if (filter.getType().equals(PriceFilterEnumDefault.LimitInTimeFrom)) {
				form.getFilter().setHasFrom(true);
				PriceFilterDate fDate = (PriceFilterDate) filter;
				form.getFilter().setFrom(fDate.getDate());
			} else if (filter.getType().equals(PriceFilterEnumDefault.LimitInTimeTo)) {
				form.getFilter().setHasTo(true);
				PriceFilterDate fDate = (PriceFilterDate) filter;
				form.getFilter().setTo(fDate.getDate());
			}
		}
		form.setFilter(innerToDtoFilters(form.getFilter(), composed.getFilter().values(), options));
		//
		if (options.contains(PriceFormOptions.Subject)) {
			innerToDtoSubject(form, price.getSubject(), options);
		}
		for (Price child : composed.getChildren()) {
			innerToDtoChildren(child, form, options);
		}
		form.compute();
		return form;
	}

	public PriceFormDto toDto(PriceFormDto form, OptionsList options) throws NoDataFoundException {
		innerToDto(form, options);
		return form;
	}

	public Price toEntity(PriceFormDto form, OptionsList options) throws NoDataFoundException {
		PriceComposed price = new PriceComposed();
		price.setRoot(true);
		if (form.getId() != null) {
			price = (PriceComposed) daoPrice.get(form.getId());
		}
		//
		price.getFilter().clear();
		Collection<PriceFilter> filters = _toEntityFilter(form.getFilter(), options);
		for (PriceFilter filter : filters) {
			price.put(filter.getType(), filter);
		}
		price.getChildren().clear();
		innerToEntityValues(price, form, options);
		return price;
	}

}
