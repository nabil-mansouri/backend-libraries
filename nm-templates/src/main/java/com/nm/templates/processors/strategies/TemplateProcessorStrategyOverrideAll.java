package com.nm.templates.processors.strategies;

import java.util.Collection;

import com.nm.app.async.PriorisableUtils;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.constants.TemplateType;
import com.nm.templates.engines.TemplateEngineFactory;
import com.nm.templates.models.Template;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TemplateProcessorStrategyOverrideAll extends TemplateProcessorStrategyOverride {
	TemplateType type;

	public TemplateProcessorStrategyOverrideAll(Template template, Collection<TemplateArgsEnum> overrides, TemplateType type) {
		super(template, overrides);
		this.type = type;
	}

	public void before() throws NotFoundException {
		founded = find(overrides);
		withDep = PriorisableUtils.withDep(founded);
		withoutDep = PriorisableUtils.withoutDep(founded);
		withDep = PriorisableUtils.sortDesc(withDep);
		engine = TemplateEngineFactory.build(type);
		original = engine.buildContext();
	}

}
