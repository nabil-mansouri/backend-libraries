package com.nm.templates.processors.strategies;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.nm.templates.TemplateException;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.models.Template;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TemplateProcessorStrategyGenerateOnly extends TemplateProcessorStrategyAbstract {
	TemplateProcessorStrategy strategy;

	public TemplateProcessorStrategyGenerateOnly(TemplateProcessorStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	public void before() throws NotFoundException {
		this.strategy.before();
	}

	@Override
	protected Collection<TemplateArgsEnum> getArgs() {
		return Lists.newArrayList();
	}

	@Override
	public boolean acceptSubTemplate(Template template) {
		return strategy.acceptSubTemplate(template);
	}

	@Override
	public void prepare() throws TemplateException {
	}

	@Override
	public void hydrate() throws TemplateException {
	}

}
