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
public class GraphLevelIterator implements GraphIterator {

	private final int level;

	public GraphLevelIterator(int level) {
		this.level = level;
	}

	public void iterate(IGraph graph, IteratorListenerIGraph listener) {
		throw new NotYetImplementedException();
	}

	public void iterate(IGraph graph, IteratorListenerRecurive listener) {
		throw new NotYetImplementedException();
	}

	public void iterate(IGraph graph, IteratorListenerGraphInfo listener) {
		throw new NotYetImplementedException();
	}

	private IteratorListener build(final IteratorListener listener) {
		return new IteratorListener() {

			public boolean stop() {
				return false;
			}

			public boolean onFounded(AbstractGraph node) {
				if (node.level() == level) {
					listener.onFounded(node);
				}
				if (node.level() >= level) {
					return false;
				} else {
					return true;
				}
			}

			public boolean doSetParent() {
				return false;
			}
		};
	}

	public void iterate(Object graph, NodeWrapper wrapper, IteratorListenerGeneric listener) {
		throw new NotYetImplementedException();
	}

	public void iterate(AbstractGraph graph, final IteratorListener listener) {
		GraphIteratorBuilder.buildDeep().iterate(graph, build(listener));
	}

	public void iterate(AbstractGraph graph, IteratorListener listener, GraphPathBuilder builder) {
		GraphIteratorBuilder.buildDeep().iterate(graph, build(listener), builder);
	}

}
