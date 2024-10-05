package com.nm.permissions.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class DtoPermissionsForm implements DtoPermissionGrid {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Set<DtoResource> resources = new HashSet<DtoResource>();
	private Set<Action> actions = new HashSet<Action>();
	private Set<DtoPermissionEntry> values = new HashSet<DtoPermissionEntry>();
	private DtoSubject subject;

	public Long getIdGrid() {
		return getId();
	}

	public void setIdGrid(Long i) {
		setId(i);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DtoSubject getSubject() {
		return subject;
	}

	public void setSubject(DtoSubject subject) {
		this.subject = subject;
	}

	public Set<DtoResource> getResources() {
		return resources;
	}

	public void setResources(Set<DtoResource> resources) {
		this.resources = resources;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	public Set<DtoPermissionEntry> getValues() {
		return values;
	}

	public Collection<DtoPermissionEntry> getBy(ResourceType resource) {
		Collection<DtoPermissionEntry> all = new ArrayList<DtoPermissionEntry>();
		for (DtoPermissionEntry v : values) {
			if (v.getType().equals(resource)) {
				all.add(v);
			}
		}
		return all;
	}

	public Collection<DtoPermissionEntry> getBy(Action action) {
		Collection<DtoPermissionEntry> all = new ArrayList<DtoPermissionEntry>();
		for (DtoPermissionEntry v : values) {
			if (v.getAction().equals(action)) {
				all.add(v);
			}
		}
		return all;
	}

	public Collection<DtoPermissionEntry> getBy(ResourceType resource, Action action) {
		Collection<DtoPermissionEntry> all = new ArrayList<DtoPermissionEntry>();
		for (DtoPermissionEntry v : values) {
			if (v.getAction().equals(action) && v.getType().equals(resource)) {
				all.add(v);
			}
		}
		return all;
	}

	public Optional<DtoPermissionEntry> getFirstBy(ResourceType resource, Action action) {
		Collection<DtoPermissionEntry> entries = getBy(resource, action);
		if (entries.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(entries.iterator().next());
		}
	}

	public void setValues(Set<DtoPermissionEntry> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "DtoPermissionsForm [id=" + id + ", resources=" + resources + ", actions=" + actions + ", values="
				+ values + ", subject=" + subject + "]";
	}

}
