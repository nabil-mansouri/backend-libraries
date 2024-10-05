package com.nm.auths.configurations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import com.nm.utils.db.DatabaseTemplateFactory;

/**
 * @see org.springframework.session.jdbc.config.annotation.web.http.
 *      JdbcHttpSessionConfiguration
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableScheduling
public class ConfigurationJdbcHttpSessionCustom extends SpringHttpSessionConfiguration implements ImportAware {

	private String tableName = "";

	private Integer maxInactiveIntervalInSeconds = 1800;

	private LobHandler lobHandler;

	@Autowired(required = false)
	@Qualifier("conversionService")
	private ConversionService conversionService;

	private ConversionService springSessionConversionService;

	@Bean
	public JdbcOperationsSessionRepository sessionRepository(DatabaseTemplateFactory fac,
			PlatformTransactionManager transactionManager) {
		JdbcOperations jdbcOperations = fac.jdbcResourceName(ConfigurationAuthentications.MODULE_NAME);
		JdbcOperationsSessionRepository sessionRepository = new JdbcOperationsSessionRepository(jdbcOperations,
				transactionManager);
		String tableName = getTableName();
		if (StringUtils.hasText(tableName)) {
			sessionRepository.setTableName(tableName);
		}
		sessionRepository.setDefaultMaxInactiveInterval(this.maxInactiveIntervalInSeconds);
		if (this.lobHandler != null) {
			sessionRepository.setLobHandler(this.lobHandler);
		}
		if (this.springSessionConversionService != null) {
			sessionRepository.setConversionService(this.springSessionConversionService);
		} else if (this.conversionService != null) {
			sessionRepository.setConversionService(this.conversionService);
		}
		//
		sessionRepository.setTableName(ConfigurationAuthenticationsSecurity.SESSION_TABLE_NAME);
		return sessionRepository;
	}

	@Autowired(required = false)
	@Qualifier("springSessionLobHandler")
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	@Autowired(required = false)
	@Qualifier("springSessionConversionService")
	public void setSpringSessionConversionService(ConversionService conversionService) {
		this.springSessionConversionService = conversionService;
	}

	private String getTableName() {
		if (StringUtils.hasText(this.tableName)) {
			return this.tableName;
		}
		return System.getProperty("spring.session.jdbc.tableName", "");
	}

	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> enableAttrMap = importMetadata
				.getAnnotationAttributes(EnableJdbcHttpSessionCustom.class.getName());
		AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
		this.tableName = enableAttrs.getString("tableName");
		this.maxInactiveIntervalInSeconds = enableAttrs.getNumber("maxInactiveIntervalInSeconds");
	}

	// NEEDED
}
