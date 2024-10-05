package com.nm.tests.data;

import org.springframework.context.annotation.Configuration;

import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableDatasModule
@EnableConfigModule
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestDatas {

}
