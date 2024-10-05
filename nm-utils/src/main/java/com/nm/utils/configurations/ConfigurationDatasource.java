package com.nm.utils.configurations;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.collect.Lists;
import com.nm.utils.db.DatabaseTemplateFactory;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableTransactionManagement
@Import({ ConfigurationDatasourceDev.class, ConfigurationDatasourceTest.class })
public class ConfigurationDatasource {
	private Collection<HibernateConfigPart> hibernateConfigs = Lists.newArrayList();

	@Autowired(required = false)
	public void setHibernateConfigs(Collection<HibernateConfigPart> hibernateConfigs) {
		this.hibernateConfigs = hibernateConfigs;
	}

	@Bean(name = DatabaseTemplateFactory.DEFAULT_DATASOURCE)
	public DataSource dataSource(Environment env) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(env.getProperty("database.jbdc.driver"));
		ds.setUrl(env.getProperty("database.jbdc.url"));
		ds.setUsername(env.getProperty("database.jbdc.username"));
		ds.setPassword(env.getProperty("database.jbdc.password"));
		return ds;
	}

	@Bean(name = DatabaseTemplateFactory.DEFAULT_JDBC_NAME)
	public JdbcTemplate jdbcTemplate(@Qualifier(DatabaseTemplateFactory.DEFAULT_DATASOURCE) DataSource ds) {
		JdbcTemplate te = new JdbcTemplate();
		te.setDataSource(ds);
		return te;
	}

	@Bean(name = DatabaseTemplateFactory.DEFAULT_HIBERNATE_NAME)
	public HibernateTemplate hibernateTemplate(Environment env, @Qualifier("sessionFactoryDefault") SessionFactory session) {
		HibernateTemplate te = new HibernateTemplate(session);
		return te;
	}

	@Primary
	@Bean(name = DatabaseTemplateFactory.DEFAULT_TRANSACTION_NAME)
	public HibernateTransactionManager transactionManager(@Qualifier(DatabaseTemplateFactory.DEFAULT_SESSION_NAME) SessionFactory session) {
		HibernateTransactionManager tr = new HibernateTransactionManager();
		tr.setSessionFactory(session);
		return tr;
	}

	@Bean(name = DatabaseTemplateFactory.DEFAULT_JDBC_TRANSACTION_NAME)
	public PlatformTransactionManager platformTransactionManager(
			@Qualifier(DatabaseTemplateFactory.DEFAULT_JDBC_NAME) JdbcTemplate template) {
		PlatformTransactionManager tr = new DataSourceTransactionManager(template.getDataSource());
		return tr;
	}

	@Bean(name = DatabaseTemplateFactory.DEFAULT_SESSION_NAME)
	public LocalSessionFactoryBean sessionFactoryDefault(Environment env) {
		Collection<HibernateConfigPart> all = get(DatabaseTemplateFactory.DEFAULT_HIBERNATE_NAME);
		//
		LocalSessionFactoryBean l = new LocalSessionFactoryBean();
		l.setDataSource(dataSource(env));
		// PACKAGES
		String[] annotatedPackages = {};
		for (HibernateConfigPart h : all) {
			for (Class<?> c : h.getBasePackage()) {
				annotatedPackages = ArrayUtils.add(annotatedPackages, c.getPackage().getName());
			}
		}
		l.setPackagesToScan(annotatedPackages);
		//
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("database.hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("database.hibernate.show_sql"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("database.hibernate.hbm2ddl.auto"));
		properties.put("hibernate.format_sql", env.getProperty("database.hibernate.format_sql", "true"));
		properties.put("hibernate.use_sql_comments", env.getProperty("database.hibernate.use_sql_comments"));
		properties.put("hibernate.hbm2ddl.import_files_sql_extractor", MultipleLinesSqlCommandExtractor.class.getName());
		properties.put("hibernate.cache.region.factory_class", SingletonEhCacheRegionFactory.class.getName());
		// SQL
		List<String> scripts = Lists.newArrayList();
		for (HibernateConfigPart h : all) {
			for (String c : h.getSqlScript()) {
				scripts.add(StringUtils.trim(c));
			}
		}
		if (!scripts.isEmpty()) {
			properties.put("hibernate.hbm2ddl.import_files", StringUtils.join(scripts, ";"));
		}
		//
		l.setAnnotatedPackages(annotatedPackages);
		//
		l.setHibernateProperties(properties);
		return l;
	}

	private Collection<HibernateConfigPart> get(String name) {
		Collection<HibernateConfigPart> all = Lists.newArrayList();
		for (HibernateConfigPart h : this.hibernateConfigs) {
			if (StringUtils.equalsIgnoreCase(StringUtils.trim(h.getModuleName()), name)) {
				all.add(h);
			}
		}
		return all;
	}
}
