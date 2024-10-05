package com.nm.utils.graphs.listeners;

import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author Nabil
 * 
 */
public interface IteratorListenerRecurive {
	public void onBeforeChildren(IGraph node);

	public void onAfterChildren(IGraph node);
}
