package com.nm.products.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_definition_ingredients")
public class ProductDefinitionIngredient implements Serializable {

	private static final long serialVersionUID = 5404910820264439120L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProductDefinitionIngredient() {
	}

	public ProductDefinitionIngredient(Boolean mandatory, IngredientDefinition ingredient) {
		super();
		this.mandatory = mandatory;
		this.ingredient = ingredient;
	}

	@Id
	@SequenceGenerator(name = "seq_product_definition_ingredients", sequenceName = "seq_product_definition_ingredients", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_definition_ingredients")
	private Long id;
	@Column(nullable = false)
	private Boolean mandatory = true;
	@ManyToOne(optional = false)
	private IngredientDefinition ingredient;
	@Column(nullable = false, insertable = false, updatable = false, name = "ingredient_id")
	private Long ingredientId;

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDefinitionIngredient other = (ProductDefinitionIngredient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ingredient == null) {
			if (other.ingredient != null)
				return false;
		} else if (!ingredient.equals(other.ingredient))
			return false;
		if (mandatory == null) {
			if (other.mandatory != null)
				return false;
		} else if (!mandatory.equals(other.mandatory))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public IngredientDefinition getIngredient() {
		return ingredient;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
		result = prime * result + ((mandatory == null) ? 0 : mandatory.hashCode());
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIngredient(IngredientDefinition ingredient) {
		this.ingredient = ingredient;
		if (ingredient != null) {
			this.setIngredientId(ingredient.getId());
		} else {
			this.setIngredientId(null);
		}
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Override
	public String toString() {
		return "ProductDefinitionIngredient [id=" + id + ", mandatory=" + mandatory + ", ingredient=" + ingredient
				+ "]";
	}

}
