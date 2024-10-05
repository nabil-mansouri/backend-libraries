package com.nm.templates.processors.strategies;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.app.async.PriorisableUtils;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.engines.TemplateEngineFactory;
import com.nm.templates.models.Template;
import com.nm.templates.models.TemplateArgument;
import com.nm.templates.processors.TemplateProcessorContext;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TemplateProcessorStrategyDefault extends TemplateProcessorStrategyAbstract {

	public TemplateProcessorStrategyDefault(Template template) {
		super();
		this.template = template;
	}

	public void before() throws NotFoundException {
		founded = find(template);
		withDep = PriorisableUtils.withDep(founded);
		withoutDep = PriorisableUtils.withoutDep(founded);
		withDep = PriorisableUtils.sortDesc(withDep);
		engine = TemplateEngineFactory.build(template);
		original = engine.buildContext();
	}

	@Override
	protected Collection<TemplateArgsEnum> getArgs() {
		Collection<TemplateArgsEnum> current = Lists.newArrayList();
		for (TemplateArgument a : template.getArguments()) {
			current.add(a.getArgument());
		}
		return current;
	}

	private Collection<TemplateProcessorContext> find(Template template) {
		Collection<TemplateProcessorContext> processor = ApplicationUtils.getBeansCollection(TemplateProcessorContext.class);
		Collection<TemplateProcessorContext> founded = Lists.newArrayList();
		for (TemplateProcessorContext process : processor) {
			for (TemplateArgument arg : template.getArguments()) {
				if (process.accept(arg.getArgument())) {
					founded.add(process);
					break;
				}
			}
		}
		return founded;
	}

}
