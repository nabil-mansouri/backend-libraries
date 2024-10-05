package com.nm.templates.processors;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.nm.templates.TemplateException;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.engines.TemplateEngine;
import com.nm.templates.engines.TemplateEngineFactory;
import com.nm.templates.models.Template;
import com.nm.templates.processors.strategies.TemplateProcessorStrategy;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public abstract class TemplateProcessorAbstract implements TemplateProcessor {
	protected final TemplateProcessorListener listener;
	private TemplateContext current;

	protected static interface TemplateProcessorAction {
		public void doGenerate(TemplateContext clone) throws Exception;
	}

	public TemplateProcessorAbstract(TemplateProcessorListener listener) {
		super();
		this.listener = listener;
	}

	public static TemplateProcessor get(TemplateProcessorListener l) {
		return new TemplateProcessorImplSimple(l);
	}

	public TemplateContext getCurrentContext() {
		return current;
	}

	public void generate(final TemplateProcessorStrategy strategy) throws TemplateException {
		try {
			strategy.before();
			if (listener != null) {
				listener.onBuildContext(strategy.getContext());
			}
			strategy.prepare();
			strategy.hydrate();
			onContextReady(strategy.getContext(), new TemplateProcessorAction() {
				public void doGenerate(TemplateContext clone) throws Exception {
					TemplateProcessorAbstract.this.current = clone;
					// SUBTEMPLATES
					for (Template child : strategy.getTemplate().getChildren()) {
						if (strategy.acceptSubTemplate(child)) {
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							TemplateEngine engine = TemplateEngineFactory.build(child.getProcessor());
							engine.generate(child, clone, out);
							clone.getResults().putResult(child.getTemplateName().toString(), out.toByteArray());
						}
					}
					//
					Template template = strategy.getTemplate();
					OutputStream out = strategy.getEngine().generate(template, clone);
					listener.generate(TemplateProcessorAbstract.this, out);
				}
			});
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	protected abstract void onContextReady(TemplateContext original, TemplateProcessorAction action) throws Exception;

}
