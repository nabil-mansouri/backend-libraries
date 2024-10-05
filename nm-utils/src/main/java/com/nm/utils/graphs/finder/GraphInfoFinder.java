package com.nm.utils.graphs.finder;

import java.util.Arrays;
import java.util.Collection;

import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.iterators.GraphIterator;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/**
 * 
 * @author nabilmansouri
 *
 */
public class GraphInfoFinder {
	private final GraphIterator it;
	private GraphFinderPath<GraphInfo> result;
	private GraphFinderPathList<GraphInfo> results;

	public GraphInfoFinder(GraphIterator it) {
		super();
		this.it = it;
	}

	public GraphFinderPathList<GraphInfo> findAll(IGraph graph, final Collection<IGraphIdentifier> id) {
		results = new GraphFinderPathList<GraphInfo>();
		it.iterate(graph, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				if (node.getCurrent() instanceof IGraphIdentifiable) {
					IGraphIdentifiable n = (IGraphIdentifiable) node.getCurrent();
					if (GraphFinderHelper.filter(n, id)) {
						results.create().setFounded(node);
					}
				}
			}
		});
		return results;
	}

	public GraphFinderPathList<GraphInfo> findAll(IGraph graph, IGraphIdentifier id) {
		return findAll(graph, Arrays.asList(id));
	}

	public GraphFinderPathList<GraphInfo> findAllUUID(IGraph graph, final Collection<String> id) {
		results = new GraphFinderPathList<GraphInfo>();
		it.iterate(graph, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				if (id.contains(node.getCurrent().uuid())) {
					results.create().setFounded(node);
				}
			}
		});
		return results;
	}

	public GraphFinderPathList<GraphInfo> findAllUUID(IGraph graph, String id) {
		return findAllUUID(graph, Arrays.asList(id));
	}

	public GraphFinderPath<GraphInfo> findFirst(IGraph graph, final Collection<IGraphIdentifier> id) {
		result = new GraphFinderPath<GraphInfo>();
		it.iterate(graph, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				if (node.getCurrent() instanceof IGraphIdentifiable) {
					IGraphIdentifiable n = (IGraphIdentifiable) node.getCurrent();
					if (GraphFinderHelper.filter(n, id)) {
						result.setFounded(node);
					}
				}
			}
		});
		return result;
	}

	public GraphFinderPath<GraphInfo> findFirst(IGraph graph, final IGraphIdentifier id) {
		return findFirst(graph, Arrays.asList(id));
	}

	public GraphFinderPath<GraphInfo> findFirstUUID(IGraph graph, final Collection<String> uuid) {
		result = new GraphFinderPath<GraphInfo>();
		it.iterate(graph, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				if (uuid.contains(node.getCurrent().uuid())) {
					result.setFounded(node);
				}
			}
		});
		return result;
	}

	public GraphFinderPath<GraphInfo> findFirstUUID(IGraph graph, final String uuid) {
		return findFirstUUID(graph, Arrays.asList(uuid));
	}

	public GraphFinderPath<GraphInfo> findLastUUID(IGraph graph, final Collection<String> id) {
		result = new GraphFinderPath<GraphInfo>();
		it.iterate(graph, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				if (id.contains(node.getCurrent().uuid())) {
					result.setFounded(node);
				}
			}
		});
		return result;
	}

	public GraphFinderPath<GraphInfo> findLastUUID(IGraph graph, final String id) {
		return findLastUUID(graph, Arrays.asList(id));
	}

	public GraphFinderPath<GraphInfo> findLast(IGraph graph, final Collection<IGraphIdentifier> id) {
		result = new GraphFinderPath<GraphInfo>();
		it.iterate(graph, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				if (node.getCurrent() instanceof IGraphIdentifiable) {
					IGraphIdentifiable n = (IGraphIdentifiable) node.getCurrent();
					if (GraphFinderHelper.filter(n, id) && !result.founded()) {
						result.setFounded(node);
					}
				}
			}
		});
		return result;
	}

	public GraphFinderPath<GraphInfo> findLast(IGraph graph, final IGraphIdentifier id) {
		return findLast(graph, Arrays.asList(id));
	}

}
