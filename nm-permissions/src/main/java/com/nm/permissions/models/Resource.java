package com.nm.permissions.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;
import com.nm.utils.graphs.hibernate.GraphOptimizedModel;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity(name = "ResourcePermission")
@Table(schema = "mod_perm", name = "perm_resource", uniqueConstraints = @UniqueConstraint(columnNames = { "type" }) )
public class Resource extends GraphOptimizedModel<Resource> {

	@Type(type = EnumHibernateType.EE)
	@Column(nullable = false, name = "type", columnDefinition = "VARCHAR", length = 256)
	private ResourceType type;
	@Column(name = "actions")
	@Type(type = EnumHibernateType.EE)
	@ElementCollection(targetClass = Action.class)
	@CollectionTable(schema = "mod_perm", name = "resource_action_list", joinColumns = @JoinColumn(name = "id") )
	private Set<Action> available = new HashSet<Action>();

	public Set<Action> getAvailable() {
		return available;
	}

	public ResourceType getType() {
		return type;
	}

	public void setAvailable(Set<Action> available) {
		this.available = available;
	}

	public Resource setType(ResourceType type) {
		this.type = type;
		return this;
	}

}
