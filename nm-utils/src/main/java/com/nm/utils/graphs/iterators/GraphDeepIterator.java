package com.nm.utils.graphs.iterators;

import java.util.Collection;
import java.util.Stack;

import com.nm.utils.graphs.AbstractGraph;
import com.nm.utils.graphs.DefaultGraphPathBuilder;
import com.nm.utils.graphs.GraphInfo;
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
public class GraphDeepIterator implements GraphIterator {

	public void iterate(AbstractGraph graph, IteratorListener listener) {
		iterate(graph, listener, new DefaultGraphPathBuilder());
	}

	public void iterate(IGraph graph, IteratorListenerGraphInfo listener) {
		Stack<GraphInfo> stack = new Stack<GraphInfo>();
		stack.push(new GraphInfo(graph));
		while (!stack.isEmpty()) {
			GraphInfo current = stack.pop();
			listener.onFounded(current);
			for (IGraph child : current.getCurrent().childrens()) {
				stack.push(new GraphInfo(child, current));
			}
		}
	}

	public void iterate(Object graph, NodeWrapper wrapper, IteratorListenerGeneric listener) {
		Stack<Object> stack = new Stack<Object>();
		stack.push(graph);
		while (!stack.isEmpty()) {
			Object current = stack.pop();
			listener.onFounded(current);
			Collection<? extends IGraph> childrens = wrapper.wrap(current).childrens();
			for (IGraph child : childrens) {
				stack.push(child);
			}
		}
	}

	public void iterate(IGraph graph, IteratorListenerIGraph listener) {
		Stack<IGraph> stack = new Stack<IGraph>();
		stack.push(graph);
		while (!stack.isEmpty()) {
			IGraph current = stack.pop();
			listener.onFounded(current);
			for (IGraph child : current.childrens()) {
				stack.push(child);
			}
		}
	}

	public void iterate(IGraph graph, IteratorListenerRecurive listener) {
		listener.onBeforeChildren(graph);
		for (IGraph child : graph.childrens()) {
			iterate(child, listener);
		}
		listener.onAfterChildren(graph);
	}

	public void iterate(AbstractGraph graph, IteratorListener listener, GraphPathBuilder builder) {
		Stack<AbstractGraph> stack = new Stack<AbstractGraph>();
		stack.push(graph);
		int lastLevel = -1;
		builder.reset();
		while (!stack.isEmpty()) {
			AbstractGraph current = stack.pop();
			//
			int currentLevel = current.level();
			if (lastLevel < currentLevel) {// DO NOTHING
			} else if (currentLevel < lastLevel) {
				builder.up(lastLevel - currentLevel + 1);
			} else if (currentLevel == lastLevel) {
				builder.up(1);
			}
			builder.down(current);
			//
			if (listener.onFounded(current)) {
				for (AbstractGraph child : current.children()) {
					if (listener.doSetParent()) {
						child.setParent(current);
					}
					stack.push(child);
				}
			}
			if (listener.stop()) {
				stack.clear();
			}
			lastLevel = currentLevel;
		}
	}

}
