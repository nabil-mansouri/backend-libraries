package com.nm.templates.processors.strategies;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.app.async.PriorisableUtils;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.engines.TemplateEngineFactory;
import com.nm.templates.models.Template;
import com.nm.templates.processors.TemplateProcessorContext;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TemplateProcessorStrategyOverride extends TemplateProcessorStrategyAbstract {
	Collection<TemplateArgsEnum> overrides;

	public TemplateProcessorStrategyOverride(Template template, Collection<TemplateArgsEnum> overrides) {
		super();
		this.template = template;
		this.overrides = overrides;
	}

	public void before() throws NotFoundException {
		founded = find(overrides);
		withDep = PriorisableUtils.withDep(founded);
		withoutDep = PriorisableUtils.withoutDep(founded);
		withDep = PriorisableUtils.sortDesc(withDep);
		engine = TemplateEngineFactory.build(template);
		original = engine.buildContext();
	}

	@Override
	protected Collection<TemplateArgsEnum> getArgs() {
		return overrides;
	}

	protected Collection<TemplateProcessorContext> find(Collection<TemplateArgsEnum> args) {
		Collection<TemplateProcessorContext> processor = ApplicationUtils.getBeansCollection(TemplateProcessorContext.class);
		Collection<TemplateProcessorContext> founded = Lists.newArrayList();
		for (TemplateProcessorContext process : processor) {
			for (TemplateArgsEnum arg : args) {
				if (process.accept(arg)) {
					founded.add(process);
					break;
				}
			}
		}
		return founded;
	}
}
