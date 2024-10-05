package com.nm.cms.converters;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NotYetImplementedException;

import com.google.common.base.Strings;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.dtos.CmsDtoContentsComposedView;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CmsContentsComposedBridgeConverter
		extends DtoConverterDefault<CmsDtoContentsComposedView, CmsDtoContentsComposedForm> {
	private SoaLocale soaLocale;

	public void setSoaLocale(SoaLocale soaLocale) {
		this.soaLocale = soaLocale;
	}

	public CmsDtoContentsComposedView toDto(CmsDtoContentsComposedView dto, CmsDtoContentsComposedForm entity,
			OptionsList options) throws DtoConvertException {
		CmsDtoContentsComposedView view = new CmsDtoContentsComposedView();
		// SELECTED LANG (MUST BE AFTER)
		LocaleFormDto defautLocale = null;
		try {
			defautLocale = soaLocale.getDefaultLocale();
		} catch (NoDataFoundException e) {
		}
		// SET NAME
		for (CmsDtoContentsTextForm content : entity.getContents()) {
			if (StringUtils.equalsIgnoreCase(content.getLang(), options.getLocale())) {
				view.setName(content.getName());
			}
			if (defautLocale != null && Strings.isNullOrEmpty(view.getName())
					&& StringUtils.equalsIgnoreCase(content.getLang(), defautLocale.getCode())) {
				view.setName(content.getName());
			}
		}
		if (Strings.isNullOrEmpty(view.getName())) {
			for (CmsDtoContentsTextForm content : entity.getContents()) {
				if (!Strings.isNullOrEmpty(content.getName())) {
					view.setName(content.getName());
				}
			}
		}
		return view;
	}

	public CmsDtoContentsComposedForm toEntity(CmsDtoContentsComposedView dto, OptionsList options)
			throws DtoConvertException {
		throw new NotYetImplementedException("Could not transform entity");
	}

}
