package com.nm.utils.configurations;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.nm.utils.graphs.hibernate.GraphOptimizedModel;
import com.nm.utils.node.ModelNode;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationUtilsHibernate implements ImportAware {

	private String module;

	/**
	 * Must have different name for method
	 * 
	 * @return
	 */
	@Bean
	public HibernateConfigPart hibernateConfigForUtils() {
		HibernateConfigPart h = new HibernateConfigPart();
		h.setModuleName(module);
		h.add(GraphOptimizedModel.class);
		h.add(ModelNode.class);
		return h;
	}

	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> enableAttrMap = importMetadata.getAnnotationAttributes(EnableUtilsModule.class.getName());
		AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
		this.module = enableAttrs.getString("hibernateName");
	}
}
