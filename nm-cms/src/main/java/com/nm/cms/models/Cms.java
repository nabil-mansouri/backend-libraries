package com.nm.cms.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.node.ModelNode;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cms_cms", schema = "mod_cms")
public class Cms extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_cms_cms", schema = "mod_cms", sequenceName = "seq_cms_cms", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_cms_cms")
	private Long id;
	//

	@OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
	protected CmsContents data;
	//
	@ManyToOne(optional = true)
	private ModelNode owner;
	@ManyToOne(optional = true)
	private ModelNode subject;

	@Override
	public Long getId() {
		return id;
	}

	public CmsContents getData() {
		return data;
	}

	public void setData(CmsContents data) {
		this.data = data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ModelNode getOwner() {
		return owner;
	}

	public void setOwner(ModelNode owner) {
		this.owner = owner;
	}

	public ModelNode getSubject() {
		return subject;
	}

	public void setSubject(ModelNode subject) {
		this.subject = subject;
	}
}
