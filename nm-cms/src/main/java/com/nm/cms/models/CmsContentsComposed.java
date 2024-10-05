package com.nm.cms.models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.cms.constants.CmsPartType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_cms_contents_composed", schema = "mod_cms")
public class CmsContentsComposed extends CmsContents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "nm_cms_contents_composed_texts", schema = "mod_cms")
	private Collection<CmsContentsText> texts = Sets.newHashSet();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "nm_cms_contents_composed_images", schema = "mod_cms")
	private Collection<CmsContentsImage> images = Sets.newHashSet();

	public CmsContentsText add(CmsPartType type, CmsContentsText text) {
		text.setType(type);
		this.texts.add(text);
		return text;
	}

	public CmsContentsImage add(CmsPartType type, CmsContentsImage text) {
		text.setType(type);
		this.images.add(text);
		return text;
	}

	public Collection<CmsContentsText> findTexts(CmsPartType part) {
		Collection<CmsContentsText> all = Lists.newArrayList();
		for (CmsContentsText t : texts) {
			if (t.getType().equals(part)) {
				all.add(t);
			}
		}
		return all;
	}

	public Collection<CmsContentsImage> findImages(CmsPartType part) {
		Collection<CmsContentsImage> all = Lists.newArrayList();
		for (CmsContentsImage t : images) {
			if (t.getType().equals(part)) {
				all.add(t);
			}
		}
		return all;
	}

	public Collection<CmsContentsText> getTexts() {
		return texts;
	}

	public void setTexts(Collection<CmsContentsText> texts) {
		this.texts = texts;
	}

	public Collection<CmsContentsImage> getImages() {
		return images;
	}

	public void setImages(Collection<CmsContentsImage> images) {
		this.images = images;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
