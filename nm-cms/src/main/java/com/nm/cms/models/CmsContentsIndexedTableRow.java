package com.nm.cms.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_cms_contents_indexed_table_row", schema = "mod_cms")
public class CmsContentsIndexedTableRow extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(optional = false)
	private CmsContentsIndexedTable table;
	@OneToMany(orphanRemoval = true, mappedBy = "row", cascade = CascadeType.ALL)
	protected List<CmsContentsIndexedTableCell> cells = Lists.newArrayList();
	@Id
	@SequenceGenerator(name = "seq_nm_cms_contents_indexed_table_row", schema = "mod_cms", sequenceName = "seq_nm_cms_contents_indexed_table_row", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_cms_contents_indexed_table_row")
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CmsContentsIndexedTable getTable() {
		return table;
	}

	public void setTable(CmsContentsIndexedTable table) {
		this.table = table;
	}

	public List<CmsContentsIndexedTableCell> getCells() {
		return cells;
	}

	public void setCells(List<CmsContentsIndexedTableCell> cells) {
		this.cells = cells;
	}

	public boolean add(CmsContentsIndexedTableCell cell) {
		cell.setRow(this);
		return cells.add(cell);
	}

	public void clear() {
		for (CmsContentsIndexedTableCell c : this.cells) {
			c.setRow(null);
		}
		this.cells.clear();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CmsContentsIndexedTableRow [");
		if (table != null) {
			builder.append("table=");
			builder.append(table);
			builder.append(", ");
		}
		if (cells != null) {
			builder.append("cells=");
			builder.append(cells);
			builder.append(", ");
		}
		if (id != null) {
			builder.append("id=");
			builder.append(id);
		}
		builder.append("]");
		return builder.toString();
	}

}
