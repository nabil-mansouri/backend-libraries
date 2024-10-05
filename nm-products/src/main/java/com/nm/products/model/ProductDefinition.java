package com.nm.products.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.AssertTrue;

import com.nm.cms.models.CmsContentsComposed;
import com.nm.products.constants.ProductState;
import com.nm.products.constants.ProductType;
import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_definition")
public class ProductDefinition implements Serializable, IGraph {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_product_definition", sequenceName = "seq_product_definition", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_definition")
	protected Long id;

	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected ProductType type = ProductType.Product;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected ProductState state = ProductState.UnPublish;
	@Column(nullable = false)
	protected Date stateDate = new Date();
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Category> categories = new HashSet<Category>(0);
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<ProductDefinitionIngredient> ingredients = new HashSet<ProductDefinitionIngredient>();
	@JoinTable(name = "nm_product_definition_parts")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductDefinitionPart> parts = new ArrayList<ProductDefinitionPart>(0);
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private CmsContentsComposed content = new CmsContentsComposed();

	public boolean root() {
		return false;
	}

	public ProductState getState() {
		return state;
	}

	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public void setState(ProductState state) {
		this.state = state;
	}

	public CmsContentsComposed getContent() {
		return content;
	}

	public void setContent(CmsContentsComposed content) {
		this.content = content;
	}

	public void addIngredient(ProductDefinitionIngredient ing) {
		this.ingredients.add(ing);
	}

	public void addCategory(Category category) {
		this.categories.add(category);
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public void addPart(ProductDefinitionPart productDefinitionPart) {
		this.parts.add(productDefinitionPart);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDefinition other = (ProductDefinition) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (parts == null) {
			if (other.parts != null)
				return false;
		} else if (!parts.equals(other.parts))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public Collection<ProductDefinitionIngredient> getIngredients() {
		return ingredients;
	}

	public Collection<Category> getCategories() {
		return categories;
	}

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public List<ProductDefinitionPart> getParts() {
		return parts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((parts == null) ? 0 : parts.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public void removeAvailableIngredient(IngredientDefinition availableIngredientGroup) {
		this.ingredients.remove(availableIngredientGroup);
	}

	public void removeProductDefinitionPart(ProductDefinitionPart productDefinitionPart) {
		this.parts.remove(productDefinitionPart);
	}

	public void setIngredients(Collection<ProductDefinitionIngredient> ings) {
		this.ingredients = ings;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setParts(List<ProductDefinitionPart> productDefinitionParts) {
		this.parts = productDefinitionParts;
	}

	@AssertTrue(message = "product.parts.recursive")
	protected boolean isValidRecursive() {
		Stack<ProductDefinition> stack = new Stack<ProductDefinition>();
		//
		stack.push(this);
		boolean first = true;
		while (!stack.isEmpty()) {
			ProductDefinition top = stack.pop();
			if (this.equals(top) && !first) {
				return false;
			}
			first = false;
			for (ProductDefinitionPart part : top.getParts()) {
				for (ProductDefinition child : part.getProducts()) {
					stack.push(child);
				}
			}
		}
		return true;
	}

	public String uuid() {
		return getClass().getSimpleName() + "/" + getId();
	}

	public List<? extends IGraph> childrens() {
		List<IGraph> all = new ArrayList<IGraph>();
		for (ProductDefinitionPart p : this.parts) {
			all.add(p);
		}
		for (ProductDefinitionIngredient p : this.ingredients) {
			all.add(p.getIngredient());
		}
		return all;
	}

	public void remove(Category bean) {
		this.getCategories().remove(bean);
	}
}
