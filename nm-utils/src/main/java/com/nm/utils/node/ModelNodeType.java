package com.nm.utils.node;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Cacheable()
@Cache(region = "entity_node_model_type", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "node_model_type", schema = "mod_utils", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }) )
public class ModelNodeType {

	@Id
	@SequenceGenerator(name = "seq_node_model_type", schema = "mod_utils", sequenceName = "seq_node_model_type", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_node_model_type")
	private Long id;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String name;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String simple;

	public String getSimple() {
		return simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
