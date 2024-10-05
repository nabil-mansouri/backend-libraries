package com.nm.app;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.nm.app.draft.Draft;
import com.nm.app.history.History;
import com.nm.app.job.AppJob;
import com.nm.app.log.GeneralLog;
import com.nm.app.triggers.Trigger;
import com.nm.utils.configurations.HibernateConfigPart;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationApplicationHibernate implements ImportAware {

	private String module;

	/**
	 * Must have different name for method
	 * 
	 * @return
	 */
	@Bean
	public HibernateConfigPart hibernateConfigForApplication() {
		HibernateConfigPart h = new HibernateConfigPart();
		h.setModuleName(module);
		h.add(Draft.class);
		h.add(History.class);
		h.add(AppJob.class);
		h.add(GeneralLog.class);
		h.add(Trigger.class);
		return h;
	}

	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> enableAttrMap = importMetadata
				.getAnnotationAttributes(EnableApplicationModule.class.getName());
		AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
		this.module = enableAttrs.getString("hibernateName");
	}

}
