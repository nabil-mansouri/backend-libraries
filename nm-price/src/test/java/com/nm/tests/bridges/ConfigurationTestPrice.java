package com.nm.tests.bridges;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.prices.configurations.EnablePriceModule;
import com.nm.prices.contract.PriceFormConverter;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnablePriceModule
@EnableDatasModule()
@EnableConfigModule()
@EnableApplicationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestPrice {
	@Bean
	public PriceAdapterImpl priceAdapterImpl(PriceFormConverter form, PriceViewConverterImpl view) {
		PriceAdapterImpl p = new PriceAdapterImpl();
		p.setFormConverter(form);
		p.setViewConverter(view);
		return p;
	}

	@Bean
	public PriceViewConverterImpl priceViewConverterImpl() {
		return new PriceViewConverterImpl();
	}

	@Bean
	public PriceFormConverterImpl priceFormConverterImpl() {
		return new PriceFormConverterImpl();
	}
}
