package com.nm.cms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cms_contents_primitive_double", schema = "mod_cms")
public class CmsContentsPrimitiveDouble extends CmsContents {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	protected Double data;

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}

}
