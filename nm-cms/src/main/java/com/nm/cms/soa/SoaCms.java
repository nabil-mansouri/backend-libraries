package com.nm.cms.soa;

import java.util.Collection;

import com.nm.cms.CmsException;
import com.nm.cms.dao.QueryBuilderCms;
import com.nm.cms.dao.QueryBuilderCmsContent;
import com.nm.cms.dtos.CmsDto;
import com.nm.cms.dtos.CmsDtoContents;
import com.nm.cms.models.Cms;
import com.nm.cms.models.CmsContents;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface SoaCms {
	public CmsDto create(OptionsList options) throws CmsException;

	public Cms saveOrUpdate(CmsDto dto, OptionsList options) throws CmsException;

	public CmsContents saveOrUpdate(CmsDtoContents dto, OptionsList options) throws CmsException;

	public CmsDto fetch(Number id, OptionsList options) throws CmsException;

	public Collection<CmsDtoContents> fetch(QueryBuilderCmsContent query, OptionsList options) throws CmsException;

	public Collection<CmsDto> fetch(QueryBuilderCms query, OptionsList options) throws CmsException;

	public void remove(QueryBuilderCms query, OptionsList options);
}
