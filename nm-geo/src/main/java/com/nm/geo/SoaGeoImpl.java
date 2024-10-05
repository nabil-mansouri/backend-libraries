package com.nm.geo;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.geo.daos.DaoAddress;
import com.nm.geo.daos.QueryAddressBuilder;
import com.nm.geo.dtos.AddressDto;
import com.nm.geo.dtos.AddressDtoImpl;
import com.nm.geo.models.Address;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class SoaGeoImpl implements SoaGeo {
	private DtoConverterRegistry registry;
	private DaoAddress dao;

	public void setDao(DaoAddress dao) {
		this.dao = dao;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	@Override
	public void remove(QueryAddressBuilder query, OptionsList options) {
		Collection<Address> cms = dao.find(query);
		dao.deleteAll(cms);
	}

	public Collection<AddressDto> fetch(QueryAddressBuilder query, OptionsList options) throws GeoException {
		try {
			Collection<Address> cms = dao.find(query);
			if (cms.isEmpty()) {
				return Lists.newArrayList();
			} else {
				Class<AddressDto> cmsClazz = options.dtoForModel(cms.iterator().next().getClass(), AddressDtoImpl.class);
				DtoConverter<AddressDto, Address> converter = registry.search(cmsClazz, Address.class);
				return converter.toDto(cms, options);
			}
		} catch (Exception e) {
			throw new GeoException(e);
		}
	}

	public Address saveOrUpdate(AddressDto dto, OptionsList options) throws GeoException {
		try {
			DtoConverter<AddressDto, Address> converter = registry.search(dto, Address.class);
			Address entity = converter.toEntity(dto, options);
			dao.saveOrUpdate(entity);
			dto.setId(entity.getId());
			return entity;
		} catch (Exception e) {
			throw new GeoException(e);
		}
	}

}
