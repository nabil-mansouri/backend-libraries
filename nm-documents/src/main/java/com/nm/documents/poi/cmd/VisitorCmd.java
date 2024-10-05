package com.nm.documents.poi.cmd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPTab;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.google.common.base.Strings;
import com.nm.documents.cmd.ICommand;

/***
 * 
 * @author nabilmansouri
 *
 */
public abstract class VisitorCmd implements Visitor {
	private String start = "{{";
	private String end = "}}";
	private String pattern = "\\{\\{(.*?)\\}\\}";

	public void startVisitDocument() {

	}

	public void startVisitParagraph(XWPFParagraph paragraph, int index) {
		try {
			System.out.println(String.format("TESTING RUN: %s", paragraph.getText()));
			Pattern MY_PATTERN = Pattern.compile(pattern);
			Matcher m = MY_PATTERN.matcher(paragraph.getText());
			Collection<CmdResult> all = new ArrayList<CmdResult>();
			while (m.find()) {
				String cmd = m.group(0);
				String inner = m.group(1);
				all.add(new CmdResult(cmd, inner));
			}
			onCmd(paragraph.getText(), paragraph, index, all);
		} catch (XmlValueDisconnectedException e) {
			// Because was removed
		}
	}

	protected abstract void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> all);

	public void startVisitTable(XWPFTable table, float[] colWidths) {

	}

	public void endVisitRun(XWPFRun run, int i) { 

	}

	public void endVisitTable(XWPFTable table, float[] colWidths) {

	}

	public void startVisitRun(XWPFRun run, int index) {

	}

	public void endVisitParagraph(XWPFParagraph paragraph, int index) {

	}

	public void endVisitDocument() {

	}

	public void startVisitRow(XWPFTableRow row, int rowIndex, boolean headerRow) {

	}

	public void endVisitTableRow(XWPFTableRow row, boolean firstRow, boolean lastRow, boolean headerRow) {

	}

	public void startVisitTableCell(XWPFTableCell cell, boolean firstRow, boolean lastRow, boolean firstCol, boolean lastCol,
			List<XWPFTableCell> vMergedCells) {

	}

	public void endVisitTableCell(XWPFTableCell cell) {

	}

	protected class CmdResult {
		protected String cmd;
		protected String inner;
		protected ICommand parsed;

		public CmdResult(String cmd, String inner) {
			super();
			this.cmd = cmd;
			this.inner = inner;
			this.parsed = ICommand.create(cmd, start, end);
		}

	}

	public void visitText(CTText ctText, XWPFRun run, int index) {
		try {
			System.out.println(String.format("TESTING RUN: %s", ctText.getStringValue()));
			Pattern MY_PATTERN = Pattern.compile(pattern);
			if (!Strings.isNullOrEmpty(ctText.getStringValue())) {
				Matcher m = MY_PATTERN.matcher(ctText.getStringValue());
				Collection<CmdResult> all = new ArrayList<CmdResult>();
				while (m.find()) {
					String cmd = m.group(0);
					String inner = m.group(1);
					all.add(new CmdResult(cmd, inner));
				}
				onCmd(ctText, run, index, all);
			}
		} catch (XmlValueDisconnectedException e) {
			// Because was removed
		}
	}

	protected abstract void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m);

	public void visitTab(CTPTab o) {

	}

	public void visitBR(CTBr o) {

	}

	public void visitTabs(CTTabs tabs) {

	}

	public void visitStyleText(XWPFRun run, String string) {

	}

	public void visitPicture(CTPicture picture, XWPFPictureData data, Float offsetX,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromH.Enum relativeFromH, Float offsetY,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromV.Enum relativeFromV,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STWrapText.Enum wrapText) {

	}

}
