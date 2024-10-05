package com.nm.utils.graphs.listeners;

import com.nm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
public class DefaultIteratorListener implements IteratorListener {

	public boolean onFounded(AbstractGraph node) {
		return true;
	}

	public boolean stop() {
		return false;
	}

	public boolean doSetParent() {
		return true;
	}

}
