package com.nm.geo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.geo.SoaGeo;
import com.nm.geo.SoaGeoImpl;
import com.nm.geo.constants.AddressType.AddressTypeDefault;
import com.nm.geo.constants.ModuleConfigKeyAddress;
import com.nm.geo.converters.AddressConverterImpl;
import com.nm.geo.daos.DaoAddress;
import com.nm.geo.daos.DaoAddressImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationGeo {
	public static final String MODULE_NAME = "geo";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(AddressTypeDefault.class);
		reg.put(ModuleConfigKeyAddress.class);
	}

	@Bean
	public AddressConverterImpl addressConverterImpl() {
		return new AddressConverterImpl();
	}

	@Bean
	public DaoAddress daoAddressImpl(DatabaseTemplateFactory fac) {
		DaoAddressImpl d = new DaoAddressImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public SoaGeo soaGeoImpl(DtoConverterRegistry dao, DaoAddress d) {
		SoaGeoImpl s = new SoaGeoImpl();
		s.setDao(d);
		s.setRegistry(dao);
		return s;
	}

}
