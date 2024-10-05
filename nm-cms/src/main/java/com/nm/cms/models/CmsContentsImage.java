package com.nm.cms.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nm.datas.models.AppData;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_cms_contents_images", schema = "mod_cms")
public class CmsContentsImage extends CmsContents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	@ManyToOne(optional = false)
	private AppData image;

	public AppData getImage() {
		return image;
	}

	public void setImage(AppData image) {
		this.image = image;
	}

	/**
	 * Use super and image to make sure that object with no ID are different in
	 * the set
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((image == null) ? 0 : image.hashCode());
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
		CmsContentsImage other = (CmsContentsImage) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		return true;
	}

}
