package com.nm.products.dtos.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Objects;
import com.nm.cms.dtos.CmsDtoContentsComposedView;
import com.nm.products.constants.ProductState;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ProductViewDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;

	private CmsDtoContentsComposedView cms = new CmsDtoContentsComposedView();
	private Date createdAt;
	private ProductState state;
	private Date dateState;
	//
	private final List<CategoryViewDto> categories = new ArrayList<CategoryViewDto>();
	private List<IngredientViewDto> excluded = new ArrayList<IngredientViewDto>();
	// USED IN JS
	private Long idDraft = null;
	private Double price;
	private boolean selected;

	public ProductViewDto() {
	}

	public ProductViewDto(Long id) {
		super();
		this.id = id;
	}

	public ProductState getState() {
		return state;
	}

	public Date getDateState() {
		return dateState;
	}

	public void setDateState(Date dateState) {
		this.dateState = dateState;
	}

	public void setState(ProductState state) {
		this.state = state;
	}

	public boolean addCategories(CategoryViewDto arg0) {
		return categories.add(arg0);
	}

	public String description() {
		return cms.getDescription();
	}

	public List<CategoryViewDto> getCategories() {
		return categories;
	}

	public CmsDtoContentsComposedView getCms() {
		return cms;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public List<IngredientViewDto> getExcluded() {
		return excluded;
	}

	public Long getId() {
		return id;
	}

	public Long getIdDraft() {
		return idDraft;
	}

	public Double getPrice() {
		return price;
	}

	public String img() {
		return cms.getImg();
	}

	public void img(String img) {
		cms.setImg(img);
	}

	public List<ProductPartViewDto> parts() {
		return null;
	}

	public List<String> imgs() {
		return cms.getImgs();
	}

	public boolean isSelected() {
		return selected;
	}

	public String name() {
		return cms.getName();
	}

	public void name(String name) {
		cms.setName(name);
	}

	public void setCms(CmsDtoContentsComposedView cms) {
		this.cms = cms;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setExcluded(List<IngredientViewDto> excluded) {
		this.excluded = excluded;
	}

	public ProductViewDto setId(Long id) {
		this.id = id;
		return this;
	}

	public ProductViewDto setIdDraft(Long idDraft) {
		this.idDraft = idDraft;
		return this;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ProductViewDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public ProductViewDto clone() {
		return SerializationUtils.clone(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProductViewDto) {
			Long id = ((ProductViewDto) obj).getId();
			return Objects.equal(id, this.getId());
		}
		return super.equals(obj);
	}

}
