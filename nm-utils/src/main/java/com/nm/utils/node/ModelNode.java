package com.nm.utils.node;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "node_model", schema = "mod_utils")
public class ModelNode {
	public static final String INSERT_QUERY = "INSERT INTO mod_utils.node_model (id,dead,type_id) VALUES (?,?,?)";
	public static final String DEAD_QUERY = "UPDATE mod_utils.node_model SET dead = true WHERE id = ?";
	@Id
	private BigInteger id;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ModelNodeType type;
	@Column(nullable = false)
	private boolean dead;

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public ModelNodeType getType() {
		return type;
	}

	public void setType(ModelNodeType type) {
		this.type = type;
	}

}
