package com.nm.datas.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "app_memory", schema = "mod_data", uniqueConstraints = @UniqueConstraint(columnNames = { "scope", "category", "key" }) )
public class AppMemory {
	@Id
	@SequenceGenerator(name = "app_memory_seq", schema = "mod_data", sequenceName = "app_memory_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_memory_seq")
	private Long id;
	@Column(updatable = false)
	private Date created = new Date();
	@UpdateTimestamp
	private Date updated = new Date();
	@Column(nullable = false, columnDefinition = "TEXT")
	private String scope;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private String key;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String value;

	public String getScope() {
		return scope;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Long getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
