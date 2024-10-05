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
public class SheetIterator {
	private Workbook workbook;

	public SheetIterator(Workbook workbook) {
		super();
		this.workbook = workbook;
	}

	public void start(Visitor v) {
		v.startVisitWorkBook(workbook);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			v.startVisitSheet(sheet, i, workbook.getSheetName(i));
			// Use index in order to keep asc workflow
			for (int rowNum = sheet.getFirstRowNum(); rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row != null) {
					v.startVisitRow(row, row.getRowNum());
					for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
						Cell cell = row.getCell(cellNum);
						if (cell != null) {
							v.startVisitCell(cell, cell.getColumnIndex());
							v.endVisitCell(cell, cell.getColumnIndex());
						}
					}
					v.endVisitRow(row, row.getRowNum());
				}
			}
			v.endVisitSheet(sheet, i, workbook.getSheetName(i));
		}
		v.endVisitWorkBook(workbook);
	}
}
