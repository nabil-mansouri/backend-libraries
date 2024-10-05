package com.nm.permissions.models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.api.client.util.Sets;
import com.google.common.base.Objects;
import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;
import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema="mod_perm",name = "perm_permission_grid")
public class PermissionGrid extends ModelTimeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "seq_permission_grid", sequenceName = "seq_permission_grid", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_permission_grid")
	private Long id;
	@OneToOne(optional = false)
	private Subject subject;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "grid")
	private Collection<Permission> permissions = Sets.newHashSet();

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}

	public Long getId() {
		return id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
		subject.setGrid(this);
	}

	public Permission find(Action action, Resource resource) {
		return find(action, resource.getType());
	}

	public Permission find(Action action, ResourceType type) {
		for (Permission p : this.permissions) {
			if (Objects.equal(p.getResource().getType(), type)) {
				if (Objects.equal(p.getAction(), action)) {
					return p;
				}
			}
		}
		return null;
	}

	public void add(Permission perm) {
		this.permissions.add(perm);
		perm.setGrid(this);
	}

}
