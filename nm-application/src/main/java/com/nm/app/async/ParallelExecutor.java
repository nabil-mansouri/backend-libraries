package com.nm.app.async;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class ParallelExecutor<T> {
	private final ParallelExecutorContract<T> contract;

	public ParallelExecutor(ParallelExecutorContract<T> contract) {
		super();
		this.contract = contract;
	}

	public Collection<T> parallel() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(contract.threads());
		CompletionService<T> completionService = new ExecutorCompletionService<T>(executor);
		AtomicInteger received = new AtomicInteger(0);
		for (Callable<T> c : contract.callables()) {
			completionService.submit(c);
			received.incrementAndGet();
		}
		// WAIT
		Collection<T> m = new ArrayList<T>();
		while (0 < received.get()) {
			try {
				Future<T> resultFuture = completionService.take();
				T temp = resultFuture.get();
				if (temp != null) {
					m.add(temp);
				} else {
					contract.onNull(resultFuture);
				}
			} catch (Exception e) {
				getHelper().logError(e);
				contract.onException(e);
			}
			received.decrementAndGet();
		}
		return m;
	}

	protected AsyncExecutorHelper getHelper() {
		AsyncExecutorHelper configService = ApplicationUtils.getBean(AsyncExecutorHelper.class);
		return configService;
	}
}
