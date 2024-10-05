package com.nm.templates.engines;

import java.util.Collection;

import com.nm.templates.constants.TemplateType;
import com.nm.templates.models.Template;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TemplateEngineFactory {

	public static TemplateEngine build(Template content) throws NotFoundException {
		Collection<TemplateEngine> engines = ApplicationUtils.getBeansCollection(TemplateEngine.class);
		for (TemplateEngine e : engines) {
			if (e.accept(content.getProcessor())) {
				return e;
			}
		}
		throw new NotFoundException("Could not found template engine");
	}

	public static TemplateEngine build(TemplateType content) throws NotFoundException {
		Collection<TemplateEngine> engines = ApplicationUtils.getBeansCollection(TemplateEngine.class);
		for (TemplateEngine e : engines) {
			if (e.accept(content)) {
				return e;
			}
		}
		throw new NotFoundException("Could not found template engine");
	}

}
