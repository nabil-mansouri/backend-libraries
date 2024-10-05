package com.nm.utils.graphs.hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/***
 * 
 * @author Nabil MANSOURI
 *
 */
@MappedSuperclass
@SuppressWarnings("rawtypes")
public abstract class GraphOptimizedModel<T extends GraphOptimizedModel> implements IGraph {
	@Id
	@Column(nullable = false, insertable = true, updatable = false)
	private String uuid = UUIDUtils.UUID(32);
	// PROPERTY ANNOTATION
	private Collection<GraphOptimizedModelMapping> mapping = Lists.newArrayList();
	private Collection<T> allNodes = Lists.newArrayList();
	// TRANSIENT
	@Transient
	private Collection<T> children = Lists.newArrayList();
	// TRANSIENT
	@Transient
	private T parent;

	@SuppressWarnings("unchecked")
	public T addChild(T child) {
		children.add(child);
		child.setParent(this);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public void buildFromState() {
		Map<String, T> all = Maps.newConcurrentMap();
		for (T a : getAllNodes()) {
			all.put(a.getUuid(), a);
		}
		Stack<String> parents = new Stack<String>();
		parents.push(this.uuid);
		while (!parents.isEmpty()) {
			String parent = parents.pop();
			T parentModel = all.get(parent);
			for (GraphOptimizedModelMapping map : mapping) {
				if (map.getParent().equals(parent)) {
					T childModel = all.get(map.getChild());
					parentModel.addChild(childModel);
					parents.push(map.getChild());
				}
			}
		}
	}

	public final void buildState() {
		clear();
		GraphIteratorBuilder.buildDeep().iterate(this, new IteratorListenerGraphInfo() {

			@SuppressWarnings("unchecked")
			public void onFounded(GraphInfo node) {
				getAllNodes().add((T) node.getCurrent());
				if (node.getParent() != null) {
					String childU = node.getCurrent().uuid();
					GraphInfo paInfo = node.getParent();
					String paUid = paInfo.getCurrent().uuid();
					mapping.add(new GraphOptimizedModelMapping(childU, paUid));
				}
			}
		});
	}

	public List<? extends IGraph> childrens() {
		return (List<? extends IGraph>) getChildren();
	}

	public void clear() {
		mapping.clear();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphOptimizedModel other = (GraphOptimizedModel) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Access(AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	public Collection<T> getAllNodes() {
		return allNodes;
	}

	public Collection<T> getChildren() {
		return children;
	}

	@Access(AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	public Collection<GraphOptimizedModelMapping> getMapping() {
		return mapping;
	}

	public T getParent() {
		return parent;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	public T removeChild(T child) {
		children.remove(child);
		child.setParent(null);
		return (T) this;
	}

	public boolean root() {
		return this.parent == null;
	}

	public void setAllNodes(Collection<T> allNodes) {
		this.allNodes = allNodes;
	}

	public void setChildren(Collection<T> children) {
		this.children = children;
	}

	public void setMapping(Collection<GraphOptimizedModelMapping> mapping) {
		this.mapping = mapping;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String uuid() {
		return getUuid();
	}

}
