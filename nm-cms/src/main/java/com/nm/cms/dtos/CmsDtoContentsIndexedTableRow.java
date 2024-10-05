package com.nm.cms.dtos;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.nm.cms.constants.CmsTableHeader;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CmsDtoContentsIndexedTableRow implements CmsDtoContents {
	public enum RowOperation {
		Void, Created, Updated, Deleted
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private int rowNum;
	private RowOperation operation = RowOperation.Void;
	private List<CmsDtoContentsPrimitive> cells = Lists.newArrayList();

	public Long getContentId() {
		return getId();
	}

	public void setContentId(Long id) {
		setId(id);
	}

	public CmsDtoContentsIndexedTableRow() {
	}

	public CmsDtoContentsIndexedTableRow(Long idRow) {
		setId(id);
	}

	public CmsDtoContentsPrimitive getCellByHeader(CmsTableHeader h) {
		for (CmsDtoContentsPrimitive p : cells) {
			if (Objects.equal(p.getHeader(), h)) {
				return p;
			}
		}
		return null;
	}

	public CmsDtoContentsPrimitive getOrCreateCellByHeader(CmsTableHeader h) {
		CmsDtoContentsPrimitive found = getCellByHeader(h);
		if (found == null) {
			return createCell().setHeader(h);
		} else {
			return found;
		}
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public RowOperation getOperation() {
		return operation;
	}

	public CmsDtoContentsIndexedTableRow setOperation(RowOperation operation) {
		this.operation = operation;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CmsDtoContentsPrimitive> getCells() {
		return cells;
	}

	public void setCells(List<CmsDtoContentsPrimitive> cells) {
		this.cells = cells;
	}

	public CmsDtoContentsPrimitive createCell() {
		CmsDtoContentsPrimitive cell = new CmsDtoContentsPrimitive();
		this.cells.add(cell);
		return cell;
	}
}
