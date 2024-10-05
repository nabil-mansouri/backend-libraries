package com.nm.cms.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect()
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsDtoContentsTextForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String lang;
	private String name;
	private String description;
	private String descriptionText;
	private String descriptionShort;
	private List<CmsDtoContentsKeyword> keywords = new ArrayList<CmsDtoContentsKeyword>();
	private boolean selected;

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public CmsDtoContentsTextForm setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
		return this;
	}

	public CmsDtoContentsTextForm addKeyword(List<String> keywords) {
		for (String k : keywords) {
			addKeyword(k);
		}
		return this;
	}

	public CmsDtoContentsTextForm addKeyword(String keywords) {
		getKeywords().add(new CmsDtoContentsKeyword(keywords));
		return this;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public List<CmsDtoContentsKeyword> getKeywords() {
		return keywords;
	}

	@JsonIgnore
	public List<String> getKeywordsString() {
		List<String> s = new ArrayList<String>();
		for (CmsDtoContentsKeyword k : keywords) {
			s.add(k.getText());
		}
		return s;
	}

	public String getLang() {
		return lang;
	}

	public String getName() {
		return name;
	}

	public boolean isSelected() {
		return selected;
	}

	public CmsDtoContentsTextForm setDescription(String description) {
		this.description = description;
		return this;
	}

	public CmsDtoContentsTextForm setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
		return this;
	}

	public CmsDtoContentsTextForm setKeywords(List<CmsDtoContentsKeyword> keywords) {
		this.keywords = keywords;
		return this;
	}

	public CmsDtoContentsTextForm setLang(String lang) {
		this.lang = lang;
		return this;
	}

	public CmsDtoContentsTextForm setName(String name) {
		this.name = name;
		return this;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public CmsDtoContentsTextForm withKeywordsString(List<String> keywords) {
		for (String s : keywords) {
			this.keywords.add(new CmsDtoContentsKeyword(s));
		}
		return this;
	}
}
