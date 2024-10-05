package com.nm.products.dtos.views;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Objects;
import com.nm.cms.dtos.CmsDtoContentsComposedView;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class IngredientViewDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
	private CmsDtoContentsComposedView cms = new CmsDtoContentsComposedView();
	private String createdAt;
	private boolean selected;
	private boolean facultatif;

	public boolean isSelected() {
		return selected;
	}

	public IngredientViewDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CmsDtoContentsComposedView getCms() {
		return cms;
	}

	public void setCms(CmsDtoContentsComposedView cms) {
		this.cms = cms;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getFacultatif() {
		return facultatif;
	}

	public IngredientViewDto setFacultatif(Boolean facultatif) {
		this.facultatif = facultatif;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IngredientViewDto) {
			Long id = ((IngredientViewDto) obj).getId();
			return Objects.equal(id, this.getId());
		}
		return super.equals(obj);
	}

	public void setName(String name) {
		cms.setName(name);
	}

	public void setAllTexts(String allTexts) {
		cms.setAllTexts(allTexts);
	}

	public void setImg(String img) {
		cms.setImg(img);
	}

	public IngredientViewDto clone() {
		return SerializationUtils.clone(this);
	}
}
