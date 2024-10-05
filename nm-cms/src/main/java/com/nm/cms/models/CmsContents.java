package com.nm.cms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.cms.constants.CmsPartType;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cms_contents", schema = "mod_cms")
@Inheritance(strategy = InheritanceType.JOINED)
public class CmsContents extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_cms_contents", schema = "mod_cms", sequenceName = "seq_cms_contents", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_cms_contents")
	protected Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	protected CmsPartType type;
	@OneToOne(mappedBy = "data")
	private Cms cms;

	public Cms getCms() {
		return cms;
	}

	public void setCms(Cms cms) {
		this.cms = cms;
	}

	//

	public CmsPartType getType() {
		return type;
	}

	public void setType(CmsPartType type) {
		this.type = type;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Use super and image to make sure that object with no ID are different in
	 * the set
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CmsContents other = (CmsContents) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
