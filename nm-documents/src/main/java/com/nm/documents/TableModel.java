package com.nm.documents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TableModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TableModelCell> columnNames = new ArrayList<TableModelCell>();
	private List<List<TableModelCell>> rows = new ArrayList<List<TableModelCell>>();
	private boolean fullWidth;
	private Integer fontSize;
	private Integer rowSize;
	private Boolean breakPage;
	private Boolean styled;
	private String style;
	private boolean insertMode;
	private StyleDirection copyStyle;

	public StyleDirection getCopyStyle() {
		return copyStyle;
	}

	public void setCopyStyle(StyleDirection copyStyle) {
		this.copyStyle = copyStyle;
	}

	public boolean isInsertMode() {
		return insertMode;
	}

	public void setInsertMode(boolean insertMode) {
		this.insertMode = insertMode;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Boolean getStyled() {
		return styled;
	}

	public void setStyled(Boolean styled) {
		this.styled = styled;
	}

	public Boolean getBreakPage() {
		return breakPage;
	}

	public void setBreakPage(Boolean breakPage) {
		this.breakPage = breakPage;
	}

	public Integer getRowSize() {
		return rowSize;
	}

	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void addColumnName(TableModelCell n) {
		this.columnNames.add(n);
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public boolean isFullWidth() {
		return fullWidth;
	}

	public void setFullWidth(boolean fullWidth) {
		this.fullWidth = fullWidth;
	}

	public List<TableModelCell> getColumnNames() {
		return columnNames;
	}

	public List<TableModelCell> createRow() {
		List<TableModelCell> l = new ArrayList<TableModelCell>();
		this.rows.add(l);
		return l;
	}

	public List<List<TableModelCell>> getRows() {
		return rows;
	}

	public TableModelCell cell(int row, int col) {
		return this.rows.get(row).get(col);
	}

	public void setColumnNames(List<TableModelCell> columnNames) {
		this.columnNames = columnNames;
	}

	public void setRows(List<List<TableModelCell>> rows) {
		this.rows = rows;
	}

	public TableModel pushColumn(TableModelCell name) {
		this.columnNames.add(name);
		return this;
	}

	public TableModel startRow() {
		this.rows.add(new ArrayList<TableModelCell>());
		return this;
	}

	public TableModel pushCell(TableModelCell cell) {
		this.rows.get(this.rows.size() - 1).add(cell);
		return this;
	}

	public TableModel addColumnName(String string) {
		this.addColumnName(new TableModelCell(string));
		return this;
	}

	public TableModel pushCell(String string) {
		this.pushCell(new TableModelCell(string));
		return this;
	}

	public String toCSV() {
		List<String> lines = Lists.newArrayList();
		//
		List<String> header = Lists.newArrayList();
		for (TableModelCell c : this.columnNames) {
			header.add(c.getValue());
		}
		lines.add(StringUtils.join(header, ";"));
		//
		for (List<TableModelCell> row : this.rows) {
			List<String> col = Lists.newArrayList();
			for (TableModelCell c : row) {
				col.add(c.getValue());
			}
			lines.add(StringUtils.join(col, ";"));
		}
		return StringUtils.join(lines, "\n");
	}
}
