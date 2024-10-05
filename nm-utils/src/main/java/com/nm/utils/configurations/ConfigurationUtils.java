package com.nm.utils.configurations;

import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.nm.utils.ApplicationUtils;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.hibernate.impl.TransactionDynamicHelper;
import com.nm.utils.hibernate.impl.TransactionWrapper;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationUtils {
	public static final String MODULE_NAME = "utils";

	@Bean
	public DtoConverterRegistry dtoConverterRegistry() {
		return new DtoConverterRegistry();
	}

	@Bean
	public TransactionWrapper transactionWrapper() {
		return new TransactionWrapper();
	}

	@Bean
	public TransactionDynamicHelper transactionDynamicHelper() {
		return new TransactionDynamicHelper();
	}

	@Bean
	public EnumJsonConverterRegistry enumJsonConverterRegistry(Environment env) {
		EnumJsonConverterRegistry e = EnumJsonConverterRegistry.getOrCreateInstance();
		String pack = env.getProperty("json.registry.base");
		e.setBase(pack);
		return e;
	}

	@Bean
	public ApplicationUtils applicationUtils(AbstractBeanFactory fac, ApplicationContext con) {
		ApplicationUtils e = new ApplicationUtils();
		e.setFac(fac);
		e.setApplicationContext(con);
		return e;
	}
}
