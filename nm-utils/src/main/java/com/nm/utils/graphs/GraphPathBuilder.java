package com.nm.utils.graphs;

/**
 * 
 * @author Nabil
 * 
 */
public interface GraphPathBuilder {
	public void up(int nb);

	public void down(AbstractGraph graph);

	public void parse(String path);

	public String getPath();
	
	public int size();
	
	public void reset();
}
