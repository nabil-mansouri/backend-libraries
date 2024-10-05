package com.nm.prices.soa.computers.impl;

import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.dtos.forms.PriceComputerResult;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.utils.hibernate.NoDataFoundException;

/***
 * 
 * @author nabilmansouri
 *
 */
public interface PriceComputerFactory {

	PriceComputerResult buildLowestPrice(PriceSubject subject);

	PriceComputerResult buildLowestPrice(PriceSubject subject, PriceFilterDto filter);

	PriceComputerResult buildCurrentPrice(PriceSubject subject, PriceFilterDto filter) throws NoDataFoundException;

	PriceComputerResult buildMaxPrice(PriceSubject subject);

	PriceComputerResult buildMaxPrice(PriceSubject subject, PriceFilterDto filter);

}