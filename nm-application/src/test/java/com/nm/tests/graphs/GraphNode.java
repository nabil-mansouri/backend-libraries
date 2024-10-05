package com.nm.tests.graphs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.nm.utils.graphs.hibernate.GraphOptimizedModel;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_test_graph_node")
@PrimaryKeyJoinColumn(name = "uuid")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class GraphNode extends GraphOptimizedModel<GraphNode> {

	@Column(nullable = false)
	protected Double amount;

	//
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
