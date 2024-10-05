package com.nm.cms.dtos;

import java.util.List;

import com.google.common.collect.Lists;
import com.nm.utils.tables.ITable;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CmsDtoContentsTable implements CmsDtoContents, ITable<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private List<List<String>> table = Lists.newArrayList();

	public Long getContentId() {
		return getId();
	}

	public void setContentId(Long id) {
		setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<List<String>> getTable() {
		return table;
	}

	public void setTable(List<List<String>> table) {
		this.table = table;
	}

	public List<List<String>> cells() {
		return table;
	}

	public List<String> createRow() {
		List<String> row = Lists.newArrayList();
		table.add(row);
		return row;
	}
}
