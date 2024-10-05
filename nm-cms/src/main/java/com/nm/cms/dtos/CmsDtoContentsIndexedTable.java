package com.nm.cms.dtos;

import java.util.List;

import com.google.common.collect.Lists;
import com.nm.cms.constants.CmsTableHeader;
import com.nm.utils.tables.ITable;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CmsDtoContentsIndexedTable implements CmsDtoContents, ITable<CmsDtoContentsPrimitive> {
	public enum TableOperation {
		Void, Updated, Deleted
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private TableOperation operation = TableOperation.Void;
	private List<CmsTableHeader> headers = Lists.newArrayList();
	private List<CmsDtoContentsIndexedTableRow> rows = Lists.newArrayList();

	public CmsDtoContentsIndexedTable() {
	}

	public CmsDtoContentsIndexedTable(Long id) {
		setId(id);
	}

	public Long getContentId() {
		return getId();
	}

	public void setContentId(Long id) {
		setId(id);
	}

	public List<CmsTableHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<CmsTableHeader> headers) {
		this.headers = headers;
	}

	public TableOperation getOperation() {
		return operation;
	}

	public void setOperation(TableOperation operation) {
		this.operation = operation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CmsDtoContentsIndexedTableRow> getRows() {
		return rows;
	}

	public CmsDtoContentsIndexedTableRow getRow(int index) {
		return rows.get(index);
	}

	public void setRows(List<CmsDtoContentsIndexedTableRow> table) {
		this.rows = table;
	}

	public List<List<CmsDtoContentsPrimitive>> cells() {
		List<List<CmsDtoContentsPrimitive>> all = Lists.newArrayList();
		for (CmsDtoContentsIndexedTableRow row : this.rows) {
			all.add(row.getCells());
		}
		return all;
	}

	public List<CmsDtoContentsPrimitive> createRow() {
		CmsDtoContentsIndexedTableRow row = new CmsDtoContentsIndexedTableRow();
		rows.add(row);
		return row.getCells();
	}

	public CmsDtoContentsIndexedTableRow createRowDto() {
		CmsDtoContentsIndexedTableRow row = new CmsDtoContentsIndexedTableRow();
		rows.add(row);
		return row;
	}

	public CmsDtoContentsIndexedTableRow createRowDto(int index) {
		CmsDtoContentsIndexedTableRow row = new CmsDtoContentsIndexedTableRow();
		rows.add(index, row);
		return row;
	}
}
