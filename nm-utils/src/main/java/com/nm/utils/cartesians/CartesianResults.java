package com.nm.utils.cartesians;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CartesianResults<T> {
	private List<List<T>> rows = new ArrayList<List<T>>();

	public List<List<T>> getRows() {
		return rows;
	}

	public void setRows(List<List<T>> rows) {
		this.rows = rows;
	}

}
