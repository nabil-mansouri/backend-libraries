package com.nm.templates.processors.strategies;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;
import com.nm.app.async.ParallelExecutor;
import com.nm.app.async.ParallelExecutorContract;
import com.nm.config.SoaAppConfig;
import com.nm.config.constants.AppConfigKeyDefault;
import com.nm.templates.TemplateException;
import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.contexts.TemplateContextParameters;
import com.nm.templates.contexts.TemplateContextResults;
import com.nm.templates.engines.TemplateEngine;
import com.nm.templates.models.Template;
import com.nm.templates.models.TemplateArgument;
import com.nm.templates.processors.TemplateProcessorContext;
import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class TemplateProcessorStrategyAbstract implements TemplateProcessorStrategy {
	protected Template template;
	protected TemplateEngine engine;
	protected TemplateContext original;
	protected Collection<TemplateProcessorContext> founded;
	protected Collection<TemplateProcessorContext> withDep;
	protected Collection<TemplateProcessorContext> withoutDep;

	public Template getTemplate() {
		return template;
	}

	public TemplateEngine getEngine() {
		return engine;
	}

	public TemplateContext getContext() {
		return original;
	}

	protected abstract Collection<TemplateArgsEnum> getArgs();

	public boolean acceptSubTemplate(Template template) {
		if (template.getArguments().isEmpty()) {
			return true;
		}
		Collection<TemplateArgsEnum> current = Lists.newArrayList();
		for (TemplateArgument a : template.getArguments()) {
			current.add(a.getArgument());
		}
		if (getArgs().containsAll(current)) {
			return true;
		}
		return false;
	}

	protected final void prepare(final Collection<TemplateProcessorContext> without, final Collection<TemplateProcessorContext> with,
			final TemplateContextParameters context) throws Exception {
		ParallelExecutor<Void> async = new ParallelExecutor<Void>(new ParallelExecutorContract<Void>() {

			public int threads() {
				return ApplicationUtils.getBean(SoaAppConfig.class).getInt(AppConfigKeyDefault.DefaultParallelThread);
			}

			public List<Callable<Void>> callables() {
				List<Callable<Void>> call = Lists.newArrayList();
				for (final TemplateProcessorContext p : without) {
					call.add(new Callable<Void>() {

						public Void call() throws Exception {
							p.prepare(context);
							return null;
						}
					});
				}
				return call;
			}

			public void onNull(Future<Void> f) {

			}

			public void onException(Exception e) {
				context.pushError(e);
			}

		});
		async.parallel();
		// DEP
		for (TemplateProcessorContext p : with) {
			p.prepare(context);
		}
	}

	public void prepare() throws TemplateException {
		try {
			prepare(withoutDep, withDep, original.getParameters());
			if (original.hasErrors()) {
				throw original.getErrors().iterator().next();
			}
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	public void hydrate() throws TemplateException {
		try {
			hydrate(withoutDep, withDep, original.getResults());
			if (original.hasErrors()) {
				throw original.getErrors().iterator().next();
			}
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	protected final void hydrate(final Collection<TemplateProcessorContext> without, final Collection<TemplateProcessorContext> with,
			final TemplateContextResults context) throws Exception {
		// TODO parallel modification cause trouble =>
		// coincurrentmodificationexception
		// ParallelExecutor<Void> async = new ParallelExecutor<Void>(new
		// ParallelExecutorContract<Void>() {
		//
		// public int threads() {
		// return
		// ApplicationUtils.getBean(SoaAppConfig.class).getInt(AppConfigKeyDefault.DefaultParallelThread);
		// }
		//
		// public List<Callable<Void>> callables() {
		// List<Callable<Void>> call = Lists.newArrayList();
		// for (final TemplateProcessorContext p : without) {
		// call.add(new Callable<Void>() {
		//
		// public Void call() throws Exception {
		// p.hydrate(context);
		// return null;
		// }
		// });
		// }
		// return call;
		// }
		//
		// public void onNull(Future<Void> f) {
		//
		// }
		//
		// public void onException(Exception e) {
		// context.pushError(e);
		// }
		//
		// });
		// async.parallel();
		//
		for (TemplateProcessorContext p : without) {
			p.hydrate(context);
		}

		// DEP
		for (TemplateProcessorContext p : with) {
			p.hydrate(context);
		}
	}
}
