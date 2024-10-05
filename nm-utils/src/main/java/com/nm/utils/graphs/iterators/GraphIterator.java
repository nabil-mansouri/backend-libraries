package com.nm.utils.graphs.iterators;

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
public interface GraphIterator {

	public void iterate(Object graph, NodeWrapper wrapper, IteratorListenerGeneric listener);

	public void iterate(IGraph graph, IteratorListenerRecurive listener);

	public void iterate(IGraph graph, IteratorListenerGraphInfo listener);

	public void iterate(IGraph graph, IteratorListenerIGraph listener);

	public void iterate(AbstractGraph graph, IteratorListener listener);

	public void iterate(AbstractGraph graph, IteratorListener listener, GraphPathBuilder builder);
}
