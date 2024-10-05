package com.nm.cms.dtos;

import com.nm.utils.node.DtoNode;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CmsDtoImpl implements CmsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private DtoNode owner=new DtoNode();
	private DtoNode subject=new DtoNode();
	private CmsDtoContents contents;

	public CmsDtoContents getContents() {
		return contents;
	}

	public void setContents(CmsDtoContents contents) {
		this.contents = contents;
	}

	public Long getId() {
		return id;
	}

	public CmsDtoImpl setId(Long id) {
		this.id = id;
		return this;
	}

	public DtoNode getOwner() {
		return owner;
	}

	public void setOwner(DtoNode owner) {
		this.owner = owner;
	}

	public DtoNode getSubject() {
		return subject;
	}

	public void setSubject(DtoNode subject) {
		this.subject = subject;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
