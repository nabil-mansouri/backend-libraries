package com.nm.cms.dtos;

import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface CmsDto extends Dto {
	public Long getId();

	public CmsDto setId(Long id);

	public CmsDtoContents getContents();
}
