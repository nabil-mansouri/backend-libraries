package com.nm.documents;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TableModelRow extends ArrayList<TableModelCell> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TableModelRow addCell(TableModelCell cell) {
		this.add(cell);
		return this;
	}

	public JSONArray toJSONObject() {
		JSONArray row = new JSONArray();
		for (TableModelCell col : this) {
			row.put(col.getValue());
		}
		return row;
	}

	public String toJSON() {
		return toJSONObject().toString();
	}
}
