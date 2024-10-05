package com.nm.cms.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cms_contents_primitive_date", schema = "mod_cms")
public class CmsContentsPrimitiveDate extends CmsContents {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	protected Date data;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
