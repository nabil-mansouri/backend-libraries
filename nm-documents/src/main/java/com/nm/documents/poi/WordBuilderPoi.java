package com.nm.documents.poi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.channels.NotYetBoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.springframework.util.Assert;

import com.nm.app.utils.UUIDUtils;
import com.nm.documents.PositionModel;
import com.nm.documents.TableModel;
import com.nm.documents.TableModelCell;
import com.nm.documents.WordBuilder;
import com.nm.documents.aspose.parser.PatternFinder;
import com.nm.documents.aspose.parser.PatternState;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class WordBuilderPoi extends WordBuilder {
	private XWPFDocument document;
	public static final long TO_POINTS_DIVISOR = 20;
	public static final long TO_INCHES_DIVISOR = 72;
	public static final double TO_CM_MULTIPLIER = 2.54;

	public XWPFDocument getDocument() {
		return document;
	}

	public WordBuilderPoi() {
	}

	@Override
	public void clear(PatternState state) {
		throw new NotYetBoundException();

	}

	@Override
	public void removePicture(PositionModel positionModel) {
		super.removePicture(positionModel);
	}

	@Override
	public void removeParagraph(PositionModel index) {
		int i = document.getPosOfParagraph(index.getPargraph());
		document.removeBodyElement(i);
	}

	@Override
	public void replaceTextbeforeRun(PositionModel position, String before, String after) throws Exception {
		throw new NotYetBoundException();
	}

	@Override
	public void insertTextbeforeRun(PositionModel position, String text) throws Exception {
		throw new NotYetBoundException();
	}

	public static void cloneParagraph(XWPFParagraph clone, XWPFParagraph source) {
		if (clone == null)
			return;
		CTPPr pPr = clone.getCTP().isSetPPr() ? clone.getCTP().getPPr() : clone.getCTP().addNewPPr();
		pPr.set(source.getCTP().getPPr());
		for (XWPFRun r : source.getRuns()) {
			XWPFRun nr = clone.createRun();
			cloneRun(nr, r);
		}
	}

	public static void cloneRun(XWPFRun clone, XWPFRun source) {
		if (clone == null)
			return;
		CTRPr rPr = clone.getCTR().isSetRPr() ? clone.getCTR().getRPr() : clone.getCTR().addNewRPr();
		rPr.set(source.getCTR().getRPr());
		clone.setText(source.getText(0));
	}

	public static void cloneTable(XWPFTable clone, XWPFTable source) {
		if (clone == null)
			return;
		if (!clone.getRows().isEmpty()) {
			clone.removeRow(0);
		}
		//
		List<XWPFTableRow> rows = source.getRows();
		for (XWPFTableRow row : rows) {
			XWPFTableRow cRow = clone.createRow();
			int i = 0;
			for (XWPFTableCell cell : row.getTableCells()) {
				XWPFTableCell cCell = cRow.getCell(i);
				if (cCell == null) {
					cCell = cRow.createCell();
				}
				if (cell.getVerticalAlignment() != null) {
					cCell.setVerticalAlignment(cell.getVerticalAlignment());
				}
				for (XWPFParagraph para : cell.getParagraphs()) {
					XWPFParagraph cPara = null;
					if (cCell.getParagraphs().isEmpty()) {
						cPara = cCell.addParagraph();
					} else {
						cPara = cCell.getParagraphs().iterator().next();
					}
					cloneParagraph(cPara, para);
				}
				i++;
			}
		}
	}

	public static void removeParagraph(XWPFParagraph paragraph) {
		System.out.println(paragraph.getText());
		for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
			try {
				paragraph.removeRun(i);
			} catch (Exception e) {

			}
		}
		System.out.println(paragraph.getText());
	}

	public void styleCell(XWPFTableCell cell, TableModelCell modelCell, TableModel tableModel, boolean header) {
		XWPFRun r = null;
		if (cell.getParagraphs().isEmpty()) {
			r = cell.addParagraph().createRun();
		} else {
			r = cell.getParagraphs().iterator().next().createRun();
		}
		Assert.notNull(modelCell.getValue());
		r.setText(modelCell.getValue());
		if (tableModel.getFontSize() != null) {
			r.setFontSize(tableModel.getFontSize());
		}
		if (header) {
			r.setBold(true);
		}
		if (tableModel.getRowSize() != null) {
			XWPFTableRow row = cell.getTableRow();
			CTTrPr trPr = row.getCtRow().addNewTrPr();
			// set row height; units = twentieth of a point, 360 = 0.25"
			CTHeight ht = trPr.addNewTrHeight();
			ht.setVal(BigInteger.valueOf(180));
		}
		//
		if (modelCell.getGridspan() != null) {
			if (cell.getCTTc().getTcPr() == null)
				cell.getCTTc().addNewTcPr();
			if (cell.getCTTc().getTcPr().getGridSpan() == null)
				cell.getCTTc().getTcPr().addNewGridSpan();
			cell.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(modelCell.getGridspan()));
		}
		cell.setVerticalAlignment(XWPFVertAlign.CENTER);
		// CENTER NUMBER
		if (modelCell.getCenter() != null && modelCell.getCenter()) {
			cell.getParagraphs().iterator().next().setAlignment(ParagraphAlignment.CENTER);
		}
	}

	@Override
	public void buildTable(PositionModel position, TableModel tableModel) throws Exception {
		// create table
		@SuppressWarnings("deprecation")
		XmlCursor cursor = position.getRun().getParagraph().getCTP().newCursor();
		if (tableModel.getBreakPage() != null && tableModel.getBreakPage()) {
			XWPFParagraph p = document.insertNewParagraph(cursor);
			XWPFRun run = p.createRun();
			run.addCarriageReturn(); // separate previous text from break
			run.addBreak(BreakType.PAGE);
			run.addBreak(BreakType.TEXT_WRAPPING);
			cursor = p.getCTP().newCursor();
		}
		XWPFTable table = document.insertNewTbl(cursor);
		//
		if (tableModel.getStyle() != null) {
			CTTblPr tblPr = table.getCTTbl().getTblPr();
			org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString styleStr = tblPr.addNewTblStyle();
			// styleStr.setVal("TableauListe4-Accentuation1");
			// styleStr.setVal("TableauGrille4-Accentuation2");
			// styleStr.setVal("TableauGrille4-Accentuation4");
			styleStr.setVal(tableModel.getStyle());
		}
		//
		if (tableModel.isFullWidth()) {
			table.setWidth(5800);
		}
		// create first row
		XWPFTableRow tableRowOne = table.getRow(0);
		Iterator<TableModelCell> it = tableModel.getColumnNames().iterator();
		for (int cpt = 0; it.hasNext(); cpt++) {
			if (tableRowOne.getCell(cpt) == null) {
				tableRowOne.createCell();
			}
			XWPFTableCell cell = tableRowOne.getCell(cpt);
			TableModelCell modelCell = it.next();
			styleCell(cell, modelCell, tableModel, true);
		}
		//
		Iterator<List<TableModelCell>> it2 = tableModel.getRows().iterator();
		for (int cpt = 1; it2.hasNext(); cpt++) {
			if (table.getRow(cpt) == null) {
				table.createRow();
			}
			XWPFTableRow row = table.getRow(cpt);
			//
			Iterator<TableModelCell> it3 = it2.next().iterator();
			for (int cpt2 = 0; it3.hasNext(); cpt2++) {
				if (row.getCell(cpt2) == null) {
					row.createCell();
				}
				XWPFTableCell cell = row.getCell(cpt2);
				TableModelCell modelCell = it3.next();
				styleCell(cell, modelCell, tableModel, false);
			}
		}
	}

	@Override
	public void insertText(String text) throws Exception {
		throw new NotYetBoundException();
	}

	@Override
	public void moveBeforeParagraph(PositionModel position) {
		throw new NotYetBoundException();
	}

	@Override
	public void moveAfterParagraph(PositionModel position) throws Exception {
		throw new NotYetBoundException();
	}

	@Override
	public void createDoc(InputStream input) throws Exception {
		// Blank Document
		this.document = new XWPFDocument(input);
	}

	@Override
	public void createDoc() throws Exception {
		// Blank Document
		this.document = new XWPFDocument();
	}

	@Override
	public void iterate(PatternFinder finder) {
		// TODO
		// Iterator<XWPFParagraph> p = document.getParagraphsIterator();
		// int j = 0;
		// while (p.hasNext()) {
		// XWPFParagraph paragraph = p.next();
		// List<XWPFRun> xwpfRuns = paragraph.getRuns();
		// int i = 0;
		// for (XWPFRun xwpfRun : xwpfRuns) {
		// int pos = xwpfRun.getTextPosition();
		// String xwpfRunText = xwpfRun.getText(pos);
		// finder.accept(new PatternState(xwpfRunText, paragraph, run, j, i));
		// i++;
		// }
		// j++;
		// }
	}

	@Override
	public void importHtml(String html) {
		throw new NotYetBoundException();
	}

	@Override
	public void close() throws IOException {
		this.document.close();
	}

	@Override
	public void writeAsHtml(OutputStream output) throws Exception {
		throw new NotYetBoundException();
	}

	@Override
	public void newPage() {
		document.createParagraph().createRun().addBreak(BreakType.PAGE);
	}

	@Override
	public void pushPngImage(InputStream is, String string) throws Exception {
		pushPngImage(new PositionModel(document.createParagraph().createRun()), is, string);
	}

	protected double getPageWidth() {
		CTSectPr sectPr = document.getDocument().getBody().getSectPr();
		long widthPage = sectPr.getPgSz().getW().longValue();
		long widthRightMargin = sectPr.getPgMar().getRight().longValue();
		long widthLeftMargin = sectPr.getPgMar().getLeft().longValue();
		double widthA = (widthPage - widthLeftMargin - widthRightMargin) / TO_POINTS_DIVISOR;
		return widthA;
	}

	protected double getPageHeight() {
		CTSectPr sectPr = document.getDocument().getBody().getSectPr();
		long heightPage = sectPr.getPgSz().getH().longValue();
		long heightTop = sectPr.getPgMar().getTop().longValue();
		long heightBotMargin = sectPr.getPgMar().getBottom().longValue();
		double heiA = (heightPage - heightTop - heightBotMargin) / TO_POINTS_DIVISOR;
		return heiA;
	}

	@Override
	public void pushPngImage(PositionModel position, InputStream is, String name, int height) throws Exception {
		position.getRun().addBreak();
		position.getRun().addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, name, Units.toEMU(getPageWidth()),
				Units.toEMU(Math.min(getPageHeight(), height)));
	}

	@Override
	public void pushPngImage(PositionModel position, ByteArrayInputStream is, String name, Double heightRatio) throws Exception {
		position.getRun().addBreak();
		position.getRun().addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, name, Units.toEMU(getPageWidth()),
				Units.toEMU(getPageHeight() * heightRatio));
	}

	@Override
	public void pushPngImage(PositionModel position, InputStream is, String name) throws Exception {
		double ratioH = 0.33;
		position.getRun().addBreak();
		position.getRun().addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, name, Units.toEMU(getPageWidth()),
				Units.toEMU(getPageHeight() * ratioH));
	}

	@Override
	public File writeFile() throws IOException {
		File f = File.createTempFile(UUIDUtils.uuid(16), ".tmp");
		this.write(new FileOutputStream(f));
		return f;
	}

	@Override
	public void write(OutputStream output) throws IOException {
		document.enforceUpdateFields();
		document.write(output);
		output.close();
		System.out.println("createdocument .docx written successully");
	}

	public void writeMac(OutputStream output) throws IOException {
		document.write(output);
		output.close();
		System.out.println("createdocument .docx written successully");
	}

	@Override
	public void removeRowHaving(String pattern) {
		Iterator<XWPFParagraph> p = document.getParagraphsIterator();
		while (p.hasNext()) {
			XWPFParagraph paragraph = p.next();
			List<XWPFRun> xwpfRuns = paragraph.getRuns();
			List<Integer> toRemove = new ArrayList<Integer>();
			int i = 0;
			for (XWPFRun xwpfRun : xwpfRuns) {
				int pos = xwpfRun.getTextPosition();
				String xwpfRunText = xwpfRun.getText(pos);
				if (StringUtils.containsIgnoreCase(xwpfRunText, pattern)) {
					toRemove.add(i);
				}
				i++;
			}
			for (Integer pos : toRemove) {
				paragraph.removeRun(pos);
			}
		}
	}
}
