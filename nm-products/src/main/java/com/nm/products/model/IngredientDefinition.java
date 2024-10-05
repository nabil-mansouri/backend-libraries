package com.nm.products.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.cms.models.CmsContentsComposed;
import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_ingredient_definition")
public class IngredientDefinition implements Serializable, IGraph {

	private static final long serialVersionUID = -4629117280465782791L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_product_ingredient_definition", sequenceName = "seq_product_ingredient_definition", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_ingredient_definition")
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private CmsContentsComposed content = new CmsContentsComposed();
	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();

	@Column(nullable = false)
	@UpdateTimestamp
	private Date updatedAt = new Date();

	public String uuid() {
		return getClass().getSimpleName() + "/" + getId();
	}

	public CmsContentsComposed getContent() {
		return content;
	}

	public void setContent(CmsContentsComposed content) {
		this.content = content;
	}

	public boolean root() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredientDefinition other = (IngredientDefinition) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PreUpdate
	public void onUpdate() {
		this.setUpdatedAt(new Date());
	}

	public List<? extends IGraph> childrens() {
		return new ArrayList<IGraph>();
	}

}
