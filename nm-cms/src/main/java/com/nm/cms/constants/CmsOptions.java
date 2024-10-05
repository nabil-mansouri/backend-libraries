package com.nm.cms.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum CmsOptions implements ModelOptions {
	FullForm, CmsSubjectEntity, CmsSubjectDto, SaveHeaders, //
	TablePrependCreateRow, TableRemoveEmptyRow, TableSignleRow;

	public ModelOptionsType getType() {
		return CmsOptionsType.Cms;
	}

	public enum CmsOptionsType implements ModelOptionsType {
		Cms
	}
}