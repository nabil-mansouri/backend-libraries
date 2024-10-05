package com.nm.utils.tables;

import java.util.List;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class ITableIterator {
	public <T> void iterate(ITable<T> table, ITableListener<T> listener) {
		List<? extends List<T>> cells = table.cells();
		int numRow = 0;
		for (List<T> rows : cells) {
			listener.onRow(rows, numRow);
			int numCol = 0;
			for (T cell : rows) {
				listener.onCell(cell, numRow, numCol);
				numCol++;
			}
			listener.onEndRow(rows, numRow);
			numRow++;
		}
	}

	public <T> void iterate(List<? extends List<T>> cells, ITableListener<T> listener) {
		int numRow = 0;
		for (List<T> rows : cells) {
			listener.onRow(rows, numRow);
			int numCol = 0;
			for (T cell : rows) {
				listener.onCell(cell, numRow, numCol);
				numCol++;
			}
			listener.onEndRow(rows, numRow);
			numRow++;
		}
	}
}
