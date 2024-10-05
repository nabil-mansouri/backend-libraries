package com.nm.cms.dtos;

import java.util.Date;

import com.nm.cms.constants.CmsTableHeader;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CmsDtoContentsPrimitive implements CmsDtoContents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer dataInt;
	private Double dataDouble;
	private String dataString;
	private Date dataDate;
	private int column;
	private int row;
	private boolean optionnal;
	private CmsTableHeader header;

	public Long getContentId() {
		return getId();
	}

	public void setContentId(Long id) {
		setId(id);
	}

	public CmsTableHeader getHeader() {
		return header;
	}

	public CmsDtoContentsPrimitive setHeader(CmsTableHeader header) {
		this.header = header;
		return this;
	}

	public boolean isOptionnal() {
		return optionnal;
	}

	public void setOptionnal(boolean optionnal) {
		this.optionnal = optionnal;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDataInt() {
		return dataInt;
	}

	public CmsDtoContentsPrimitive setDataInt(Integer dataInt) {
		this.dataInt = dataInt;
		return this;
	}

	public Double getDataDouble() {
		return dataDouble;
	}

	public CmsDtoContentsPrimitive setDataDouble(Double dataDouble) {
		this.dataDouble = dataDouble;
		return this;
	}

	public String getDataString() {
		return dataString;
	}

	public CmsDtoContentsPrimitive setDataString(String dataString) {
		this.dataString = dataString;
		return this;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public CmsDtoContentsPrimitive setDataDate(Date dataDate) {
		this.dataDate = dataDate;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
