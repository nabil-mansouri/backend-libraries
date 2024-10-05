package com.nm.utils.graphs.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.iterators.GraphIterator;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/**
 * 
 * @author nabilmansouri
 *
 */
public class GraphManager {
	private final GraphIterator it;

	public GraphManager(GraphIterator it) {
		super();
		this.it = it;
	}

	public Collection<IGraph> removeAllUUID(final IGraph graph, final Collection<String> id) {
		final Collection<IGraph> all = new ArrayList<IGraph>();
		it.iterate(graph, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				if (id.contains(node.getCurrent().uuid())) {
					all.add(node.getCurrent());
					if (node.hasParent()) {
						List<? extends IGraph> children = node.getParent().getCurrent().childrens();
						List<IGraph> toRemove = new ArrayList<IGraph>();
						for (IGraph i : children) {
							if (i.equals(node.getCurrent())) {
								toRemove.add(i);
							}
						}
						children.removeAll(toRemove);
					}
				}
			}
		});
		return all;
	}

	public Collection<IGraph> removeAllUUID(IGraph graph, String id) {
		return removeAllUUID(graph, Arrays.asList(id));
	}

	public Collection<IGraph> remove(GraphInfo node) {
		final Collection<IGraph> all = new ArrayList<IGraph>();
		all.add(node.getCurrent());
		if (node.hasParent()) {
			List<? extends IGraph> children = node.getParent().getCurrent().childrens();
			List<IGraph> toRemove = new ArrayList<IGraph>();
			for (IGraph i : children) {
				if (i.equals(node.getCurrent())) {
					toRemove.add(i);
				}
			}
			children.removeAll(toRemove);
		}
		return all;
	}

}
