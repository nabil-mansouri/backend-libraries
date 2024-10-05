package com.nm.app.async;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class ParallelExecutorContractLoop<T> implements ParallelExecutorContract<T> {
	public abstract int total();

	int i = 0;

	public int threads() {
		return total();
	}

	public abstract ParallelExecutorContractLoopBlock<T> factory(int i);

	public List<Callable<T>> callables() {
		List<Callable<T>> l = Lists.newArrayList();
		for (i = 0; i < total(); i++) {
			l.add(new Callable<T>() {
				public T call() throws Exception {
					return factory(i).call(i);
				}
			});
		}
		return l;
	}

	public void onException(Exception e) {

	}

	public void onNull(Future<T> f) {

	}
}
