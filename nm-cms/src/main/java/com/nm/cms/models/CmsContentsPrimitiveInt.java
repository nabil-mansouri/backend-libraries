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
@Table(name = "nm_cms_contents_primitive_int", schema = "mod_cms")
public class CmsContentsPrimitiveInt extends CmsContents {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	protected int data;

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

}
