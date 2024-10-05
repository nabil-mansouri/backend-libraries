package com.nm.utils.db;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DatabaseTemplateFactory {
	public static final String DEFAULT_HIBERNATE_NAME = "hibernateTemplate";
	public static final String DEFAULT_TRANSACTION_NAME = "transactionManager";
	public static final String DEFAULT_JDBC_TRANSACTION_NAME = "transactionManagerJdbc";
	public static final String DEFAULT_SESSION_NAME = "sessionFactoryDefault";
	public static final String DEFAULT_JDBC_NAME = "jdbcTemplate";
	public static final String DEFAULT_DATASOURCE = "dataSource";
	// MAP WORK WITH @Configuration!
	private Map<String, HibernateTemplate> hibernates;
	private Map<String, JdbcTemplate> jdbc;
	private Map<String, DataSource> sources;
	private Environment env;

	public DatabaseTemplateFactory() {
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public void setHibernates(Map<String, HibernateTemplate> hibernates) {
		this.hibernates = hibernates;
	}

	public void setJdbc(Map<String, JdbcTemplate> jdbc) {
		this.jdbc = jdbc;
	}

	public void setSources(Map<String, DataSource> sources) {
		this.sources = sources;
	}

	public DataSource dsResource(ModuleName name) {
		return dsResource(name.name());
	}

	public DataSource dsResource(String name) {
		String n = env.getProperty(String.format("hibernate.module.%s", name));
		if (n == null) {
			n = env.getProperty("hibernate.module.default", DEFAULT_DATASOURCE);
		}
		return sources.get(n);
	}

	public HibernateTemplate hibernateResource(ModuleName name) {
		return hibernateResource(name.name());
	}

	public HibernateTemplate hibernateResource(String name) {
		String n = env.getProperty(String.format("hibernate.module.%s", name));
		if (n == null) {
			n = env.getProperty("hibernate.module.default", DEFAULT_HIBERNATE_NAME);
		}
		return hibernates.get(n);
	}

	public JdbcTemplate jdbcResourceName(ModuleName name) {
		return jdbcResourceName(name.name());
	}

	public JdbcTemplate jdbcResourceName(String name) {
		String n = env.getProperty(String.format("jdbc.module.%s", name));
		if (n == null) {
			n = env.getProperty("jdbc.module.default", DEFAULT_JDBC_NAME);
		}
		return jdbc.get(n);
	}
}
