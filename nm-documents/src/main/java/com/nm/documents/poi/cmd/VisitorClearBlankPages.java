package com.nm.documents.poi.cmd;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nm.documents.poi.WordBuilderPoi;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorClearBlankPages implements Visitor {
	protected XWPFRun lastRUn;
	protected XWPFParagraph lastPar;
	private Monitor monitor = new Monitor();
	private MonitorBR monitorBRs = new MonitorBR();
	private List<XWPFParagraph> toRemovePar = Lists.newArrayList();

	private static class Monitor {
		private boolean lock;
		private List<XWPFParagraph> buffer = Lists.newArrayList();

		public void push(XWPFParagraph par) {
			if (!lock) {
				this.buffer.add(par);
			}
		}

		public List<XWPFParagraph> commit() {
			List<XWPFParagraph> copy = Lists.newArrayList(this.buffer);
			this.lock = true;
			this.buffer.clear();
			return copy;
		}

		public void beginIfRolledBack() {
			if (lock) {
				begin();
			}
		}

		public void begin() {
			this.lock = false;
			this.buffer.clear();
		}

		public void rollback() {
			this.lock = true;
			this.buffer.clear();
		}

	}

	private class MonitorBR {
		private boolean lock;
		private List<XWPFRun> buffer = Lists.newArrayList();

		public void push(XWPFRun par) {
			if (!lock) {
				this.buffer.add(par);
			}
		}

		@SuppressWarnings("deprecation")
		private void saveBR() {
			if (this.buffer.size() > 1) {
				for (int i = 0; i < buffer.size() - 1; i++) {
					XWPFRun run = buffer.get(i);
					run.getParagraph().removeRun(run.getParagraph().getRuns().indexOf(run));
				}
			}
		}

		public List<XWPFRun> commit() {
			saveBR();
			List<XWPFRun> copy = Lists.newArrayList(this.buffer);
			this.lock = true;
			this.buffer.clear();
			return copy;
		}

		public void beginIfRolledBack() {
			if (lock) {
				begin();
			}
		}

		public void begin() {
			this.lock = false;
			this.buffer.clear();
		}

		public void rollback() {
			saveBR();
			this.lock = true;
			this.buffer.clear();
		}

	}

	public void startVisitRun(XWPFRun run, int index) {
		lastRUn = run;
		if (!Strings.isNullOrEmpty(StringUtils.trim(lastRUn.toString()))) {
			monitor.rollback();
			monitorBRs.rollback();
		}
	}

	public void startVisitTableCell(XWPFTableCell cell, boolean firstRow, boolean lastRow, boolean firstCol, boolean lastCol,
			List<XWPFTableCell> vMergedCells) {
	}

	public void endVisitRun(XWPFRun run, int i) {
	}

	public void startVisitTable(XWPFTable table, float[] colWidths) {
		monitor.rollback();
		monitorBRs.rollback();
	}

	public void startVisitRow(XWPFTableRow row, int rowIndex, boolean headerRow) {
	}

	public void startVisitParagraph(XWPFParagraph paragraph, int index) {
		lastPar = paragraph;
		monitor.beginIfRolledBack();
		if (!Strings.isNullOrEmpty(StringUtils.trim(paragraph.getText()))) {
			monitor.rollback();
		}
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
		monitor.push(paragraph);
	}

	public void endVisitDocument() {
		monitorBRs.commit();
		for (XWPFParagraph p : toRemovePar) {
			WordBuilderPoi.removeParagraph(p);
			int pos = p.getDocument().getPosOfParagraph(p);
			p.getDocument().removeBodyElement(pos);
		}
	}

	public void visitBR(CTBr o) {
		toRemovePar.addAll(monitor.commit());
		monitorBRs.beginIfRolledBack();
		monitorBRs.push(lastRUn);
	}

	public void visitStyleText(XWPFRun run, String string) {
	}

	public void visitPicture(CTPicture picture, XWPFPictureData data, Float offsetX,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromH.Enum relativeFromH, Float offsetY,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromV.Enum relativeFromV,
			org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STWrapText.Enum wrapText) {
		monitor.rollback();
		monitorBRs.rollback();
	}

	public void visitTab(CTPTab o) {
	}

	public void visitTabs(CTTabs tabs) {
	}

	public void visitText(CTText ctText, XWPFRun run, int index) {
		if (!Strings.isNullOrEmpty(StringUtils.trim(ctText.getStringValue()))) {
			monitor.rollback();
			monitorBRs.rollback();
		}
	}
}
