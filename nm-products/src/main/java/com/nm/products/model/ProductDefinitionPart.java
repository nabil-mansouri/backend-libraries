package com.nm.products.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_part_definition")
public class ProductDefinitionPart implements Serializable, IGraph {

	private static final long serialVersionUID = 5404910820264439120L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_product_part_definition", sequenceName = "seq_product_part_definition", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_part_definition")
	private Long id;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Boolean mandatory = true;
	@Size(min = 1)
	@ManyToMany(fetch = FetchType.LAZY)
	private List<ProductDefinition> products = new ArrayList<ProductDefinition>();

	public boolean root() {
		return false;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public void addProductDefinition(ProductDefinition productDefinition) {
		this.products.add(productDefinition);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<ProductDefinition> getProducts() {
		return products;
	}

	public List<ProductDefinition> getProductsAsList() {
		return this.products;
	}

	public void removeProductDefinition(ProductDefinition productDefinition) {
		this.products.remove(productDefinition);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductDefinitions(List<ProductDefinition> productDefinitions) {
		this.products = productDefinitions;
	}

	public List<? extends IGraph> childrens() {
		return this.products;
	}
	public String uuid() {
		return getClass().getSimpleName() + "/" + getId();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProductDefinitionPart other = (ProductDefinitionPart) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
