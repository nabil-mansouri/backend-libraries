package com.nm.shop.dtos;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.cms.dtos.CmsDtoContentsComposedView;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ShopViewDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date createdAt;
	private CmsDtoContentsComposedView cms = new CmsDtoContentsComposedView();
	private ShopStateDto state = new ShopStateDto();
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public ShopViewDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public ShopStateDto getState() {
		return state;
	}

	public void setState(ShopStateDto state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CmsDtoContentsComposedView getCms() {
		return cms;
	}

	public void setCms(CmsDtoContentsComposedView cms) {
		this.cms = cms;
	}

	public ShopViewDto clone() {
		return SerializationUtils.clone(this);
	}
}
