package com.nm.permissions.dtos;

import java.util.HashSet;
import java.util.Set;

import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class DtoResourceDefault implements DtoResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private ResourceType type;
	private Set<Action> action = new HashSet<Action>();

	public String getIdResource() {
		return getId();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Set<Action> getAction() {
		return action;
	}

	public ResourceType getType() {
		return type;
	}

	public void setAction(Set<Action> action) {
		this.action = action;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

}
