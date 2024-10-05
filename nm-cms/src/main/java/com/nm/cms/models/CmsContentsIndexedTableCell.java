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

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cms_contents_indexed_table_cell", schema = "mod_cms")
public class CmsContentsIndexedTableCell extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_cms_contents_indexed_table_cell", schema = "mod_cms", sequenceName = "seq_nm_cms_contents_indexed_table_cell", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_cms_contents_indexed_table_cell")
	protected Long id;
	@ManyToOne(optional = false)
	private CmsContentsIndexedTableRow row;
	@OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
	private CmsContents content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CmsContents getContent() {
		return content;
	}

	public void setContent(CmsContents content) {
		this.content = content;
	}

	public CmsContentsIndexedTableRow getRow() {
		return row;
	}

	public void setRow(CmsContentsIndexedTableRow row) {
		this.row = row;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CmsContentsIndexedTableCell [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (row != null) {
			builder.append("row=");
			builder.append(row);
			builder.append(", ");
		}
		if (content != null) {
			builder.append("content=");
			builder.append(content);
		}
		builder.append("]");
		return builder.toString();
	}

}
