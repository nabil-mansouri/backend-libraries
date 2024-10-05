package com.nm.cms.dtos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.datas.dtos.AppDataDtoImpl;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect()
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsDtoContentsComposedForm implements CmsDtoContents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private AppDataDtoImpl img;
	private List<AppDataDtoImpl> imgs = new ArrayList<AppDataDtoImpl>();
	private List<CmsDtoContentsTextForm> texts = new ArrayList<CmsDtoContentsTextForm>();
	private boolean noDefaultLang;
	private boolean noSelectedLang;
	private List<CmsDtoContentsTextForm> contents = new ArrayList<CmsDtoContentsTextForm>();

	public Long getContentId() {
		return getId();
	}

	public void setContentId(Long id) {
		setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean add(CmsDtoContentsTextForm e) {
		return contents.add(e);
	}

	public CmsDtoContentsComposedForm addKeyword(String lang, String key) {
		createIfAbsent(lang).getKeywords().add(new CmsDtoContentsKeyword(key));
		return this;
	}

	public CmsDtoContentsTextForm createIfAbsent(String lang) {
		if (fetchByLang(lang) != null) {
			return fetchByLang(lang);
		} else {
			CmsDtoContentsTextForm bean = new CmsDtoContentsTextForm();
			bean.setLang(lang);
			contents.add(bean);
			return bean;
		}
	}

	public CmsDtoContentsTextForm fetchByLang(String lang) {
		for (CmsDtoContentsTextForm bean : this.contents) {
			if (StringUtils.equals(bean.getLang(), lang)) {
				return bean;
			}
		}
		return null;
	}

	public List<CmsDtoContentsTextForm> getContents() {
		return contents;
	}

	public CmsDtoContentsComposedForm putDescription(String lang, String desc) {
		createIfAbsent(lang).setDescription(desc);
		return this;
	}

	public CmsDtoContentsComposedForm putName(String lang, String desc) {
		createIfAbsent(lang).setName(desc);
		return this;
	}

	public void setContents(List<CmsDtoContentsTextForm> contents) {
		this.contents = contents;
	}

	public AppDataDtoImpl getImg() {
		return img;
	}

	public CmsDtoContentsComposedForm setImg(AppDataDtoImpl img) {
		this.img = img;
		return this;
	}

	public List<AppDataDtoImpl> getImgs() {
		return imgs;
	}

	public CmsDtoContentsComposedForm setImgs(List<AppDataDtoImpl> imgs) {
		this.imgs = imgs;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CmsDtoContentsComposedForm add(AppDataDtoImpl e) {
		imgs.add(e);
		return this;
	}

	public CmsDtoContentsComposedForm add(String e) {
		imgs.add(new AppDataDtoImpl().withUrl(e));
		return this;
	}

	public List<CmsDtoContentsTextForm> getTexts() {
		return texts;
	}

	public void setTexts(List<CmsDtoContentsTextForm> texts) {
		this.texts = texts;
	}

	public boolean isNoDefaultLang() {
		return noDefaultLang;
	}

	public void setNoDefaultLang(boolean noDefaultLang) {
		this.noDefaultLang = noDefaultLang;
	}

	public boolean isNoSelectedLang() {
		return noSelectedLang;
	}

	public void setNoSelectedLang(boolean noSelectedLang) {
		this.noSelectedLang = noSelectedLang;
	}

}
