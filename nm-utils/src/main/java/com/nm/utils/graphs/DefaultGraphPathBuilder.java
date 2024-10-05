package com.nm.utils.graphs;

import java.util.Arrays;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Nabil
 * 
 */
public class DefaultGraphPathBuilder implements GraphPathBuilder {
	LinkedList<String> all = new LinkedList<String>();

	public void up(int nb) {
		for (int i = 0; i < nb; i++) {
			if (!all.isEmpty()) {
				all.removeLast();
			}
		}
	}

	public void reset() {
		all.clear();
	}

	public int size() {
		return all.size();
	}

	public void parse(String path) {
		path = StringUtils.removeStart(path, "/");
		all.addAll(Arrays.asList(path.split("/")));
	}

	public void down(AbstractGraph graph) {
		all.addLast(graph.nodeId() + "-" + graph.nodetype());
	}

	public String getPath() {
		return "/" + StringUtils.join(all, "/");
	}

	@Override
	public String toString() {
		return this.getPath();
	}
}
