package com.nm.templates.processors.strategies;

import java.util.Collection;

import com.google.common.collect.Sets;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.constants.TemplateType;
import com.nm.templates.models.Template;
import com.nm.templates.models.TemplateArgument;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TemplateProcessorStrategyFactory {
	public static TemplateProcessorStrategy generateOnlyStrategy(TemplateProcessorStrategy strategy) {
		return new TemplateProcessorStrategyGenerateOnly(strategy);
	}

	public static TemplateProcessorStrategy defaultStrategy(Template template) {
		return new TemplateProcessorStrategyDefault(template);
	}

	public static TemplateProcessorStrategy overrideStrategy(Template template, Collection<TemplateArgsEnum> overrides) {
		return new TemplateProcessorStrategyOverride(template, overrides);
	}

	public static TemplateProcessorStrategy overrideAllStrategy(Template template, Collection<TemplateArgsEnum> overrides,
			TemplateType type) {
		return new TemplateProcessorStrategyOverrideAll(template, overrides, type);
	}

	public static interface TemplateOverideIfArg {
		TemplateType type();

		Collection<TemplateArgsEnum> selected();
	}

	public static TemplateProcessorStrategy overrideStrategyIf(Template template, TemplateOverideIfArg arg) {
		boolean typeOverride = arg.type() != null;
		boolean selectedOverride = (arg.selected() != null && !arg.selected().isEmpty());
		if (typeOverride || selectedOverride) {
			if (typeOverride && selectedOverride) {
				return overrideAllStrategy(template, arg.selected(), arg.type());
			} else if (typeOverride) {
				return overrideAllStrategy(template, args(template.getArguments()), arg.type());
			} else {
				return overrideStrategy(template, arg.selected());
			}
		} else {
			return defaultStrategy(template);
		}
	}

	private static Collection<TemplateArgsEnum> args(Collection<TemplateArgument> args) {
		Collection<TemplateArgsEnum> all = Sets.newHashSet();
		for (TemplateArgument a : args) {
			all.add(a.getArgument());
		}
		return all;
	}
}
