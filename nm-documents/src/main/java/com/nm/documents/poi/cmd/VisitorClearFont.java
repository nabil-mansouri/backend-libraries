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

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorClearFont implements Visitor {
	private final String font;

	public VisitorClearFont(String font) {
		this.font = font;
	}

	public void startVisitRun(XWPFRun run, int index) {
		run.setFontFamily(font);
	}

	public void startVisitTableCell(XWPFTableCell cell, boolean firstRow, boolean lastRow, boolean firstCol, boolean lastCol,
			List<XWPFTableCell> vMergedCells) {
	}

	public void endVisitRun(XWPFRun run, int i) {
	}

	public void startVisitTable(XWPFTable table, float[] colWidths) {

	}

	public void startVisitRow(XWPFTableRow row, int rowIndex, boolean headerRow) {
	}

	public void startVisitParagraph(XWPFParagraph paragraph, int index) {

	}

	public void startVisitDocument() {
	}

	public void endVisitTableRow(XWPFTableRow row, boolean firstRow, boolean lastRow, boolean headerRow) {
	}

	public void endVisitTableCell(XWPFTableCell cell) {
	}

	public void endVisitTable(XWPFTable table, float[] colWidths) {
	}

	public void endVisitParagraph(XWPFParagraph paragraph, int index) {

	}

	public void endVisitDocument() {

	}

	public void visitBR(CTBr o) {

	}

	public void visitStyleText(XWPFRun run, String string) {
	}

	public void visitPicture(CTPicture picture, XWPFPictureData data, Float offsetX,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromH.Enum relativeFromH, Float offsetY,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromV.Enum relativeFromV,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STWrapText.Enum wrapText) {

	}

	public void visitTab(CTPTab o) {
	}

	public void visitTabs(CTTabs tabs) {
	}

	public void visitText(CTText ctText, XWPFRun run, int index) {

	}
}
