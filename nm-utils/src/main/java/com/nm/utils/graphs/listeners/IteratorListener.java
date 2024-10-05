package com.nm.utils.graphs.listeners;

import com.nm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
public interface IteratorListener {
	public boolean onFounded(AbstractGraph node);

	public boolean stop();

	public boolean doSetParent();
}
