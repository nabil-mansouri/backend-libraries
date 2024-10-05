package com.nm.users.dtos;

import java.io.Serializable;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserGroupForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private int nbUsers;
	private boolean loading;
	private boolean edit;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupForm other = (UserGroupForm) obj;
		if (edit != other.edit)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loading != other.loading)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nbUsers != other.nbUsers)
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNbUsers() {
		return nbUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (edit ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (loading ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nbUsers;
		return result;
	}

	public boolean isEdit() {
		return edit;
	}

	public boolean isLoading() {
		return loading;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNbUsers(int nbUsers) {
		this.nbUsers = nbUsers;
	}

}
