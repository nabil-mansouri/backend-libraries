package com.nm.comms.templates;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;
import com.nm.app.async.ParallelExecutor;
import com.nm.app.async.ParallelExecutorContract;
import com.nm.app.async.PriorisableUtils;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.config.SoaAppConfig;
import com.nm.config.constants.AppConfigKeyDefault;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.contexts.TemplateContextParameters;
import com.nm.templates.contexts.TemplateContextResults;
import com.nm.templates.processors.TemplateProcessorAbstract;
import com.nm.templates.processors.TemplateProcessorListener;
import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TemplateProcessorImplCommunication extends TemplateProcessorAbstract {
	private final Message communication;
	private CommunicationActor currentActor;
	private CommunicationActor forcedActor;

	public TemplateProcessorImplCommunication(TemplateProcessorListener listener, Message communication) {
		super(listener);
		this.communication = communication;
	}

	public TemplateProcessorImplCommunication(TemplateProcessorListener listener, Message communication, CommunicationActor force) {
		super(listener);
		this.forcedActor = force;
		this.communication = communication;
	}

	@Override
	protected void onContextReady(TemplateContext original, TemplateProcessorAction action) throws Exception {
		this.currentActor = null;
		if (this.forcedActor == null) {
			for (CommunicationActor actor : communication.getReceivers()) {
				this.currentActor = actor;
				// CLONE FOR ACTOR
				processActor(original, action);
			}
		} else {
			this.currentActor = this.forcedActor;
			processActor(original, action);
		}
	}

	private void processActor(TemplateContext original, TemplateProcessorAction action) throws Exception {
		TemplateContext clone = original.clone();
		Collection<TemplateProcessorContextActor> foundedA = find(currentActor);
		Collection<TemplateProcessorContextActor> withDepA = PriorisableUtils.withDep(foundedA);
		Collection<TemplateProcessorContextActor> withoutDepA = PriorisableUtils.withoutDep(foundedA);
		prepare(withoutDepA, withDepA, clone.getParameters(), currentActor);
		if (clone.hasErrors()) {
			throw clone.getErrors().iterator().next();
		}
		hydrate(withoutDepA, withDepA, clone.getResults(), currentActor);
		if (clone.hasErrors()) {
			throw clone.getErrors().iterator().next();
		}
		//
		action.doGenerate(clone);
	}

	public CommunicationActor getCurrentActor() {
		return currentActor;
	}

	protected Collection<TemplateProcessorContextActor> find(CommunicationActor actor) {
		Collection<TemplateProcessorContextActor> processorActor = ApplicationUtils.getBeansCollection(TemplateProcessorContextActor.class);
		Collection<TemplateProcessorContextActor> founded = Lists.newArrayList();
		for (TemplateProcessorContextActor process : processorActor) {
			if (process.accept(actor)) {
				founded.add(process);
			}
		}
		return founded;
	}

	protected void prepare(final Collection<TemplateProcessorContextActor> without, final Collection<TemplateProcessorContextActor> with,
			final TemplateContextParameters context, final CommunicationActor actor) throws Exception {
		ParallelExecutor<Void> async = new ParallelExecutor<Void>(new ParallelExecutorContract<Void>() {

			public int threads() {
				return ApplicationUtils.getBean(SoaAppConfig.class).getInt(AppConfigKeyDefault.DefaultParallelThread);
			}

			public List<Callable<Void>> callables() {
				List<Callable<Void>> call = Lists.newArrayList();
				for (final TemplateProcessorContextActor p : without) {
					call.add(new Callable<Void>() {

						public Void call() throws Exception {
							p.prepare(context, actor);
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
		for (TemplateProcessorContextActor p : with) {
			p.prepare(context, actor);
		}
	}

	protected void hydrate(final Collection<TemplateProcessorContextActor> without, final Collection<TemplateProcessorContextActor> with,
			final TemplateContextResults context,

			final CommunicationActor actor) throws Exception {
		ParallelExecutor<Void> async = new ParallelExecutor<Void>(new ParallelExecutorContract<Void>() {

			public int threads() {
				return ApplicationUtils.getBean(SoaAppConfig.class).getInt(AppConfigKeyDefault.DefaultParallelThread);
			}

			public List<Callable<Void>> callables() {
				List<Callable<Void>> call = Lists.newArrayList();
				for (final TemplateProcessorContextActor p : without) {
					call.add(new Callable<Void>() {

						public Void call() throws Exception {
							p.hydrate(context, actor);
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
		for (TemplateProcessorContextActor p : with) {
			p.hydrate(context, actor);
		}
	}
}
