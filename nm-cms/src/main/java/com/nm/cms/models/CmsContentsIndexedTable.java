package com.nm.cms.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.google.common.collect.Lists;
import com.nm.cms.constants.CmsTableHeader;
import com.nm.utils.json.EnumHibernateType;
import com.nm.utils.tables.ITable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cms_contents_indexed_table", schema = "mod_cms")
public class CmsContentsIndexedTable extends CmsContents implements ITable<CmsContentsIndexedTableCell> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "headers")
	@Type(type = EnumHibernateType.EE)
	@ElementCollection(targetClass = CmsTableHeader.class)
	@CollectionTable(name = "nm_cms_contents_indexed_table_header", schema = "mod_cms", joinColumns = @JoinColumn(name = "id") )
	protected List<CmsTableHeader> headers = Lists.newArrayList();
	/**
	 * 
	 */
	@OneToMany(orphanRemoval = true, mappedBy = "table", cascade = CascadeType.ALL)
	protected List<CmsContentsIndexedTableRow> rows = Lists.newArrayList();

	public List<CmsTableHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<CmsTableHeader> headers) {
		this.headers = headers;
	}

	public CmsContentsIndexedTableRow getByIndex(int index) {
		return rows.get(index);
	}

	public void add(CmsContentsIndexedTableRow row) {
		this.rows.add(row);
		row.setTable(this);
	}

	public List<CmsContentsIndexedTableRow> getRows() {
		return rows;
	}

	public void setRows(List<CmsContentsIndexedTableRow> rows) {
		this.rows = rows;
	}

	public List<List<CmsContentsIndexedTableCell>> cells() {
		List<List<CmsContentsIndexedTableCell>> all = Lists.newArrayList();
		for (CmsContentsIndexedTableRow r : rows) {
			all.add(r.cells);
		}
		return all;
	}

	public List<CmsContentsIndexedTableCell> createRow() {
		CmsContentsIndexedTableRow row = createRowModel();
		return row.cells;
	}

	public CmsContentsIndexedTableRow createRowModel() {
		CmsContentsIndexedTableRow row = new CmsContentsIndexedTableRow();
		this.add(row);
		return row;
	}
}
