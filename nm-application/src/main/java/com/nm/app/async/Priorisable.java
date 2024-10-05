package com.nm.app.async;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface Priorisable {

	public int priority();

	public boolean hasDependencies();

}
