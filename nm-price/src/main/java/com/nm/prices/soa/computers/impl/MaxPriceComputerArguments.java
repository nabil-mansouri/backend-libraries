package com.nm.prices.soa.computers.impl;

import com.nm.prices.contract.PriceAdapterContract;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.DaoPriceValue;
import com.nm.prices.model.subject.PriceSubject;

/***
 * 
 * @author nabilmansouri
 *
 */
final class MaxPriceComputerArguments extends LowestPriceComputerArguments {

	public MaxPriceComputerArguments(DaoPrice daoPrice, DaoPriceValue daoPriceValue, Long subject,
			PriceAdapterContract adapter) {
		super(daoPrice, daoPriceValue, subject, adapter);
	}

	public boolean has(PriceSubject subject) {
		return true;
	}

}