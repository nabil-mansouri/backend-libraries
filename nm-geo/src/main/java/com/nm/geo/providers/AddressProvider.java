package com.nm.geo.providers;

import com.nm.geo.dtos.AddressDto;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface AddressProvider {
	public AddressDto geocode(String address, OptionsList options) throws Exception;
}
