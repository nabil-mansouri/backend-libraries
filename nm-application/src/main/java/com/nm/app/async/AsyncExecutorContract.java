package com.nm.app.async;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface AsyncExecutorContract<T> {
	public T start();

	public byte[] result();

	public int maxDuration();

	public TimeUnit maxDurationUnit();
}
