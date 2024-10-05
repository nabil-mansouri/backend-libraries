package com.nm.utils.configurations;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.google.common.collect.Maps;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.graphs.hibernate.GraphOptimizedListener;
import com.nm.utils.node.ModelNodeListener;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationUtilsDatabases {
	private Map<String, HibernateTemplate> hibernates = Maps.newHashMap();
	private Map<String, JdbcTemplate> jdbc = Maps.newHashMap();
	private Map<String, DataSource> sources = Maps.newHashMap();

	@Autowired(required = false)
	public void setHibernates(Map<String, HibernateTemplate> hibernates) {
		this.hibernates = hibernates;
	}

	@Autowired(required = false)
	public void setJdbc(Map<String, JdbcTemplate> jdbc) {
		this.jdbc = jdbc;
	}

	@Autowired(required = false)
	public void setSources(Map<String, DataSource> sources) {
		this.sources = sources;
	}

	@Autowired(required = false)
	public void setSessionFactory(Collection<SessionFactory> sessions) {
		for (SessionFactory f : sessions) {
			ModelNodeListener.build(f);
			GraphOptimizedListener.build(f);
		}
	}

	@Bean
	public DatabaseTemplateFactory databaseTemplateFactory(Environment env) {
		DatabaseTemplateFactory d = new DatabaseTemplateFactory();
		d.setEnv(env);
		d.setHibernates(hibernates);
		d.setJdbc(jdbc);
		d.setSources(sources);
		return d;
	}

}
