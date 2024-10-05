package com.nm.shop.configurations;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.nm.shop.bridge.ShopPlanningable;
import com.nm.shop.model.Shop;
import com.nm.utils.configurations.HibernateConfigPart;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationShopHibernate implements ImportAware {

	private String module;

	/**
	 * Must have different name for method
	 * 
	 * @return
	 */
	@Bean
	public HibernateConfigPart hibernateConfigForShop() {
		HibernateConfigPart h = new HibernateConfigPart();
		h.setModuleName(module);
		h.add(Shop.class);
		h.add(ShopPlanningable.class);
		return h;
	}

	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> enableAttrMap = importMetadata.getAnnotationAttributes(EnableShopModule.class.getName());
		AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
		this.module = enableAttrs.getString("hibernateName");
	}
}
