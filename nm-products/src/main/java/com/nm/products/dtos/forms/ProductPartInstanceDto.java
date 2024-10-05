package com.nm.products.dtos.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ProductPartInstanceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;

	private String name;
	private boolean mandatory;
	private ProductInstanceDto selected;

	public boolean hasSelected() {
		return this.selected != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPartInstanceDto other = (ProductPartInstanceDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mandatory != other.mandatory)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (selected == null) {
			if (other.selected != null)
				return false;
		} else if (!selected.equals(other.selected))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProductInstanceDto getSelected() {
		return selected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (mandatory ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((selected == null) ? 0 : selected.hashCode());
		return result;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSelected(ProductInstanceDto selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "ProductPartInstanceView [id=" + id + ", name=" + name + ", mandatory=" + mandatory + ", selected="
				+ selected + "]";
	}
}
