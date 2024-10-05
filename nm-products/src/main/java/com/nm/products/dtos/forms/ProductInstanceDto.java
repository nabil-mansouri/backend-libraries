package com.nm.products.dtos.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.products.dtos.views.IngredientViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ProductInstanceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
	private String img;
	private String name;
	private String description;
	private Date createdAt;
	// SEARCH IN JS
	private String allTexts;
	private final List<String> imgs = new ArrayList<String>();
	private final List<String> langs = new ArrayList<String>();
	private final List<String> categories = new ArrayList<String>();
	private List<IngredientViewDto> excluded = new ArrayList<IngredientViewDto>();
	private Double price;

	public boolean addCategories(String arg0) {
		return categories.add(arg0);
	}

	public boolean addImgs(String e) {
		return imgs.add(e);
	}

	public boolean addLang(String e) {
		return langs.add(e);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductInstanceDto other = (ProductInstanceDto) obj;
		if (allTexts == null) {
			if (other.allTexts != null)
				return false;
		} else if (!allTexts.equals(other.allTexts))
			return false;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (excluded == null) {
			if (other.excluded != null)
				return false;
		} else if (!excluded.equals(other.excluded))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
			return false;
		if (imgs == null) {
			if (other.imgs != null)
				return false;
		} else if (!imgs.equals(other.imgs))
			return false;
		if (langs == null) {
			if (other.langs != null)
				return false;
		} else if (!langs.equals(other.langs))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	public String getAllTexts() {
		return allTexts;
	}

	public List<String> getCategories() {
		return categories;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getDescription() {
		return description;
	}

	public List<IngredientViewDto> getExcluded() {
		return excluded;
	}

	public Long getId() {
		return id;
	}

	public String getImg() {
		return img;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public List<String> getLangs() {
		return langs;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allTexts == null) ? 0 : allTexts.hashCode());
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((excluded == null) ? 0 : excluded.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		result = prime * result + ((imgs == null) ? 0 : imgs.hashCode());
		result = prime * result + ((langs == null) ? 0 : langs.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	public boolean removeCategories(String arg0) {
		return categories.remove(arg0);
	}

	public boolean removeLang(String o) {
		return langs.remove(o);
	}

	public void setAllTexts(String allTexts) {
		this.allTexts = allTexts;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExcluded(List<IngredientViewDto> excluded) {
		this.excluded = excluded;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductInstanceView [id=" + id + ", img=" + img + ", name=" + name + ", description=" + description
				+ ", createdAt=" + createdAt + ", allTexts=" + allTexts + ", imgs=" + imgs + ", langs=" + langs
				+ ", categories=" + categories + ", parts=" + ", excluded=" + excluded + ", price=" + price + "]";
	}

	public void add(IngredientViewDto convert) {
		this.excluded.add(convert);
	}

}
