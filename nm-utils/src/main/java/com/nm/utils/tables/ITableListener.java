package com.nm.utils.tables;

import java.util.List;

/**
 * 
 * @author Nabil
 * 
 */
public interface ITableListener<T> {
	public void onCell(T cell, int numRow, int numCol);

	public void onRow(List<T> cell, int numRow);

	public void onEndRow(List<T> cell, int numRow);
}
