package com.nm.geo;

import java.util.Collection;

import com.nm.geo.daos.QueryAddressBuilder;
import com.nm.geo.dtos.AddressDto;
import com.nm.geo.models.Address;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface SoaGeo {

	public Address saveOrUpdate(AddressDto dto, OptionsList options) throws GeoException;

	public void remove(QueryAddressBuilder query, OptionsList options);

	public Collection<AddressDto> fetch(QueryAddressBuilder query, OptionsList options) throws GeoException;
}
