package com.nm.utils.graphs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 
 * @author Nabil
 * 
 */
@JsonIgnoreProperties(value = { "parent" })
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class AbstractGraph {
	@JsonIgnore
	protected AbstractGraph parent;

	public final AbstractGraph getParent() {
		return parent;
	}

	public final void setParent(AbstractGraph parent) {
		this.parent = parent;
	}

	public abstract String nodetype();

	@JsonIgnore
	public abstract String nodeId();

	public abstract void removeChild(AbstractGraph graph);

	public final int level() {
		if (parent()) {
			return getParent().level() + 1;
		} else {
			return 0;
		}
	}

	@JsonIgnore
	public final boolean root() {
		return level() == 0;
	}

	@JsonIgnore
	public final boolean parent() {
		return parent != null;
	}

	public abstract List<? extends AbstractGraph> children();

	public abstract void children(List<? extends AbstractGraph> g);
}
