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
public class IngredientFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private CmsDtoContentsComposedForm cms = new CmsDtoContentsComposedForm();

	public IngredientFormDto add(CmsDtoContentsTextForm e) {
		cms.add(e);
		return this;
	}

	public Long getId() {
		return id;
	}

	public IngredientFormDto setId(Long id) {
		this.id = id;
		return this;
	}

	public CmsDtoContentsComposedForm getCms() {
		return cms;
	}

	public IngredientFormDto setCms(CmsDtoContentsComposedForm contents) {
		this.cms = contents;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IngredientFormDto add(AppDataDtoImpl e) {
		this.cms.add(e);
		return this;
	}

	public IngredientFormDto setImg(AppDataDtoImpl appDataDtoImpl) {
		this.cms.setImg(appDataDtoImpl);
		return this;
	}

}
