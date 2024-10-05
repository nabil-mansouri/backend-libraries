package com.nm.cms.dtos;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.nm.cms.constants.CmsTableHeader;
import com.nm.datas.dtos.AppDataDtoImpl;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect()
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsDtoContentsFile implements CmsDtoContents {

	public CmsDtoContentsFile(AppDataDtoImpl app) {
		super();
		this.setAppData(app);
	}

	public CmsDtoContentsFile() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private AppDataDtoImpl appData;
	private boolean header;
	private String separator = ";";
	protected List<CmsTableHeader> headers = Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContentId() {
		return getId();
	}

	public void setContentId(Long id) {
		setId(id);
	}

	public List<CmsTableHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<CmsTableHeader> headers) {
		this.headers = headers;
	}

	public CmsDtoContentsFile add(CmsTableHeader h) {
		this.headers.add(h);
		return this;
	}

	public CmsDtoContentsFile add(Collection<CmsTableHeader> h) {
		this.headers.addAll(h);
		return this;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public boolean isHeader() {
		return header;
	}

	public CmsDtoContentsFile setHeader(boolean header) {
		this.header = header;
		return this;
	}

	public AppDataDtoImpl getAppData() {
		return appData;
	}

	public CmsDtoContentsFile setAppData(AppDataDtoImpl appData) {
		this.appData = appData;
		return this;
	}

}
