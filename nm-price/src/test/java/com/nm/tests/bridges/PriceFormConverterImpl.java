package com.nm.tests.bridges;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.prices.contract.PriceFormConverterDefault;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.prices.dtos.forms.PriceFormFilterDto;
import com.nm.prices.model.Price;
import com.nm.prices.model.PriceComposed;
import com.nm.prices.model.filter.PriceFilter;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.model.subject.PriceSubjectDefault;
import com.nm.prices.model.values.PriceValueSimple;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PriceFormConverterImpl extends PriceFormConverterDefault {

	public PriceFormConverterImpl() {
	}

	@Override
	protected PriceFormDto innerToDto(Price price, OptionsList options) throws NoDataFoundException {
		PriceFormDto form = new PriceFormDto(new PriceFormFilterDto() {
			private static final long serialVersionUID = 1L;
		}) {

			private static final long serialVersionUID = 1L;
		};
		return form;
	}

	@Override
	protected PriceFormDto innerToDto(PriceFormDto f, OptionsList options) throws NoDataFoundException {
		if (options.contains(PriceFormOptions.Subject)) {

		}
		return f;
	}

	@Override
	protected PriceFormDto innerToDtoChildren(Price child, PriceFormDto f, OptionsList options)
			throws NoDataFoundException {
		return f;
	}

	@Override
	protected PriceFormFilterDto innerToDtoFilters(PriceFormFilterDto filter, Collection<PriceFilter> filters,
			OptionsList options) throws NoDataFoundException {
		return filter;
	}

	@Override
	protected PriceFormDto innerToEntityValues(PriceComposed price, PriceFormDto f, OptionsList options)
			throws NoDataFoundException {
		PriceSubjectDefault subject = new PriceSubjectDefault();
		price.setSubject(subject);
		price.getValues().clear();
		PriceValueSimple value = new PriceValueSimple();
		value.setValue(11d);
		price.add(value);
		return f;
	}

	@Override
	protected PriceFormDto innerToDtoSubject(PriceFormDto f, PriceSubject subject, OptionsList options)
			throws NoDataFoundException {
		return f;
	}

	@Override
	protected Collection<PriceFilter> innerToEntityFilter(PriceFormFilterDto f, OptionsList options)
			throws NoDataFoundException {
		return new ArrayList<PriceFilter>();
	}
}
