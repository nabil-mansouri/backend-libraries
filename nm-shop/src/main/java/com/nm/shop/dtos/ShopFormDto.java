package com.nm.shop.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.geo.dtos.AddressDtoImpl;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopFormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;

	private CmsDtoContentsComposedForm cms = new CmsDtoContentsComposedForm();
	private AddressDtoImpl address = new AddressDtoImpl();
	private boolean isnew;

	public boolean isIsnew() {
		return isnew;
	}

	public void setIsnew(boolean isnew) {
		this.isnew = isnew;
	}

	public void setConfigError(boolean configError) {
	}

	public boolean isConfigError() {
		return cms.isNoDefaultLang() || cms.isNoSelectedLang();
	}

	public AddressDtoImpl getAddress() {
		return address;
	}

	public void setAddress(AddressDtoImpl address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public ShopFormDto setId(Long id) {
		this.id = id;
		return this;
	}

	public CmsDtoContentsComposedForm getCms() {
		return cms;
	}

	public ShopFormDto setCms(CmsDtoContentsComposedForm contents) {
		this.cms = contents;
		return this;
	}

	public ShopFormDto add(CmsDtoContentsTextForm e) {
		cms.add(e);
		return this;
	}

}
