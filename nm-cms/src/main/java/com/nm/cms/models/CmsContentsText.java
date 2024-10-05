package com.nm.cms.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.json.JSONObject;

import com.google.common.collect.Lists;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_cms_contents_text", schema = "mod_cms")
public class CmsContentsText extends CmsContents {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	@Transient
	private JSONObject object = new JSONObject();

	@Column(columnDefinition = "TEXT", nullable = false)
	public String getData() {
		return object.toString();
	}

	public void setData(String data) {
		try {
			object = new JSONObject(data);
		} catch (Exception e) {
			object = new JSONObject();
		}
	}

	public CmsContentsText addLang(String locale, String value) {
		object.put(locale, value);
		return this;
	}

	public String lang(String locale, String def) {
		try {
			return object.getString(locale);
		} catch (Exception e) {
			return def;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> langs() {
		return Lists.newArrayList(object.keySet());
	}

	/**
	 * Use super and image to make sure that object with no ID are different in
	 * the set
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CmsContentsText other = (CmsContentsText) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

}
