package com.nm.utils.graphs.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/***
 * 
 * @author Nabil MANSOURI
 *
 */
@Entity()
@Table(name = "nm_graph_optimized_model_mapping", schema = "mod_utils")
public class GraphOptimizedModelMapping {
	@Id
	@SequenceGenerator(name = "seq_nm_graph_optimized_model_mapping", schema = "mod_utils", sequenceName = "seq_nm_graph_optimized_model_mapping", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_graph_optimized_model_mapping")
	protected Long id;
	@Column(nullable = false)
	private String child;
	@Column(nullable = false)
	private String parent;

	public GraphOptimizedModelMapping() {
	}

	public GraphOptimizedModelMapping(String child, String parent) {
		super();
		this.child = child;
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "GraphOptimizedModelMapping [id=" + id + ", child=" + child + ", parent=" + parent + "]";
	}
}
