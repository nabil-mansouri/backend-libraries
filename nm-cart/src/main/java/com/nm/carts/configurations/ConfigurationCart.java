package com.nm.carts.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.carts.constants.CartRowTypeDefault;
import com.nm.carts.contract.CartEventDefault;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.dtos.converters.CartConverterImpl;
import com.nm.carts.dtos.converters.CartOwnerConverterImpl;
import com.nm.carts.models.DaoCart;
import com.nm.carts.models.DaoCartImpl;
import com.nm.carts.models.DaoCartOwner;
import com.nm.carts.models.DaoCartOwnerImpl;
import com.nm.carts.soa.SoaCart;
import com.nm.carts.soa.SoaCartImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationCart {
	public static final String MODULE_NAME = "cart";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(CartRowTypeDefault.class);
		reg.put(CartEventDefault.class);
		reg.put(CartStateDefault.class);
	}

	@Bean
	public CartConverterImpl cartConverterImpl(DaoCart dao) {
		CartConverterImpl c = new CartConverterImpl();
		c.setDaoCart(dao);
		return c;
	}

	@Bean
	public CartOwnerConverterImpl cartOwnerConverterImpl(DaoCartOwner dao) {
		CartOwnerConverterImpl c = new CartOwnerConverterImpl();
		c.setDaoOwner(dao);
		return c;
	}

	@Bean
	public DaoCartImpl daoCartImpl(DatabaseTemplateFactory dao) {
		com.nm.carts.models.DaoCartImpl c = new DaoCartImpl();
		c.setHibernateTemplate(dao.hibernateResource(MODULE_NAME));
		return c;
	}

	@Bean
	public DaoCartOwnerImpl daoCartOwnerImpl(DatabaseTemplateFactory dao) {
		com.nm.carts.models.DaoCartOwnerImpl c = new DaoCartOwnerImpl();
		c.setHibernateTemplate(dao.hibernateResource(MODULE_NAME));
		return c;
	}

	@Bean
	public SoaCart soaCartImpl(DaoCart dao, DaoCartOwner own, DtoConverterRegistry reg) {
		SoaCartImpl c = new SoaCartImpl();
		c.setDaoCart(dao);
		c.setDaoOwner(own);
		c.setRegistry(reg);
		return c;
	}
}
