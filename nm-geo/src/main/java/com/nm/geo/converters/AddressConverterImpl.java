package com.nm.geo.converters;

import java.math.BigInteger;

import org.json.JSONObject;

import com.google.common.base.Strings;
import com.nm.geo.dtos.AddressComponentsDtoImpl;
import com.nm.geo.dtos.AddressDtoImpl;
import com.nm.geo.models.Address;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.node.DtoNode;
import com.nm.utils.node.ModelNode;

/**
 * 
 * @author nabilmansouri
 *
 */
public class AddressConverterImpl extends DtoConverterDefault<AddressDtoImpl, Address> {

	public AddressDtoImpl toDto(AddressDtoImpl bean, Address address, OptionsList options) throws DtoConvertException {
		bean.setComplement(address.getComplement());
		{
			AddressComponentsDtoImpl component = new AddressComponentsDtoImpl();
			component.setCountry(address.getCountry());
			component.setLatitude(address.getLatitude());
			component.setLocality(address.getLocality());
			component.setLongitude(address.getLongitude());
			component.setPostal(address.getPostal());
			component.setStreet(address.getStreet());
			bean.setComponents(component);
		}
		bean.setCreated(address.getCreated());
		if (!Strings.isNullOrEmpty((address.getDetails()))) {
			bean.setDetails(new JSONObject(address.getDetails()));
		}
		bean.setGeocode(address.getGeocode());
		bean.setId(address.getId());
		bean.setName(address.getName());
		bean.setAbout(new DtoNode(address.getAbout() != null ? address.getAbout().getId() : null));
		return bean;
	}

	public Address toEntity(AddressDtoImpl bean, OptionsList options) throws DtoConvertException {
		IGenericDao<Address, Long> dao = AbstractGenericDao.get(Address.class);
		try {
			Address address = new Address();
			if (bean.getId() != null) {
				address = dao.get(bean.getId());
			}
			return toEntity(address, bean, options);
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public Address toEntity(Address address, AddressDtoImpl bean, OptionsList options) {
		address.setComplement(bean.getComplement());
		address.setCountry(bean.getComponents().getCountry());
		address.setDetails(bean.getDetails().toString());
		address.setGeocode(bean.getGeocode());
		address.setId(bean.getId());
		address.setLatitude(bean.getComponents().getLatitude());
		address.setLocality(bean.getComponents().getLocality());
		address.setLongitude(bean.getComponents().getLongitude());
		address.setName(bean.getName());
		address.setPostal(bean.getComponents().getPostal());
		address.setStreet(bean.getComponents().getStreet());
		//
		IGenericDao<ModelNode, BigInteger> dao = AbstractGenericDao.get(ModelNode.class, BigInteger.class);
		address.setAbout((bean.getAbout().getUuid() != null) ? dao.load(bean.getAbout().getUuid()) : null);
		return address;
	}

}
