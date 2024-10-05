package com.nm.utils.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@Profile(TestUrlUtils.PROFILE_TEST)
@PropertySource(value = { "classpath:utils/test.properties" })
public class ConfigurationDatasourceTest {

}
