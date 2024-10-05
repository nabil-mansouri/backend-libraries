package com.nm.app.currency;

import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaDevise {

	public CurrencyDto getDefault() throws NoDataFoundException;

	public void setDefault(CurrencyDto form);

}
