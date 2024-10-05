package com.nm.prices.soa.computers.impl;

import com.nm.prices.contract.PriceAdapterContract;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.DaoPriceValue;
import com.nm.prices.dtos.constants.PriceSelector;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.dtos.forms.PriceComputerResult;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.soa.SoaPrice;
import com.nm.prices.soa.computers.PriceChooserStrategy;
import com.nm.prices.soa.computers.PriceComputer;
import com.nm.utils.hibernate.NoDataFoundException;

/***
 * 
 * @author nabilmansouri
 *
 */
public class PriceComputerFactoryImpl implements PriceComputerFactory {
	private PriceComputer computer;
	private DaoPrice daoPrice;
	private DaoPriceValue daoPriceValue;
	private SoaPrice soaPrice;
	private PriceAdapterContract priceAdapter;

	public void setComputer(PriceComputer computer) {
		this.computer = computer;
	}

	public void setDaoPrice(DaoPrice daoPrice) {
		this.daoPrice = daoPrice;
	}

	public void setDaoPriceValue(DaoPriceValue daoPriceValue) {
		this.daoPriceValue = daoPriceValue;
	}

	public void setPriceAdapter(PriceAdapterContract priceAdapter) {
		this.priceAdapter = priceAdapter;
	}

	public void setSoaPrice(SoaPrice soaPrice) {
		this.soaPrice = soaPrice;
	}

	public PriceComputerResult buildLowestPrice(PriceSubject subject, PriceFilterDto filter) {
		PriceChooserStrategy strategy = new PriceChooserStrategyMinimum();
		PriceComputerResult result = new PriceComputerResult();
		LowestPriceComputerArguments arg = new LowestPriceComputerArguments(daoPrice, daoPriceValue, subject.getId(),
				priceAdapter);
		return computer.compute(new LowestPriceComputerArgumentsIterator(arg, priceAdapter.buildComputer(), filter),
				strategy, result);
	}

	public PriceComputerResult buildCurrentPrice(PriceSubject subject, PriceFilterDto filter)
			throws NoDataFoundException {
		filter.assertRequired();
		PriceSelector priceSelector = soaPrice.getPriceSelector();
		switch (priceSelector) {
		case Lowest:
			return buildLowestPrice(subject, filter);
		case Greatest:
			return buildMaxPrice(subject, filter);
		default:
			return buildMaxPrice(subject, filter);
		}
	}

	public PriceComputerResult buildLowestPrice(PriceSubject subject) {
		PriceChooserStrategy strategy = new PriceChooserStrategyMinimum();
		PriceComputerResult result = new PriceComputerResult();
		LowestPriceComputerArguments arg = new LowestPriceComputerArguments(daoPrice, daoPriceValue, subject.getId(),
				priceAdapter);
		return computer.compute(new LowestPriceComputerArgumentsIterator(arg, priceAdapter.buildComputer()), strategy,
				result);
	}

	public PriceComputerResult buildMaxPrice(PriceSubject subject) {
		PriceChooserStrategy strategy = new PriceChooserStrategyMaximum();
		PriceComputerResult result = new PriceComputerResult();
		MaxPriceComputerArguments arg = new MaxPriceComputerArguments(daoPrice, daoPriceValue, subject.getId(),
				priceAdapter);
		return computer.compute(new MaxPriceComputerArgumentsIterator(arg, priceAdapter.buildComputer()), strategy,
				result);
	}

	public PriceComputerResult buildMaxPrice(PriceSubject subject, PriceFilterDto filter) {
		PriceChooserStrategy strategy = new PriceChooserStrategyMaximum();
		PriceComputerResult result = new PriceComputerResult();
		MaxPriceComputerArguments arg = new MaxPriceComputerArguments(daoPrice, daoPriceValue, subject.getId(),
				priceAdapter);
		return computer.compute(new MaxPriceComputerArgumentsIterator(arg, priceAdapter.buildComputer(), filter),
				strategy, result);
	}
}
