package com.nm.auths.configurations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @see org.springframework.session.jdbc.config.annotation.web.http.
 *      EnableJdbcHttpSession
 * @author Nabil MANSOURI
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ConfigurationJdbcHttpSessionCustom.class)
@Configuration
public @interface EnableJdbcHttpSessionCustom {

	/**
	 * The name of database table used by Spring Session to store sessions.
	 * 
	 * @return the database table name
	 */
	String tableName() default "";

	/**
	 * The session timeout in seconds. By default, it is set to 1800 seconds (30
	 * minutes). This should be a non-negative integer.
	 *
	 * @return the seconds a session can be inactive before expiring
	 */
	int maxInactiveIntervalInSeconds() default 1800;

}
