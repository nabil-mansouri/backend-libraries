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
@Table(name = "nm_cms_contents_primitive_string", schema = "mod_cms")
public class CmsContentsPrimitiveString extends CmsContents {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(columnDefinition = "TEXT", nullable = false)
	protected String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
