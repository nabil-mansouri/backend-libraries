package com.nm.app.async;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface ParallelExecutorContract<T> {
	public int threads();

	public List<Callable<T>> callables();

	public void onNull(Future<T> f);

	public void onException(Exception e);
}
