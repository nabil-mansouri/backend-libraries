package com.nm.cms.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect()
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsDtoContentsComposedView implements CmsDtoContents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String img;
	private List<String> imgs = new ArrayList<String>();
	private String name;
	private String description;
	private String descriptionShort;
	private Collection<String> keywords = new HashSet<String>();
	private Collection<String> langs = new HashSet<String>();
	// SEARCH IN JS
	private String allTexts;

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

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public Collection<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Collection<String> keywords) {
		this.keywords = keywords;
	}

	public String getName() {
		return name;
	}

	public Collection<String> getLangs() {
		return langs;
	}

	public void setLangs(Collection<String> langs) {
		this.langs = langs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAllTexts() {
		return allTexts;
	}

	public void setAllTexts(String allTexts) {
		this.allTexts = allTexts;
	}

	public void addLang(String curr) {
		this.langs.add(curr);
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean add(String e) {
		return imgs.add(e);
	}
}
