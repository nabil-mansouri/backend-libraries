package com.nm.utils.graphs.iterators;

import org.hibernate.cfg.NotYetImplementedException;

import com.nm.utils.graphs.AbstractGraph;
import com.nm.utils.graphs.GraphPathBuilder;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.NodeWrapper;
import com.nm.utils.graphs.listeners.IteratorListener;
import com.nm.utils.graphs.listeners.IteratorListenerGeneric;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;
import com.nm.utils.graphs.listeners.IteratorListenerIGraph;
import com.nm.utils.graphs.listeners.IteratorListenerRecurive;

/**
 * 
 * @author Nabil
 * 
 */
public class GraphParentsIterator implements GraphIterator {

	public void iterate(AbstractGraph graph, IteratorListener listener) {
		AbstractGraph current = graph;
		while (current != null) {
			if (listener.onFounded(current) && current.parent()) {
				current = current.getParent();
			} else {
				current = null;
			}
			if (listener.stop()) {
				current = null;
			}
		}
	}
	public void iterate(IGraph graph, IteratorListenerGraphInfo listener) {
		throw new NotYetImplementedException();
	}
	public void iterate(IGraph graph, IteratorListenerRecurive listener) {
		throw new NotYetImplementedException();
	}

	public void iterate(Object graph, NodeWrapper wrapper, IteratorListenerGeneric listener) {
		throw new NotYetImplementedException();
	}

	public void iterate(IGraph graph, IteratorListenerIGraph listener) {
		throw new NotYetImplementedException();
	}

	public void iterate(AbstractGraph graph, IteratorListener listener, GraphPathBuilder builder) {
		iterate(graph, listener);
	}
}
