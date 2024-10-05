package com.nm.app.async;

/**
 * 
 * @author MANSOURI Nabil
 *
 * @param <T>
 */
public abstract interface ParallelExecutorContractLoopBlock<T> {

	public T call(int i) throws Exception;

}