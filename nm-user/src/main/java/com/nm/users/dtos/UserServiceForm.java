package com.nm.users.dtos;

import java.io.Serializable;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserServiceForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (selected ? 1231 : 1237);
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
		UserServiceForm other = (UserServiceForm) obj;
		if (selected != other.selected)
			return false;
		return true;
	}

}
