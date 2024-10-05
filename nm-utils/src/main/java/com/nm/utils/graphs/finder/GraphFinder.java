package com.nm.utils.graphs.finder;

import java.util.Arrays;
import java.util.Collection;

import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.iterators.GraphIterator;
import com.nm.utils.graphs.listeners.IteratorListenerIGraph;

/**
 * 
 * @author nabilmansouri
 *
 */
public class GraphFinder {
	private final GraphIterator it;
	private GraphFinderPath<IGraph> result;
	private GraphFinderPathList<IGraph> results;

	public GraphFinder(GraphIterator it) {
		super();
		this.it = it;
	}

	public GraphFinderPathList<IGraph> findAll(IGraph graph, final Collection<IGraphIdentifier> id) {
		results = new GraphFinderPathList<IGraph>();
		it.iterate(graph, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				if (node instanceof IGraphIdentifiable) {
					IGraphIdentifiable n = (IGraphIdentifiable) node;
					if (GraphFinderHelper.filter(n, id)) {
						results.create().setFounded(node);
					}
				}
			}
		});
		return results;
	}

	public GraphFinderPathList<IGraph> findAll(IGraph graph, IGraphIdentifier id) {
		return findAll(graph, Arrays.asList(id));
	}

	public GraphFinderPathList<IGraph> findAllUUID(IGraph graph, final Collection<String> id) {
		results = new GraphFinderPathList<IGraph>();
		it.iterate(graph, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				if (id.contains(node.uuid())) {
					results.create().setFounded(node);
				}
			}
		});
		return results;
	}

	public GraphFinderPathList<IGraph> findAllUUID(IGraph graph, String id) {
		return findAllUUID(graph, Arrays.asList(id));
	}

	public GraphFinderPath<IGraph> findFirst(IGraph graph, final Collection<IGraphIdentifier> id) {
		result = new GraphFinderPath<IGraph>();
		it.iterate(graph, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				if (node instanceof IGraphIdentifiable) {
					IGraphIdentifiable n = (IGraphIdentifiable) node;
					if (GraphFinderHelper.filter(n, id)) {
						result.setFounded(node);
					}
				}
			}
		});
		return result;
	}

	public GraphFinderPath<IGraph> findFirst(IGraph graph, final IGraphIdentifier id) {
		return findFirst(graph, Arrays.asList(id));
	}

	public GraphFinderPath<IGraph> findFirstUUID(IGraph graph, final Collection<String> uuid) {
		result = new GraphFinderPath<IGraph>();
		it.iterate(graph, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				if (uuid.contains(node.uuid())) {
					result.setFounded(node);
				}
			}
		});
		return result;
	}

	public GraphFinderPath<IGraph> findFirstUUID(IGraph graph, final String uuid) {
		return findFirstUUID(graph, Arrays.asList(uuid));
	}

	public GraphFinderPath<IGraph> findLastUUID(IGraph graph, final Collection<String> id) {
		result = new GraphFinderPath<IGraph>();
		it.iterate(graph, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				if (id.contains(node.uuid())) {
					result.setFounded(node);
				}
			}
		});
		return result;
	}

	public GraphFinderPath<IGraph> findLastUUID(IGraph graph, final String id) {
		return findLastUUID(graph, Arrays.asList(id));
	}

	public GraphFinderPath<IGraph> findLast(IGraph graph, final Collection<IGraphIdentifier> id) {
		result = new GraphFinderPath<IGraph>();
		it.iterate(graph, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				if (node instanceof IGraphIdentifiable) {
					IGraphIdentifiable n = (IGraphIdentifiable) node;
					if (GraphFinderHelper.filter(n, id) && !result.founded()) {
						result.setFounded(node);
					}
				}
			}
		});
		return result;
	}

	public GraphFinderPath<IGraph> findLast(IGraph graph, final IGraphIdentifier id) {
		return findLast(graph, Arrays.asList(id));
	}

}
