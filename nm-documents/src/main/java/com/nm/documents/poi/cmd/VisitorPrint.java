package com.nm.documents.poi.cmd;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPTab;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

public class VisitorPrint implements Visitor {

	public void startVisitRun(XWPFRun run, int index) {
		System.out.println(String.format("RUN: %s", run.getPictureText()));
	}

	public void startVisitTableCell(XWPFTableCell cell, boolean firstRow, boolean lastRow, boolean firstCol, boolean lastCol,
			List<XWPFTableCell> vMergedCells) {
		System.out.println(String.format("CELL: %s", cell.getText()));
	}

	public void endVisitRun(XWPFRun run, int i) {
		System.out.println(String.format("END RUN: %s", run.getPictureText()));
	}

	public void startVisitTable(XWPFTable table, float[] colWidths) {
		System.out.println(String.format("TABLE: %s", table.getText()));
	}

	public void startVisitRow(XWPFTableRow row, int rowIndex, boolean headerRow) {
		System.out.println(String.format("ROW: %s", row.toString()));
	}

	public void startVisitParagraph(XWPFParagraph paragraph, int index) {
		System.out.println(String.format("PARAGRAPH: %s", paragraph.getText()));
	}

	public void startVisitDocument() {
		System.out.println("START DOC");
	}

	public void endVisitTableRow(XWPFTableRow row, boolean firstRow, boolean lastRow, boolean headerRow) {
		System.out.println(String.format("END ROW: %s", row.toString()));
	}

	public void endVisitTableCell(XWPFTableCell cell) {
		System.out.println(String.format("END CELL: %s", cell.getText()));
	}

	public void endVisitTable(XWPFTable table, float[] colWidths) {
		System.out.println(String.format("END TABLE: %s", table.getText()));
	}

	public void endVisitParagraph(XWPFParagraph paragraph, int index) {
		System.out.println(String.format("END PARAGRAPH: %s", paragraph.getText()));
	}

	public void endVisitDocument() {
		System.out.println("END DOC");
	}

	public void visitBR(CTBr o) {
		System.out.println(String.format("VISIT BR: %s", o));
	}

	public void visitStyleText(XWPFRun run, String string) {
		System.out.println(String.format("VISIT STYLE TEXT: %s", string));
	}

	public void visitPicture(CTPicture picture, XWPFPictureData data, Float offsetX,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromH.Enum relativeFromH, Float offsetY,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromV.Enum relativeFromV,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STWrapText.Enum wrapText) {
		System.out.println(String.format("VISIT PICTURE : %s", (data != null) ? data.getFileName() : ""));
	}

	public void visitTab(CTPTab o) {
		System.out.println(String.format("VISIT TAB: %s", o));
	}

	public void visitTabs(CTTabs tabs) {
		System.out.println(String.format("VISIT TABS: %s", tabs));

	}

	public void visitText(CTText ctText, XWPFRun run, int index) {
		System.out.println(String.format("VISIT CTEXT: %s", ctText.getStringValue()));
	}
}
