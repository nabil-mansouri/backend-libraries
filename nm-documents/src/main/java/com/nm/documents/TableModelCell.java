package com.nm.documents;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TableModelCell implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer gridspan;
	private String value;
	private Boolean center;

	public TableModelCell() {
	}

	public TableModelCell(String value) {
		super();
		setValue(value);
	}

	public TableModelCell setCenter(Boolean center) {
		this.center = center;
		return this;
	}

	public Boolean getCenter() {
		return center;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		String trim = StringUtils.replace(value, ",", "");
		trim = StringUtils.replace(trim, "%", "");
		trim = StringUtils.replace(trim, "-", "");
		trim = StringUtils.trim(trim);
		if (StringUtils.isNumericSpace(trim)) {
			setCenter(true);
		}
	}

	public Integer getGridspan() {
		return gridspan;
	}

	public TableModelCell setGridspan(Integer gridspan) {
		this.gridspan = gridspan;
		return this;
	}
}
