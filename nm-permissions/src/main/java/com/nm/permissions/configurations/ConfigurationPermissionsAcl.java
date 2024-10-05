package com.nm.permissions.configurations;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.drools.core.util.IoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nm.permissions.acl.PermissionFactoryCustom;
import com.nm.permissions.acl.SoaAcl;
import com.nm.permissions.acl.SoaAclImpl;
import com.nm.permissions.acl.converters.AclObjectIdentityConverter;
import com.nm.permissions.acl.converters.AclPermissionConverter;
import com.nm.permissions.acl.converters.AclSIDConverter;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

import net.sf.ehcache.CacheManager;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ConfigurationPermissionsAcl {
	public static final String ROLE_ADMIN = "ROLE_ACL_ADMIN";
	String sidIdentityQuery;
	String classIdentityQuery;

	@Autowired
	public void setFactory(DatabaseTemplateFactory factory, Environment e) throws Exception {
		JdbcTemplate jdbc = factory.jdbcResourceName(ConfigurationPermissions.MODULE_NAME);
		DataSource ds = jdbc.getDataSource();
		String suffix = (String) JdbcUtils.extractDatabaseMetaData(ds, new DatabaseMetaDataCallback() {

			public Object processMetaData(DatabaseMetaData databaseMetaData) throws SQLException {
				String databaseProductName = JdbcUtils.commonDatabaseName(databaseMetaData.getDatabaseProductName());
				if ("Oracle".equalsIgnoreCase(databaseProductName)) {
					return "Oracle";
				} else if ("PostgreSQL".equalsIgnoreCase(databaseProductName)) {
					sidIdentityQuery = "SELECT currval('acl_sid_id_seq');";
					classIdentityQuery = "SELECT currval('acl_class_id_seq');";
					return "Postgres";
				} else if ("MySQL".equalsIgnoreCase(databaseProductName)) {
					return "MySQL";
				} else if ("SqlServer".equalsIgnoreCase(databaseProductName)) {
					return "SqlServer";
				} else {
					return "";
				}
			}
		});
		String file = String.format("createAclSchema%s.sql", suffix);
		ClassPathResource cp = new ClassPathResource(file);
		EncodedResource resource = (new EncodedResource(cp));
		String operation = e.getProperty("database.hibernate.hbm2ddl.auto");
		if (StringUtils.equalsIgnoreCase("create", StringUtils.trim(operation))) {
			byte[] bytes = IoUtils.readBytesFromInputStream(cp.getInputStream());
			for (String l : StringUtils.toEncodedString(bytes, resource.getCharset()).split("\n")) {
				try {
					// DROP TABLE IF EXISTS
					if (StringUtils.containsIgnoreCase(l, "drop table")) {
						l = StringUtils.removeStart(l, "--");
						jdbc.execute(l);
					}
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			}
			new ResourceDatabasePopulator(true, false, resource.getEncoding(), resource.getResource()).execute(ds);
		}
	}

	@Bean
	public SoaAcl soaAclImpl(MutableAclService c, DtoConverterRegistry reg) {
		SoaAclImpl s = new SoaAclImpl();
		s.setAclServiceMutable(c);
		s.setRegistry(reg);
		return s;
	}

	// CONVERTERS
	@Bean
	public AclPermissionConverter aclPermissionConverter(PermissionFactoryCustom c) {
		AclPermissionConverter s = new AclPermissionConverter();
		s.setFactory(c);
		return s;
	}

	@Bean
	public AclSIDConverter aclSIDConverter() {
		return new AclSIDConverter();
	}

	@Bean
	public AclObjectIdentityConverter aclObjectIdentityConverter() {
		return new AclObjectIdentityConverter();
	}

	// SPRING
	@Bean
	public PermissionFactory defaultPermissionFactory(EnumJsonConverterRegistry reg) {
		// return new DefaultPermissionFactory(BasePermission.class);
		return new PermissionFactoryCustom(reg);
	}

	@Bean
	public PermissionGrantingStrategy grantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}

	@Bean
	public AclCache aclCache() {
		EhCacheFactoryBean factoryBean = new EhCacheFactoryBean();
		EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
		cacheManager.setAcceptExisting(true);
		cacheManager.setCacheManagerName(CacheManager.getInstance().getName());
		cacheManager.afterPropertiesSet();
		factoryBean.setName("aclCache");
		factoryBean.setCacheManager(cacheManager.getObject());
		factoryBean.setMaxBytesLocalHeap("8M");
		factoryBean.setMaxEntriesLocalHeap(0L);
		factoryBean.afterPropertiesSet();
		return new EhCacheBasedAclCache(factoryBean.getObject(), grantingStrategy(), aclAuthorizationStrategy());
	}

	@Bean
	public LookupStrategy lookupStrategy(DatabaseTemplateFactory factory, PermissionFactory r) {
		BasicLookupStrategy b = new BasicLookupStrategy(factory.dsResource(ConfigurationPermissions.MODULE_NAME),
				aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
		b.setPermissionFactory(r);
		return b;
	}

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority(ROLE_ADMIN),
				new SimpleGrantedAuthority(ROLE_ADMIN), new SimpleGrantedAuthority(ROLE_ADMIN));
	}

	@Bean
	public MutableAclService aclServiceMutable(DatabaseTemplateFactory factory, LookupStrategy lookup) {
		JdbcMutableAclService service = new JdbcMutableAclService(
				factory.dsResource(ConfigurationPermissions.MODULE_NAME), lookup, aclCache());
		if (this.classIdentityQuery != null) {
			service.setClassIdentityQuery(classIdentityQuery);
		}
		if (this.sidIdentityQuery != null) {
			service.setSidIdentityQuery(sidIdentityQuery);
		}
		return service;
	}
}
