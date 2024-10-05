package com.nm.templates.launcher;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.nm.app.async.AsyncExecutor;
import com.nm.app.async.AsyncExecutorContract;
import com.nm.templates.daos.TemplateQueryBuilder;
import com.nm.templates.dtos.TemplateDto;
import com.nm.templates.models.Template;
import com.nm.templates.processors.TemplateProcessor;
import com.nm.templates.processors.TemplateProcessorAbstract;
import com.nm.templates.processors.strategies.TemplateProcessorStrategy;
import com.nm.templates.processors.strategies.TemplateProcessorStrategyFactory;
import com.nm.templates.processors.strategies.TemplateProcessorStrategyFactory.TemplateOverideIfArg;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.hibernate.impl.TransactionWrapper;
import com.nm.utils.hibernate.impl.TransactionWrapper.TransactionHandler;

/**
 * 
 * @author Nabil MANSOURI
 *
 * @param <T>
 */
public abstract class AsyncLauncherAbstract<T extends Serializable> {
	protected abstract TemplateDto getTemplate();

	protected abstract TemplateOverideIfArg getOverride();

	protected abstract TimeUnit units();

	protected abstract int duration();

	protected Template getTemplateModel() throws NoDataFoundException {
		return AbstractGenericDao.get(Template.class).findFirst(TemplateQueryBuilder.get().withId(getTemplate().getId()));
	}

	public AsyncExecutor<byte[]> compute(final AsyncLauncherListener<T> listener) throws Exception {
		return new AsyncExecutor<byte[]>(new AsyncExecutorContract<byte[]>() {

			public int maxDuration() {
				return duration();
			}

			public TimeUnit maxDurationUnit() {
				return units();
			}

			public byte[] start() {
				TransactionWrapper wrapper = ApplicationUtils.getBean(TransactionWrapper.class);
				wrapper.requireIfNotExists(new TransactionHandler<Void>() {
					public Void process() {
						try {
							TemplateProcessor processor = TemplateProcessorAbstract.get(listener);
							TemplateProcessorStrategy strategy = TemplateProcessorStrategyFactory.overrideStrategyIf(getTemplateModel(),
									getOverride());
							processor.generate(strategy);
						} catch (Exception e) {
							throw new IllegalArgumentException(e);
						}
						return null;
					}
				});
				return listener.getRes();
			}

			public byte[] result() {
				return listener.getRes();
			}
		});
	}
}
