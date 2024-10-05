package com.nm.app.locale;


import java.util.Collection;

import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaLocale {

	public OptionsList setLocaleIfEmpty(OptionsList options);

	public Collection<LocaleFormDto> getSelectedLocales();

	public LocaleFormDto getDefaultLocale() throws NoDataFoundException;

	public void setSelectedLocales(Collection<LocaleFormDto> form);

	public void setDefaultLocale(LocaleFormDto form);

}
