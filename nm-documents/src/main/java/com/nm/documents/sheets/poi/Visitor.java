package com.nm.documents.sheets.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface Visitor {
	public void startVisitWorkBook(Workbook workBook);

	public void startVisitSheet(Sheet sheet, int index, String name);

	public void startVisitRow(Row row, int index);

	public void startVisitCell(Cell cell, int index);

	public void endVisitCell(Cell cell, int index);

	public void endVisitRow(Row row, int index);

	public void endVisitSheet(Sheet sheet, int index, String name);

	public void endVisitWorkBook(Workbook workBook);
}