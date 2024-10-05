package com.nm.documents.sheets.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import com.nm.app.utils.UUIDUtils;
import com.nm.documents.StyleDirection;
import com.nm.documents.TableModel;
import com.nm.documents.TableModelCell;
import com.nm.utils.MathUtilExt;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class ExcelBuilderPoi {
	private Workbook workBook;

	public void setWorkBook(Workbook workBook) {
		this.workBook = workBook;
	}

	public void createDoc(InputStream input) throws Exception {
		// Blank Document
		this.workBook = new XSSFWorkbook(input);
	}

	public void createDoc() throws Exception {
		// Blank Document
		this.workBook = new XSSFWorkbook();
	}

	public Workbook getWorkBook() {
		return workBook;
	}

	public void close() throws IOException {
		this.workBook.close();
	}

	public Row createRow(Cell originalFirstCell, int num, boolean insert) {
		// FIRST ROW SHOULD NOT BE INSERTED
		Sheet sheet = originalFirstCell.getSheet();
		if (insert && num != originalFirstCell.getRowIndex()) {
			sheet.shiftRows(num, sheet.getLastRowNum(), 1);
		}
		// If the row exist in destination, push down all rows by 1 else create
		// a new row
		Row row = sheet.getRow(num);
		if (row != null) {
			return row;
		} else {
			return sheet.createRow(num);
		}
	}

	public File writeFile() throws IOException {
		File f = File.createTempFile(UUIDUtils.uuid(16), ".tmp");
		this.write(new FileOutputStream(f));
		return f;
	}

	public void write(OutputStream output) throws IOException {
		workBook.write(output);
		output.close();
		System.out.println("createdocument .xslx written successully");
	}

	public ExcelBuilderPoi() {
	}

	public static void setCell(Cell cell, String value) {
		if (MathUtilExt.isNumber(value)) {
			cell.setCellValue(MathUtilExt.toDouble(value));
		} else {
			cell.setCellValue(value);
		}
	}

	public void styleCell(Cell cell, TableModelCell modelCell, TableModel tableModel, boolean header) {
		Assert.notNull(modelCell.getValue());
		setCell(cell, modelCell.getValue());
		if (modelCell.getGridspan() != null) {
			CellRangeAddress range = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(),
					nextIndex(cell, modelCell) - 1);
			cell.getSheet().addMergedRegion(range);
		}
		if (modelCell.getCenter() != null && modelCell.getCenter()) {
			cell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
		}
	}

	private int nextIndex(Cell cell, TableModelCell model) {
		if (model.getGridspan() == null) {
			return 1 + cell.getColumnIndex();
		} else {
			return model.getGridspan() + cell.getColumnIndex();
		}
	}

	private void copyStyle(StyleDirection dir, Cell orig, Cell current, CellStyle style) {
		if (dir != null) {
			switch (dir) {
			case Horizontal:
				if (orig.getRowIndex() == current.getRowIndex()) {
					current.setCellStyle(style);
				}
				break;
			case Vertical:
				if (orig.getColumnIndex() == current.getColumnIndex()) {
					current.setCellStyle(style);
				}
				break;
			case All:
				Row rowOrig = orig.getRow();
				orig = rowOrig.getCell(current.getColumnIndex());
				if (orig != null) {
					current.setCellStyle(orig.getCellStyle());
				}
				break;

			}
		}
	}

	private Cell getOrCreate(Row row, int cellnum) {
		Cell cell = row.getCell(cellnum);
		if (cell == null) {
			return row.createCell(cellnum);
		} else {
			return cell;
		}
	}

	public void buildTable(Cell cellO, TableModel model) {
		int currentColumn = cellO.getColumnIndex();
		int currentRow = cellO.getRowIndex();
		Row row = null;
		CellStyle style = cellO.getCellStyle();
		for (TableModelCell column : model.getColumnNames()) {
			if (row == null) {
				row = createRow(cellO, currentRow++, model.isInsertMode());
			}
			Cell current = getOrCreate(row, currentColumn);
			currentColumn = nextIndex(current, column);
			styleCell(current, column, model, true);
			copyStyle(model.getCopyStyle(), cellO, current, style);
		}
		for (List<TableModelCell> rows : model.getRows()) {
			currentColumn = cellO.getColumnIndex();
			row = createRow(cellO, currentRow++, model.isInsertMode());
			for (TableModelCell column : rows) {
				Cell current = getOrCreate(row, currentColumn);
				currentColumn = nextIndex(current, column);
				styleCell(current, column, model, false);
				copyStyle(model.getCopyStyle(), cellO, current, style);
			}
		}
	}

	public void copyRow(Sheet worksheet, int sourceRowNum, int destinationRowNum) {
		// Get the source / new row
		Row newRow = worksheet.getRow(destinationRowNum);
		Row sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create
		// a new row
		if (newRow != null) {
			worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			CellStyle newCellStyle = workBook.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}
			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}

		// If there are are any merged regions in the source row, copy to new
		// row
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
						(newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
						cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
				worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}
}
