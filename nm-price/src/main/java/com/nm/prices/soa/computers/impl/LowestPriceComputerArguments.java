package com.nm.prices.soa.computers.impl;

import java.util.Collection;

import com.nm.prices.contract.PriceAdapterContract;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.DaoPriceValue;
import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dao.impl.PriceSubjectQueryBuilder;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.model.Price;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.model.values.PriceValue;
import com.nm.prices.soa.computers.PriceComputerArguments;

/***
 * 
 * @author nabilmansouri
 *
 */
public class LowestPriceComputerArguments implements PriceComputerArguments {
	private final DaoPrice daoPrice;
	private final DaoPriceValue daoPriceValue;
	private final Long subject;
	private final PriceAdapterContract adapter;
	private PriceFilterDto current;

	public LowestPriceComputerArguments(DaoPrice daoPrice, DaoPriceValue daoPriceValue, Long subject,
			PriceAdapterContract adapter) {
		this.daoPrice = daoPrice;
		this.daoPriceValue = daoPriceValue;
		this.subject = subject;
		this.adapter = adapter;
	}

	public void setCurrent(PriceFilterDto current) {
		this.current = current;
	}

	public Collection<Price> prices() {
		PriceSubjectQueryBuilder q0 = adapter.buildSubjectQuery().withRoot(true).withId(subject);
		PriceQueryBuilder q1 = adapter.buildQuery().withSubject(q0);
		q1.withOnlyCurrent().withFilter(current);
		return daoPrice.find(q1);
	}

	public boolean has(PriceSubject subject) {
		return subject.getRoot();
	}

	public Collection<PriceValue> values(Price price) {
		return daoPriceValue.find(adapter.buildValueQuery().withFilter(current).withPrice(price));
	}

}