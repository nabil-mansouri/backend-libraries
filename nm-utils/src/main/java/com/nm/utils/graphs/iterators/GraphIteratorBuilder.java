package com.nm.utils.graphs.iterators;

/**
 * 
 * @author Nabil
 * 
 */
public class GraphIteratorBuilder {

	public static GraphIterator buildDeep() {
		return new GraphDeepIterator();
	}

	public static GraphIterator buildBreadth() {
		return new GraphBreadthIterator();
	}

	public static GraphIterator buildParents() {
		return new GraphParentsIterator();
	}

	public static GraphIterator buildLevel(int level) {
		return new GraphLevelIterator(level);
	}
}
