package com.nm.products.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.nm.cms.models.CmsContentsComposed;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_category")
public class Category implements Serializable {

	private static final long serialVersionUID = -5259645752344689165L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_product_category", sequenceName = "seq_product_category", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_category")
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private CmsContentsComposed content = new CmsContentsComposed();

	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();

	@UpdateTimestamp
	@Column(nullable = false)
	private Date updatedAt = new Date();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	private Collection<ProductDefinition> productDefinitions = new HashSet<ProductDefinition>(0);
	@OneToMany()
	@JoinTable(name = "nm_product_category_children")
	private Collection<Category> children = new HashSet<Category>();

	public boolean addProduct(ProductDefinition e) {
		return productDefinitions.add(e);
	}

	public boolean add(Category e) {
		return children.add(e);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	public Collection<ProductDefinition> getProductDefinitions() {
		return productDefinitions;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean hasProducts() {
		return productDefinitions.isEmpty();
	}

	public boolean removeProduct(ProductDefinition o) {
		return productDefinitions.remove(o);
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProductDefinitions(Collection<ProductDefinition> productDefinitions) {
		this.productDefinitions = productDefinitions;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int size() {
		return productDefinitions.size();
	}

	public CmsContentsComposed getContent() {
		return content;
	}

	public void setContent(CmsContentsComposed content) {
		this.content = content;
	}

	public Collection<Category> getChildren() {
		return children;
	}

	public void setChildren(Collection<Category> children) {
		this.children = children;
	}

}
