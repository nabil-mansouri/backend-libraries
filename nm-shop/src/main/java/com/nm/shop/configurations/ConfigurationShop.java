package com.nm.shop.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.plannings.soa.PlanningAdapter;
import com.nm.plannings.soa.SoaPlanning;
import com.nm.shop.bridge.PlaningAdapterShop;
import com.nm.shop.bridge.ShopSlotType;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.dao.DaoShopConfiguration;
import com.nm.shop.dao.DaoShopPlanningable;
import com.nm.shop.dao.impl.DaoShopConfigurationImpl;
import com.nm.shop.dao.impl.DaoShopImpl;
import com.nm.shop.dao.impl.DaoShopPlanningImpl;
import com.nm.shop.soa.SoaShop;
import com.nm.shop.soa.checker.ShopChecker;
import com.nm.shop.soa.checker.ShopStateCheckerImpl;
import com.nm.shop.soa.converters.ShopConverter;
import com.nm.shop.soa.converters.ShopConverterImpl;
import com.nm.shop.soa.impl.SoaShopConfiguration;
import com.nm.shop.soa.impl.SoaShopConfigurationImpl;
import com.nm.shop.soa.impl.SoaShopImpl;
import com.nm.shop.tests.ShopDataFactory;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationShop {
	public static final String MODULE_NAME = "shop";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(ShopSlotType.class);
	}

	@Bean
	public PlanningAdapter planingAdapterShop(DaoShop dao) {
		PlaningAdapterShop p = new PlaningAdapterShop();
		p.setDaoShop(dao);
		return p;
	}

	@Bean
	public DaoShopConfiguration daoShopConfigurationImpl(DatabaseTemplateFactory fac) {
		DaoShopConfigurationImpl p = new DaoShopConfigurationImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public DaoShop daoShopImpl(DatabaseTemplateFactory fac) {
		DaoShopImpl p = new DaoShopImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public DaoShopPlanningable daoShopPlanningImpl(DatabaseTemplateFactory fac) {
		DaoShopPlanningImpl p = new DaoShopPlanningImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public SoaShopConfiguration soaShopConfigurationImpl(DaoShopConfiguration d) {
		SoaShopConfigurationImpl p = new SoaShopConfigurationImpl();
		p.setDaoShopConfiguration(d);
		return p;
	}

	@Bean
	public ShopChecker shopStateCheckerImpl(SoaPlanning soaP, SoaShopConfiguration conf) {
		ShopStateCheckerImpl p = new ShopStateCheckerImpl();
		p.setSoaPlanning(soaP);
		p.setSoaShopConfiguration(conf);
		return p;
	}

	@Bean
	public SoaShop soaShopImpl(DaoShop soaP, ShopConverter conf) {
		SoaShopImpl p = new SoaShopImpl();
		p.setDaoRestaurant(soaP);
		p.setRestaurantConverter(conf);
		return p;
	}

	@Bean
	public ShopDataFactory shopDataFactory(DaoShop soaP, SoaShop soa) {
		ShopDataFactory p = new ShopDataFactory();
		p.setDaoShop(soaP);
		p.setSoaShop(soa);
		return p;
	}

	@Bean
	public ShopConverter shopConverterImpl(ShopChecker check, DaoShop dao, DtoConverterRegistry reg) {
		ShopConverterImpl p = new ShopConverterImpl();
		p.setDaoRestaurant(dao);
		p.setRegistry(reg);
		p.setRestaurantChecker(check);
		return p;
	}
}
