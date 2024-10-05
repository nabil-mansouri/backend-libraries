package com.nm.utils.graphs;

/**
 * 
 * @author nabilmansouri
 *
 */
public class GraphInfo {
	private IGraph current;
	private GraphInfo parent;

	public int level() {
		if (hasParent()) {
			return 1 + getParent().level();
		} else {
			return 0;
		}
	}

	public GraphInfo(IGraph current) {
		super();
		this.current = current;
	}

	@SuppressWarnings("unchecked")
	public <T extends IGraph> T getCurrent(Class<T> clazz) {
		return (T) current;
	}

	public boolean isCurrent(Class<?> clazz) {
		return clazz.isInstance(current);
	}

	@SuppressWarnings("unchecked")
	public <T extends IGraph> T getParent(Class<T> clazz) {
		return (T) parent;
	}

	public GraphInfo(IGraph current, GraphInfo parent) {
		super();
		this.current = current;
		this.parent = parent;
	}

	public boolean hasParent() {
		return this.parent != null;
	}

	public IGraph getCurrent() {
		return current;
	}

	public void setCurrent(IGraph current) {
		this.current = current;
	}

	public GraphInfo getParent() {
		return parent;
	}

	public void setParent(GraphInfo parent) {
		this.parent = parent;
	}

}
