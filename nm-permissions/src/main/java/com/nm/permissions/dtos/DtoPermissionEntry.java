package com.nm.permissions.dtos;

import org.apache.commons.lang3.BooleanUtils;

import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class DtoPermissionEntry implements DtoPermission {

	private ResourceType type;
	private Action action;
	private boolean enable;
	private Boolean selected;
	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public DtoPermissionEntry() {
	}

	public DtoPermissionEntry(ResourceType res, Action a, boolean selected, boolean enable) {
		setAction(a);
		setEnable(enable);
		setSelected(selected);
		setType(res);
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Boolean getSelected() {
		return selected;
	}

	public Boolean getSelectedSafe() {
		return BooleanUtils.isTrue(getSelected());
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "PermissionValueBean [type=" + type + ", action=" + action + ", enable=" + enable + ", selected="
				+ selected + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + level;
		result = prime * result + ((selected == null) ? 0 : selected.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DtoPermissionEntry other = (DtoPermissionEntry) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (enable != other.enable)
			return false;
		if (level != other.level)
			return false;
		if (selected == null) {
			if (other.selected != null)
				return false;
		} else if (!selected.equals(other.selected))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
