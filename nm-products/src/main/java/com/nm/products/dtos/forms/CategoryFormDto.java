package com.nm.products.dtos.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.datas.dtos.AppDataDtoImpl;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private CmsDtoContentsComposedForm cms = new CmsDtoContentsComposedForm();
	// Null to avoid stack overflow in json
	private CategoryFormDto parent;

	public CategoryFormDto() {
	}

	public CategoryFormDto(Long id) {
		setId(id);
	}

	public void setParent(CategoryFormDto parent) {
		this.parent = parent;
	}

	public CategoryFormDto getParent() {
		return parent;
	}

	public CategoryFormDto setImg(AppDataDtoImpl appDataDtoImpl) {
		this.cms.setImg(appDataDtoImpl);
		return this;
	}

	public CategoryFormDto add(CmsDtoContentsTextForm e) {
		cms.add(e);
		return this;
	}

	public Long getId() {
		return id;
	}

	public CategoryFormDto setId(Long id) {
		this.id = id;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CmsDtoContentsComposedForm getCms() {
		return cms;
	}

	public CategoryFormDto setCms(CmsDtoContentsComposedForm contents) {
		this.cms = contents;
		return this;
	}

	public CategoryFormDto add(AppDataDtoImpl e) {
		this.cms.add(e);
		return this;
	}

}
