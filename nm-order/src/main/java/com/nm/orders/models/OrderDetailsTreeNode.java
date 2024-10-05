package com.nm.orders.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.orders.constants.OrderTreeNodeType;
import com.nm.utils.graphs.hibernate.GraphOptimizedModel;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_order_details_tree_node_tree_node")
public class OrderDetailsTreeNode extends GraphOptimizedModel<OrderDetailsTreeNode>implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column()
	protected String name;
	@Column(nullable = false)
	protected Double amount;
	@Column(nullable = false)
	protected Long reference;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	protected OrderTreeNodeType type;

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrderTreeNodeType getType() {
		return type;
	}

	public void setType(OrderTreeNodeType type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
