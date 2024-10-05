package com.nm.documents.poi.cmd;

import java.util.ArrayList;
import java.util.Collection;
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
public class VisitorCollection implements Visitor {
	private Collection<Visitor> visitors = new ArrayList<Visitor>();

	public void clear() {
		visitors.clear();
	}

	public void startVisitRun(XWPFRun run, int index) {
		for (Visitor v : visitors) {
			v.startVisitRun(run, index);
		}
	}

	public void endVisitRun(XWPFRun run, int i) {
		for (Visitor v : visitors) {
			v.endVisitRun(run, i);
		}
	}

	public void startVisitTableCell(XWPFTableCell cell, boolean firstRow, boolean lastRow, boolean firstCol, boolean lastCol,
			List<XWPFTableCell> vMergedCells) {
		for (Visitor v : visitors) {
			v.startVisitTableCell(cell, firstRow, lastRow, firstCol, lastCol, vMergedCells);
		}
	}

	public void startVisitTable(XWPFTable table, float[] colWidths) {
		for (Visitor v : visitors) {
			v.startVisitTable(table, colWidths);
		}
	}

	public void startVisitRow(XWPFTableRow row, int rowIndex, boolean headerRow) {
		for (Visitor v : visitors) {
			v.startVisitRow(row, rowIndex, headerRow);
		}
	}

	public void startVisitParagraph(XWPFParagraph paragraph, int index) {
		for (Visitor v : visitors) {
			v.startVisitParagraph(paragraph, index);
		}
	}

	public void startVisitDocument() {
		for (Visitor v : visitors) {
			v.startVisitDocument();
		}
	}

	public void endVisitTableRow(XWPFTableRow row, boolean firstRow, boolean lastRow, boolean headerRow) {
		for (Visitor v : visitors) {
			v.endVisitTableRow(row, firstRow, lastRow, headerRow);
		}
	}

	public void endVisitTableCell(XWPFTableCell cell) {
		for (Visitor v : visitors) {
			v.endVisitTableCell(cell);
		}
	}

	public void endVisitTable(XWPFTable table, float[] colWidths) {
		for (Visitor v : visitors) {
			v.endVisitTable(table, colWidths);
		}
	}

	public void endVisitParagraph(XWPFParagraph paragraph, int index) {
		for (Visitor v : visitors) {
			v.endVisitParagraph(paragraph, index);
		}
	}

	public void endVisitDocument() {
		for (Visitor v : visitors) {
			v.endVisitDocument();
		}
	}

	public void visitBR(CTBr o) {
		for (Visitor v : visitors) {
			v.visitBR(o);
		}
	}

	public void visitPicture(CTPicture picture, XWPFPictureData data, Float offsetX,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromH.Enum relativeFromH, Float offsetY,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromV.Enum relativeFromV,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STWrapText.Enum wrapText) {
		for (Visitor v : visitors) {
			v.visitPicture(picture, data, offsetX, relativeFromH, offsetY, relativeFromV, wrapText);
		}
	}

	public void visitStyleText(XWPFRun run, String string) {
		for (Visitor v : visitors) {
			v.visitStyleText(run, string);
		}
	}

	public void visitTab(CTPTab o) {
		for (Visitor v : visitors) {
			v.visitTab(o);
		}
	}

	public void visitTabs(CTTabs tabs) {
		for (Visitor v : visitors) {
			v.visitTabs(tabs);
		}
	}

	public void visitText(CTText ctText, XWPFRun run, int index) {
		for (Visitor v : visitors) {
			v.visitText(ctText, run, index);
		}
	}

	public VisitorCollection add(Visitor e) {
		visitors.add(e);
		return this;
	}

	public VisitorCollection addAll(Collection<? extends Visitor> c) {
		visitors.addAll(c);
		return this;
	}

}
