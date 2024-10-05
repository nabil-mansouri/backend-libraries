package com.nm.utils.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.utils.node.ModelNodeInitializer;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationUtilsExt {

	@Bean
	public ModelNodeInitializer modelNodeInitializer() {
		return new ModelNodeInitializer();
	}
}
