package com.nm.prices.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.config.SoaModuleConfig;
import com.nm.prices.contract.PriceAdapterContract;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.DaoPriceSubject;
import com.nm.prices.dao.DaoPriceValue;
import com.nm.prices.dao.impl.DaoPriceImpl;
import com.nm.prices.dao.impl.DaoPriceSubjectImpl;
import com.nm.prices.dao.impl.DaoPriceValueImpl;
import com.nm.prices.dtos.constants.PriceFilterEnum.PriceFilterEnumDefault;
import com.nm.prices.soa.SoaPrice;
import com.nm.prices.soa.computers.PriceComputer;
import com.nm.prices.soa.computers.impl.PriceComputerDefault;
import com.nm.prices.soa.computers.impl.PriceComputerFactory;
import com.nm.prices.soa.computers.impl.PriceComputerFactoryImpl;
import com.nm.prices.soa.impl.SoaPriceImpl;
import com.nm.prices.soa.synchronizer.PriceCacheBuilder;
import com.nm.prices.soa.synchronizer.impl.PriceCacheBuilderImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationPrice {
	public static final String MODULE_NAME = "price";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(PriceFilterEnumDefault.class);
	}

	@Bean
	public PriceCacheBuilder PriceCacheBuilderImpl() {
		return new PriceCacheBuilderImpl();
	}

	@Bean
	public DaoPrice daoPriceImpl(DatabaseTemplateFactory fac) {
		DaoPriceImpl d = new DaoPriceImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoPriceSubject daoPriceSubjectImpl(DatabaseTemplateFactory fac) {
		DaoPriceSubjectImpl d = new DaoPriceSubjectImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoPriceValue daoPriceValueImpl(DatabaseTemplateFactory fac) {
		DaoPriceValueImpl d = new DaoPriceValueImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public PriceComputerFactory priceComputerFactoryImpl(PriceComputer comp, DaoPrice daoP, DaoPriceValue daoPV,
			SoaPrice soa, PriceAdapterContract con) {
		PriceComputerFactoryImpl d = new PriceComputerFactoryImpl();
		d.setComputer(comp);
		d.setDaoPrice(daoP);
		d.setDaoPriceValue(daoPV);
		d.setPriceAdapter(con);
		d.setSoaPrice(soa);
		return d;
	}

	@Bean
	public PriceComputer priceComputerDefault() {
		return new PriceComputerDefault();
	}

	@Bean
	public SoaPrice soaPriceImpl(SoaModuleConfig soaModuleConfig, DaoPrice daoP, PriceAdapterContract con) {
		SoaPriceImpl d = new SoaPriceImpl();
		d.setSoaModuleConfig(soaModuleConfig);
		d.setDaoPrice(daoP);
		d.setPriceAdapter(con);
		return d;
	}

}
