package com.nm.products.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.nm.products.constants.ProductType;
import com.nm.utils.I18nUtils;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_instance")
public class ProductInstance implements Serializable {

	private static final long serialVersionUID = 1978550118107172866L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_product_instance", sequenceName = "seq_product_instance", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_instance")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected ProductType type = ProductType.Product;
	@Column(columnDefinition = "TEXT", nullable = false)
	protected String name;
	@Column(columnDefinition = "TEXT", nullable = false)
	protected String image;
	@ElementCollection
	@CollectionTable(name = "nm_product_instance_images")
	@OrderColumn
	private Collection<String> images = new HashSet<String>(0);
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Collection<IngredientInstance> excluded = new ArrayList<IngredientInstance>();
	@NotNull
	protected Double price = 0d;
	@NotNull
	protected Long idProduct;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProductInstancePart> parts = new ArrayList<ProductInstancePart>(0);

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public List<ProductInstancePart> getParts() {
		return parts;
	}

	public void setParts(List<ProductInstancePart> parts) {
		this.parts = parts;
	}

	public Date getCreated() {
		return created;
	}

	public Collection<IngredientInstance> getExcluded() {
		return excluded;
	}

	public Long getId() {
		return id;
	}

	public List<String> getNameLangage() {
		return I18nUtils.getLangages(getName());
	}

	public String getImage() {
		return image;
	}

	public Collection<String> getImages() {
		return images;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setExcluded(Collection<IngredientInstance> excluded) {
		this.excluded = excluded;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setImages(Collection<String> images) {
		this.images = images;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName(String locale) {
		return I18nUtils.getByLangage(locale, getName());
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
}
