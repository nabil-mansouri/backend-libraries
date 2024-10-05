package com.nm.cms.converters;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import com.google.common.collect.Lists;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.cms.constants.CmsOptions;
import com.nm.cms.constants.CmsPartType.CmsPartTypeDefault;
import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.dtos.CmsDtoContentsKeyword;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.cms.models.CmsContents;
import com.nm.cms.models.CmsContentsComposed;
import com.nm.cms.models.CmsContentsImage;
import com.nm.cms.models.CmsContentsText;
import com.nm.datas.AppDataException;
import com.nm.datas.SoaAppData;
import com.nm.datas.daos.DaoAppData;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class CmsContentsComposedFormConverter extends DtoConverterDefault<CmsDtoContentsComposedForm, CmsContents> {

	private SoaLocale soaLocale;
	private DaoAppData daoAppData;
	private SoaAppData soaAppData;

	public void setDaoAppData(DaoAppData daoAppData) {
		this.daoAppData = daoAppData;
	}

	public void setSoaAppData(SoaAppData soaAppData) {
		this.soaAppData = soaAppData;
	}

	public void setSoaLocale(SoaLocale soaLocale) {
		this.soaLocale = soaLocale;
	}

	@Override
	public Collection<Class<? extends CmsContents>> managedEntity() {
		Collection<Class<? extends CmsContents>> all = super.managedEntity();
		all.add(CmsContentsComposed.class);
		return all;
	}

	public CmsDtoContentsComposedForm toDto(CmsDtoContentsComposedForm dto, CmsContents entity, OptionsList options) throws DtoConvertException {
		try {
			dto.setId(entity.getId());
			if (entity instanceof CmsContentsComposed) {
				CmsContentsComposed entityReal = (CmsContentsComposed) entity;
				for (CmsContentsImage img : entityReal.getImages()) {
					processImages(dto, img, options);
				}
				for (CmsContentsText img : entityReal.getTexts()) {
					processText(dto, img, options);
				}
			} else if (entity instanceof CmsContentsText) {
				CmsContentsText t = (CmsContentsText) entity;
				processText(dto, t, options);
			} else if (entity instanceof CmsContentsImage) {
				CmsContentsImage t = (CmsContentsImage) entity;
				processImages(dto, t, options);
			}
			//
			if (options.contains(CmsOptions.FullForm)) {
				// Set only configured langs and select defaut
				Collection<LocaleFormDto> selected = soaLocale.getSelectedLocales();
				if (selected.isEmpty()) {
					dto.setNoSelectedLang(true);
				}
				// Overrides lang
				Map<String, CmsDtoContentsTextForm> codes = new HashMap<String, CmsDtoContentsTextForm>();
				for (LocaleFormDto l : selected) {
					codes.put(l.getCode(), new CmsDtoContentsTextForm().setLang(l.getCode()));
				}
				for (CmsDtoContentsTextForm c : dto.getContents()) {
					if (codes.containsKey(c.getLang())) {
						codes.put(c.getLang(), c);
					}
				}
				dto.getContents().clear();
				dto.getContents().addAll(codes.values());
				// SELECTED LANG (MUST BE AFTER)
				LocaleFormDto defaut = null;
				try {
					defaut = soaLocale.getDefaultLocale();
				} catch (NoDataFoundException e) {
					dto.setNoDefaultLang(true);
				}
				for (CmsDtoContentsTextForm c : dto.getContents()) {
					if (defaut != null && StringUtils.equalsIgnoreCase(defaut.getCode(), c.getLang())) {
						c.setSelected(true);
					}
				}
			}
			return dto;
		} catch (

		Exception e)

		{
			throw new DtoConvertException(e);
		}

	}

	private void processImages(CmsDtoContentsComposedForm dto, CmsContentsImage image, OptionsList options) throws Exception {
		if (image.getType() instanceof CmsPartTypeDefault) {
			CmsPartTypeDefault d = (CmsPartTypeDefault) image.getType();
			switch (d) {
			case Main:
				dto.setImg(search(AppDataDtoImpl.class, AppData.class).toDto(image.getImage(), options));
				break;
			case Secondary:
				dto.getImgs().add(search(AppDataDtoImpl.class, AppData.class).toDto(image.getImage(), options));
				break;
			default:

			}
		}
	}

	private List<String> toList(String data) {
		List<String> all = Lists.newArrayList();
		try {
			JSONArray array = new JSONArray(data);
			for (int i = 0; i < array.length(); i++) {
				all.add(array.getString(i));
			}
		} catch (Exception e) {
		}
		return all;
	}

	public void processText(CmsDtoContentsComposedForm form, CmsContentsText content, OptionsList options) {
		for (String loc : content.langs()) {
			CmsDtoContentsTextForm langFormBean = form.createIfAbsent(loc);
			if (content.getType() instanceof CmsPartTypeDefault) {
				CmsPartTypeDefault d = (CmsPartTypeDefault) content.getType();
				switch (d) {
				case Description:
					langFormBean.setDescription(content.lang(loc, null));
					break;
				case DescriptionRawText:
					langFormBean.setDescriptionText(content.lang(loc, null));
					break;
				case DescriptionShort:
					langFormBean.setDescriptionShort(content.lang(loc, null));
					break;
				case Keywords:
					langFormBean.withKeywordsString(toList(content.lang(loc, null)));
					break;
				case Title:
					langFormBean.setName(content.lang(loc, null));
					break;
				default:
					break;

				}
			}
		}
	}

	private String toString(List<CmsDtoContentsKeyword> keys) {
		JSONArray array = new JSONArray();
		for (CmsDtoContentsKeyword k : keys) {
			array.put(k.getText());
		}
		return array.toString();
	}

	public CmsContents toEntity(CmsDtoContentsComposedForm dto, OptionsList options) throws DtoConvertException {
		try {
			CmsContentsComposed entity = new CmsContentsComposed();
			if (dto.getId() != null) {
				try {
					entity = AbstractGenericDao.get(CmsContentsComposed.class).get(dto.getId());
				} catch (NoDataFoundException e) {
					// MAYBE INSTANCEOF Text or image
					dto.setId(null);
				}
			}
			entity.setType(CmsPartTypeDefault.Main);
			//
			entity.getTexts().clear();
			CmsContentsText desc = entity.add(CmsPartTypeDefault.Description, new CmsContentsText());
			CmsContentsText descShort = entity.add(CmsPartTypeDefault.DescriptionShort, new CmsContentsText());
			CmsContentsText descText = entity.add(CmsPartTypeDefault.DescriptionRawText, new CmsContentsText());
			CmsContentsText keywords = entity.add(CmsPartTypeDefault.Keywords, new CmsContentsText());
			CmsContentsText title = entity.add(CmsPartTypeDefault.Title, new CmsContentsText());
			for (CmsDtoContentsTextForm lang : dto.getContents()) {
				descShort.addLang(lang.getLang(), lang.getDescriptionShort());
				desc.addLang(lang.getLang(), lang.getDescription());
				descText.addLang(lang.getLang(), lang.getDescriptionText());
				keywords.addLang(lang.getLang(), toString(lang.getKeywords()));
				title.addLang(lang.getLang(), lang.getName());
			}
			//
			entity.getImages().clear();
			if (dto.getImg() != null) {
				CmsContentsImage main = entity.add(CmsPartTypeDefault.Main, new CmsContentsImage());
				main.setImage(loadOrSave(dto.getImg(), options));
			}
			for (AppDataDtoImpl up : dto.getImgs()) {
				CmsContentsImage sec = entity.add(CmsPartTypeDefault.Secondary, new CmsContentsImage());
				sec.setImage(loadOrSave(up, options));
			}
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	private AppData loadOrSave(AppDataDtoImpl dto, OptionsList options) throws AppDataException {
		if (dto.getId() != null) {
			return daoAppData.load(dto.getId());
		} else {
			return soaAppData.save(dto, options);
		}

	}
}
