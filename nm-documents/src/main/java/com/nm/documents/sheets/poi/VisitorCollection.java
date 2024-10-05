package com.nm.documents.sheets.poi;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCollection implements Visitor {
	private Collection<Visitor> visitors = new ArrayList<Visitor>();

	public void clear() {
		visitors.clear();
	}

	public VisitorCollection add(Visitor e) {
		visitors.add(e);
		return this;
	}

	public VisitorCollection addAll(Collection<? extends Visitor> c) {
		visitors.addAll(c);
		return this;
	}

	public void startVisitWorkBook(Workbook workBook) {
		for (Visitor v : visitors) {
			v.startVisitWorkBook(workBook);
		}
	}

	public void startVisitSheet(Sheet sheet, int index, String name) {
		for (Visitor v : visitors) {
			v.startVisitSheet(sheet, index, name);
		}
	}

	public void startVisitRow(Row row, int index) {
		for (Visitor v : visitors) {
			v.startVisitRow(row, index);
		}
	}

	public void startVisitCell(Cell cell, int index) {
		for (Visitor v : visitors) {
			v.startVisitCell(cell, index);
		}
	}

	public void endVisitCell(Cell cell, int index) {
		for (Visitor v : visitors) {
			v.endVisitCell(cell, index);
		}
	}

	public void endVisitRow(Row row, int index) {
		for (Visitor v : visitors) {
			v.endVisitRow(row, index);
		}
	}

	public void endVisitSheet(Sheet sheet, int index, String name) {
		for (Visitor v : visitors) {
			v.endVisitSheet(sheet, index, name);
		}
	}

	public void endVisitWorkBook(Workbook workBook) {
		for (Visitor v : visitors) {
			v.endVisitWorkBook(workBook);
		}
	}

}
