package com.nm.products.dtos.views;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.cms.dtos.CmsDtoContentsComposedView;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class CategoryViewDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	private String createdAt;
	private CmsDtoContentsComposedView cms = new CmsDtoContentsComposedView();
	// FRONT
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public CategoryViewDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public CmsDtoContentsComposedView getCms() {
		return cms;
	}

	public void setCms(CmsDtoContentsComposedView cms) {
		this.cms = cms;
	}

}
