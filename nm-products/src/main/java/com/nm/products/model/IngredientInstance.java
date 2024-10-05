package com.nm.products.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.utils.I18nUtils;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_ingredient_instance")
public class IngredientInstance implements Serializable {

	private static final long serialVersionUID = -4629117280465782791L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_product_ingredient_instance", sequenceName = "seq_product_ingredient_instance", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_ingredient_instance")
	private Long id;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String name;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String image;

	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();

	@Column(nullable = false)
	@UpdateTimestamp
	private Date updatedAt = new Date();

	public void addName(String locale, String name) {
		setName(I18nUtils.addLangage(locale, getName(), name));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredientInstance other = (IngredientInstance) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	@NotNull(message = "{product.ingredient.image.notNull}")
	@Size(max = 300, message = "{product.ingredient.image.size}")
	public String getImage() {
		return image;
	}

	@NotNull(message = "{product.ingredient.name.notNull}")
	@Size(max = 50, message = "{product.ingredient.name.size}")
	public String getName() {
		return name;
	}

	public String getName(String locale) {
		return I18nUtils.getByLangage(locale, getName());
	}

	public List<String> getNameLangage() {
		return I18nUtils.getLangages(getName());
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", description=" + ", image=" + image + ", motcle=" + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

	@PreUpdate
	public void onUpdate() {
		this.setUpdatedAt(new Date());
	}
}
