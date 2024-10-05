package com.nm.cms.converters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NotYetImplementedException;

import com.nm.app.locale.SoaLocale;
import com.nm.cms.constants.CmsPartType.CmsPartTypeDefault;
import com.nm.cms.dtos.CmsDtoContentsComposedView;
import com.nm.cms.models.CmsContents;
import com.nm.cms.models.CmsContentsComposed;
import com.nm.cms.models.CmsContentsImage;
import com.nm.cms.models.CmsContentsText;
import com.nm.datas.constants.AppDataOptions;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class CmsContentsComposedViewConverter extends DtoConverterDefault<CmsDtoContentsComposedView, CmsContents> {

	private SoaLocale soaLocale;

	public void setSoaLocale(SoaLocale soaLocale) {
		this.soaLocale = soaLocale;
	}

	@Override
	public Collection<Class<? extends CmsContents>> managedEntity() {
		Collection<Class<? extends CmsContents>> all = super.managedEntity();
		all.add(CmsContentsComposed.class);
		return all;
	}

	public CmsDtoContentsComposedView toDto(CmsDtoContentsComposedView dto, CmsContents entity, OptionsList options) throws DtoConvertException {
		try {
			if (entity instanceof CmsContentsComposed) {
				soaLocale.setLocaleIfEmpty(options);
				CmsContentsComposed entityReal = (CmsContentsComposed) entity;
				for (CmsContentsImage img : entityReal.getImages()) {
					processImages(dto, img);
				}
				for (CmsContentsText img : entityReal.getTexts()) {
					processText(dto, img, options);
				}
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	private void processImages(CmsDtoContentsComposedView dto, CmsContentsImage image) throws Exception {
		if (image.getType() instanceof CmsPartTypeDefault) {
			CmsPartTypeDefault d = (CmsPartTypeDefault) image.getType();
			OptionsList opts = new OptionsList().withOption(AppDataOptions.URL);
			switch (d) {
			case Main:
				dto.setImg(search(AppDataDtoImpl.class, AppData.class).toDto(image.getImage(), opts).getUrl());
				break;
			case Secondary:
				dto.add(search(AppDataDtoImpl.class, AppData.class).toDto(image.getImage(), opts).getUrl());
				break;
			default:

			}
		}
	}

	public void processText(CmsDtoContentsComposedView form, CmsContentsText content, OptionsList options) {
		if (options.hasLocale()) {
			if (content.getType() instanceof CmsPartTypeDefault) {
				CmsPartTypeDefault d = (CmsPartTypeDefault) content.getType();
				List<String> joined = Arrays.asList(form.getAllTexts(), content.lang(options.getLocale(), null));
				form.setAllTexts(StringUtils.join(joined, "\n"));
				switch (d) {
				case Description:
					form.setDescription(content.lang(options.getLocale(), null));
					break;
				case DescriptionShort:
					form.setDescriptionShort(content.lang(options.getLocale(), null));
					break;
				case DescriptionRawText:
					break;
				case Keywords:
					break;
				case Title:
					form.setName(content.lang(options.getLocale(), null));
					break;
				default:
					break;

				}
			}
			for (String curr : content.langs()) {
				form.addLang(curr);
			}

		}

	}

	public CmsContents toEntity(CmsDtoContentsComposedView dto, OptionsList options) throws DtoConvertException {
		try {
			throw new NotYetImplementedException("Cannot transform view to entity");
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
