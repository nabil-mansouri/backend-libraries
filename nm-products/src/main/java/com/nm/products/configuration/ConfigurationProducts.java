package com.nm.products.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.nm.app.currency.SoaDevise;
import com.nm.app.draft.SoaDraft;
import com.nm.app.locale.SoaLocale;
import com.nm.products.converter.ProductDefinitionFormConverter;
import com.nm.products.converter.ProductDefinitionGraphConverter;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.converter.ProductInstanceConverter;
import com.nm.products.converter.impl.ProductDefinitionFormConverterImpl;
import com.nm.products.converter.impl.ProductDefinitionGraphConverterImpl;
import com.nm.products.converter.impl.ProductDefinitionViewConverterImpl;
import com.nm.products.converter.impl.ProductInstanceConverterImpl;
import com.nm.products.dao.DaoCategory;
import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.DaoProductDefinitionPart;
import com.nm.products.dao.impl.DaoCategoryImpl;
import com.nm.products.dao.impl.DaoIngredientImpl;
import com.nm.products.dao.impl.DaoProductDefinitionImpl;
import com.nm.products.dao.impl.DaoProductDefinitionPartImpl;
import com.nm.products.factory.ProductDataFactory;
import com.nm.products.navigation.ProductNavigationProcessor;
import com.nm.products.navigation.ProductNavigatorBreath;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.products.soa.cache.ProductCacheBuilder;
import com.nm.products.soa.cache.ProductCacheBuilderImpl;
import com.nm.products.soa.impl.SoaProductDefinitionImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@Import(ConfigurationProductsChecker.class)
public class ConfigurationProducts {
	public static final String MODULE_NAME = "products";

	@Bean
	public ProductNavigationProcessor productNavigationProcessor(//
			DaoProductDefinitionPart daoPart, DaoProductDefinition daoP, //
			ProductDefinitionViewConverter view) throws Exception {
		ProductNavigationProcessor p = new ProductNavigationProcessor();
		p.setDaoPart(daoPart);
		p.setDaoProduct(daoP);
		p.setViewConverter(view);
		return p;
	}

	@Bean
	public ProductNavigatorBreath ProductNavigatorBreath(ProductNavigationProcessor nav, //
			ProductDefinitionViewConverter view) throws Exception {
		ProductNavigatorBreath p = new ProductNavigatorBreath();
		p.setProcessor(nav);
		p.setViewConverter(view);
		return p;
	}

	@Bean
	public ProductCacheBuilder productCacheBuilderImpl(//
			DaoProductDefinitionPart daoPart, DaoProductDefinition daoP, //
			DaoIngredient daoI) throws Exception {
		ProductCacheBuilderImpl p = new ProductCacheBuilderImpl();
		p.setDaoIngredient(daoI);
		p.setDaoProduct(daoP);
		p.setDaoPart(daoPart);
		return p;
	}

	@Bean
	public ProductDataFactory productDataFactory(//
			SoaProductDefinition soaP, DaoProductDefinition daoP, //
			DaoCategory doaC, DaoIngredient daoI, //
			SoaLocale soaL, SoaDevise soaD) throws Exception {
		ProductDataFactory p = new ProductDataFactory();
		p.setDaoCategory(doaC);
		p.setDaoIngredient(daoI);
		p.setDaoProductDefinition(daoP);
		p.setSoaDevise(soaD);
		p.setSoaLocale(soaL);
		p.setSoaProductDefinition(soaP);
		return p;
	}

	@Bean
	public ProductDefinitionFormConverter productDefinitionFormConverterImpl(//
			ProductDefinitionViewConverter view, DaoCategory daoC, //
			DaoProductDefinition daoP, DaoIngredient daoI, //
			DtoConverterRegistry reg) throws Exception {
		ProductDefinitionFormConverterImpl p = new ProductDefinitionFormConverterImpl();
		p.setDaoCategory(daoC);
		p.setDaoIngredient(daoI);
		p.setDaoProductDefinition(daoP);
		p.setRegistry(reg);
		p.setViewConverter(view);
		return p;
	}

	@Bean
	public ProductDefinitionGraphConverter productDefinitionGraphConverterImpl(//
			ProductDefinitionViewConverter view) throws Exception {
		ProductDefinitionGraphConverterImpl p = new ProductDefinitionGraphConverterImpl();
		p.setViewConverter(view);
		return p;
	}

	@Bean
	public ProductDefinitionViewConverter ProductDefinitionViewConverterImpl(//
			DtoConverterRegistry reg) throws Exception {
		ProductDefinitionViewConverterImpl p = new ProductDefinitionViewConverterImpl();
		p.setRegistry(reg);
		return p;
	}

	@Bean
	public ProductInstanceConverter productInstanceConverterImpl(//
			ProductCacheBuilder reg) throws Exception {
		ProductInstanceConverterImpl p = new ProductInstanceConverterImpl();
		p.setProductCacheBuilder(reg);
		return p;
	}

	@Bean
	public DaoCategory daoCategoryImpl(DatabaseTemplateFactory fac) throws Exception {
		com.nm.products.dao.impl.DaoCategoryImpl p = new DaoCategoryImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public DaoIngredient daoIngredientImpl(DatabaseTemplateFactory fac) throws Exception {
		com.nm.products.dao.impl.DaoIngredientImpl p = new DaoIngredientImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public DaoProductDefinition daoProductDefinitionImpl(DatabaseTemplateFactory fac) throws Exception {
		com.nm.products.dao.impl.DaoProductDefinitionImpl p = new DaoProductDefinitionImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public DaoProductDefinitionPart daoProductDefinitionPartImpl(DatabaseTemplateFactory fac) throws Exception {
		com.nm.products.dao.impl.DaoProductDefinitionPartImpl p = new DaoProductDefinitionPartImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	// SoaProductInstance ....
	@Bean
	public SoaProductDefinition soaProductDefinitionImpl(DaoCategory daoC, //
			DaoProductDefinition daoP, DaoIngredient daoI, //
			SoaDraft soaD, ProductDefinitionViewConverter view, //
			ProductDefinitionFormConverter form) throws Exception {
		SoaProductDefinitionImpl p = new SoaProductDefinitionImpl();
		p.setDaoCategory(daoC);
		p.setDaoIngredient(daoI);
		p.setDaoProductDefinition(daoP);
		p.setFormConverter(form);
		p.setProductConverter(view);
		p.setSoaDraft(soaD);
		return p;
	}

}
