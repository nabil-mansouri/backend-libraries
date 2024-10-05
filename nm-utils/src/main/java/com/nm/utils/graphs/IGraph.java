package com.nm.utils.graphs;

import java.util.List;

/**
 * 
 * @author Nabil
 * 
 */
public interface IGraph {
	public abstract List<? extends IGraph> childrens();

	public boolean root();

	public String uuid();
}
