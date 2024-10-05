package com.nm.documents.poi.cmd;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.poi.xwpf.converter.core.styles.XWPFStylesDocument;
import org.apache.poi.xwpf.converter.core.utils.DxaUtil;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.apache.poi.xwpf.converter.core.utils.XWPFTableUtil;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ISDTContent;
import org.apache.poi.xwpf.usermodel.ISDTContents;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFFootnote;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFSDTContent;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObjectData;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTPosH;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTPosV;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTWrapSquare;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromH;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STRelFromV;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STWrapText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEmpty;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPTab;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class POIIterator {

	protected XWPFDocument doc;
	protected XWPFStylesDocument stylesDocument;

	public POIIterator(XWPFDocument doc) throws Exception {
		this.doc = doc;
		this.stylesDocument = new XWPFStylesDocument(doc);
	}

	protected void visitParagraph(Visitor v, XWPFParagraph p, int index) throws Exception {
		v.startVisitParagraph(p, index);
		for (int i = 0; i < p.getRuns().size(); i++) {
			XWPFRun run = p.getRuns().get(i);
			v.startVisitRun(run, i);
			visitRun(v, run, i);
			v.endVisitRun(run, i);
		}
		v.endVisitParagraph(p, index);
	}

	protected boolean isLastRow(int rowIndex, int rowsSize) {
		return rowIndex == rowsSize - 1;
	}

	protected int getCellIndex(int cellIndex, XWPFTableCell cell) {
		BigInteger gridSpan = stylesDocument.getTableCellGridSpan(cell.getCTTc().getTcPr());
		if (gridSpan != null) {
			cellIndex = cellIndex + gridSpan.intValue();
		} else {
			cellIndex++;
		}
		return cellIndex;
	}

	protected void visitTableRow(Visitor v, XWPFTableRow row, float[] colWidths, boolean firstRow, boolean lastRowIfNoneVMerge,
			int rowIndex, int rowsSize) throws Exception {
		boolean headerRow = stylesDocument.isTableRowHeader(row);
		v.startVisitRow(row, rowIndex, headerRow);
		int nbColumns = colWidths.length;
		// Process cell
		boolean firstCol = true;
		boolean lastCol = false;
		boolean lastRow = false;
		List<XWPFTableCell> vMergedCells = null;
		List<XWPFTableCell> cells = row.getTableCells();
		if (nbColumns > cells.size()) {
			firstCol = true;
			int cellIndex = -1;
			CTRow ctRow = row.getCtRow();
			XmlCursor c = ctRow.newCursor();
			c.selectPath("./*");
			while (c.toNextSelection()) {
				XmlObject o = c.getObject();
				if (o instanceof CTTc) {
					CTTc tc = (CTTc) o;
					XWPFTableCell cell = row.getTableCell(tc);
					cellIndex = getCellIndex(cellIndex, cell);
					lastCol = (cellIndex == nbColumns);
					vMergedCells = getVMergedCells(cell, rowIndex, cellIndex);
					if (vMergedCells == null || vMergedCells.size() > 0) {
						lastRow = isLastRow(lastRowIfNoneVMerge, rowIndex, rowsSize, vMergedCells);
						visitCell(v, cell, firstRow, lastRow, firstCol, lastCol, rowIndex, cellIndex, vMergedCells);
					}
					firstCol = false;
				} else if (o instanceof CTSdtCell) {
					// Fix bug of POI
					CTSdtCell sdtCell = (CTSdtCell) o;
					List<CTTc> tcList = sdtCell.getSdtContent().getTcList();
					for (CTTc ctTc : tcList) {
						XWPFTableCell cell = new XWPFTableCell(ctTc, row, row.getTable().getBody());
						cellIndex = getCellIndex(cellIndex, cell);
						lastCol = (cellIndex == nbColumns);
						vMergedCells = getVMergedCells(cell, rowIndex, cellIndex);
						if (vMergedCells == null || vMergedCells.size() > 0) {
							lastRow = isLastRow(lastRowIfNoneVMerge, rowIndex, rowsSize, vMergedCells);
							visitCell(v, cell, firstRow, lastRow, firstCol, lastCol, rowIndex, cellIndex, vMergedCells);
						}
						firstCol = false;
					}
				}
			}
			c.dispose();
		} else {
			// Column number is equal to cells number.
			for (int i = 0; i < cells.size(); i++) {
				lastCol = (i == cells.size() - 1);
				XWPFTableCell cell = cells.get(i);
				vMergedCells = getVMergedCells(cell, rowIndex, i);
				if (vMergedCells == null || vMergedCells.size() > 0) {
					lastRow = isLastRow(lastRowIfNoneVMerge, rowIndex, rowsSize, vMergedCells);
					visitCell(v, cell, firstRow, lastRow, firstCol, lastCol, rowIndex, i, vMergedCells);
				}
				firstCol = false;
			}
		}

		v.endVisitTableRow(row, firstRow, lastRow, headerRow);
		// );
	}

	protected void visitCell(Visitor v, XWPFTableCell cell, boolean firstRow, boolean lastRow, boolean firstCol, boolean lastCol,
			int rowIndex, int cellIndex, List<XWPFTableCell> vMergedCells) throws Exception {
		v.startVisitTableCell(cell, firstRow, lastRow, firstCol, lastCol, vMergedCells);
		visitTableCellBody(v, cell, vMergedCells);
		v.endVisitTableCell(cell);
	}

	protected void visitTableCellBody(Visitor v, XWPFTableCell cell, List<XWPFTableCell> vMergeCells) throws Exception {
		if (vMergeCells != null) {
			for (XWPFTableCell mergedCell : vMergeCells) {
				List<IBodyElement> bodyElements = mergedCell.getBodyElements();
				visitBodyElements(v, bodyElements);
			}
		} else {
			List<IBodyElement> bodyElements = cell.getBodyElements();
			visitBodyElements(v, bodyElements);
		}
	}

	protected boolean isLastRow(boolean lastRowIfNoneVMerge, int rowIndex, int rowsSize, List<XWPFTableCell> vMergedCells) {
		if (vMergedCells == null) {
			return lastRowIfNoneVMerge;
		}
		return isLastRow(rowIndex - 1 + vMergedCells.size(), rowsSize);
	}

	protected List<XWPFTableCell> getVMergedCells(XWPFTableCell cell, int rowIndex, int cellIndex) {
		try {
			List<XWPFTableCell> vMergedCells = null;
			STMerge.Enum vMerge = stylesDocument.getTableCellVMerge(cell);
			if (vMerge != null) {
				if (vMerge.equals(STMerge.RESTART)) {
					// vMerge="restart"
					// Loop for each table cell of each row upon
					// vMerge="restart"
					// was found or cell without vMerge
					// was declared.
					vMergedCells = new ArrayList<XWPFTableCell>();
					vMergedCells.add(cell);

					XWPFTableRow row = null;
					XWPFTableCell c;
					XWPFTable table = cell.getTableRow().getTable();
					for (int i = rowIndex + 1; i < table.getRows().size(); i++) {
						row = table.getRow(i);
						c = row.getCell(cellIndex);
						if (c == null) {
							break;
						}
						vMerge = stylesDocument.getTableCellVMerge(c);
						if (vMerge != null && vMerge.equals(STMerge.CONTINUE)) {

							vMergedCells.add(c);
						} else {
							return vMergedCells;
						}
					}
				} else {
					// vMerge="continue", ignore the cell because it was already
					// processed
					return Collections.emptyList();
				}
			}
			return vMergedCells;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	protected void visitTable(Visitor v, XWPFTable table, int index) throws Exception {
		float[] colWidths = new float[0];
		try {
			colWidths = XWPFTableUtil.computeColWidths(table);
		} catch (Exception e) {
		}
		v.startVisitTable(table, colWidths);
		//
		boolean firstRow = false;
		boolean lastRow = false;
		List<XWPFTableRow> rows = table.getRows();
		int rowsSize = rows.size();
		for (int i = 0; i < rowsSize; i++) {
			firstRow = (i == 0);
			lastRow = isLastRow(i, rowsSize);
			XWPFTableRow row = rows.get(i);
			visitTableRow(v, row, colWidths, firstRow, lastRow, i, rowsSize);
		}
		//
		v.endVisitTable(table, colWidths);
	}

	@SuppressWarnings("unchecked")
	protected void visitBodyElements(Visitor v, List<IBodyElement> bodyElements) throws Exception {
		for (int i = 0; i < bodyElements.size(); i++) {
			IBodyElement bodyElement = bodyElements.get(i);
			switch (bodyElement.getElementType()) {
			case PARAGRAPH:
				XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
				visitParagraph(v, paragraph, i);
				break;
			case TABLE:
				visitTable(v, (XWPFTable) bodyElement, i);
				break;
			case CONTENTCONTROL:
				ISDTContent c = ((XWPFSDT) bodyElement).getContent();
				Field protectedStringField = XWPFSDTContent.class.getDeclaredField("bodyElements");
				protectedStringField.setAccessible(true);
				List<ISDTContents> fieldValue = (List<ISDTContents>) protectedStringField.get(c);
				List<IBodyElement> b = new ArrayList<IBodyElement>();
				b.addAll((Collection<? extends IBodyElement>) fieldValue);
				visitBodyElements(v, b);
				break;
			default:
				System.out.println(bodyElement.getElementType() + " AUTRES ");
				break;
			}
		}
	}

	protected void visitRun(Visitor v, XWPFRun run, int i) throws Exception {
		CTR ctr = run.getCTR();
		CTRPr rPr = ctr.getRPr();
		boolean hasTexStyles = rPr != null
				&& (rPr.getHighlight() != null || rPr.getStrike() != null || rPr.getDstrike() != null || rPr.getVertAlign() != null);
		StringBuilder text = new StringBuilder();

		// Loop for each element of <w:run text, tab, image etc
		// to keep the order of thoses elements.
		XmlCursor c = ctr.newCursor();
		c.selectPath("./*");
		while (c.toNextSelection()) {
			XmlObject o = c.getObject();

			if (o instanceof CTText) {
				CTText ctText = (CTText) o;
				String tagName = o.getDomNode().getNodeName();
				// Field Codes (w:instrText, defined in spec sec. 17.16.23)
				// come up as instances of CTText, but we don't want them
				// in the normal text output
				if ("w:instrText".equals(tagName)) {

				} else {
					if (hasTexStyles) {
						text.append(ctText.getStringValue());
					} else {
						v.visitText(ctText, run, i);
					}
				}
			} else if (o instanceof CTPTab) {
				v.visitTab((CTPTab) o);
			} else if (o instanceof CTBr) {
				v.visitBR((CTBr) o);
			} else if (o instanceof CTEmpty) {
				// Some inline text elements get returned not as
				// themselves, but as CTEmpty, owing to some odd
				// definitions around line 5642 of the XSDs
				// This bit works around it, and replicates the above
				// rules for that case
				String tagName = o.getDomNode().getNodeName();
				if ("w:tab".equals(tagName)) {
					@SuppressWarnings("deprecation")
					CTTabs tabs = stylesDocument.getParagraphTabs(run.getParagraph());
					v.visitTabs(tabs);
				}
				if ("w:br".equals(tagName)) {
					v.visitBR(null);
				}
				if ("w:cr".equals(tagName)) {
					v.visitBR(null);
				}
			} else if (o instanceof CTDrawing) {
				visitDrawing(v, (CTDrawing) o);
			} else if (o instanceof org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTRPrImpl) {
				// org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTRPrImpl
				// ctrs =
				// (org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTRPrImpl)
				// o;
			} else if (o instanceof org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPictureImpl) {
				org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPictureImpl pic = (org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPictureImpl) o;
				XmlObject[] textBoxObjects = pic.selectPath(
						"declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p");
				for (int j = 0; j < textBoxObjects.length; j++) {
					XWPFParagraph embeddedPara = new XWPFParagraph((CTP) textBoxObjects[j], doc);
					visitParagraph(v, embeddedPara, i);
				}
			} else if (o instanceof XmlAnyTypeImpl) {
				XmlAnyTypeImpl oo = (XmlAnyTypeImpl) o;
				XmlObject[] textBoxObjects = oo
						.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:p");
				for (int j = 0; j < textBoxObjects.length; j++) {
					MyXWPFParagraph embeddedPara = new MyXWPFParagraph(textBoxObjects[j], doc);
					visitParagraph(v, embeddedPara, i);
				}
			}
		}
		if (hasTexStyles && StringUtils.isNotEmpty(text.toString())) {
			v.visitStyleText(run, text.toString());
		}
		c.dispose();
	}

	protected void visitDrawing(Visitor v, CTDrawing drawing) throws Exception {
		List<CTInline> inlines = drawing.getInlineList();
		for (CTInline inline : inlines) {
			visitInline(v, inline);
		}
		List<CTAnchor> anchors = drawing.getAnchorList();
		for (CTAnchor anchor : anchors) {
			visitAnchor(v, anchor);
		}
	}

	protected void visitAnchor(Visitor v, CTAnchor anchor) throws Exception {
		CTGraphicalObject graphic = anchor.getGraphic();

		/*
		 * wp:positionH relativeFrom="column">
		 * <wp:posOffset>-898525</wp:posOffset> </wp:positionH>
		 */
		STRelFromH.Enum relativeFromH = null;
		Float offsetX = null;
		CTPosH positionH = anchor.getPositionH();
		if (positionH != null) {
			relativeFromH = positionH.getRelativeFrom();
			offsetX = DxaUtil.emu2points(positionH.getPosOffset());
		}

		STRelFromV.Enum relativeFromV = null;
		Float offsetY = null;
		CTPosV positionV = anchor.getPositionV();
		if (positionV != null) {
			relativeFromV = positionV.getRelativeFrom();
			offsetY = DxaUtil.emu2points(positionV.getPosOffset());
		}
		STWrapText.Enum wrapText = null;
		CTWrapSquare wrapSquare = anchor.getWrapSquare();
		if (wrapSquare != null) {
			wrapText = wrapSquare.getWrapText();
		}
		visitGraphicalObject(v, graphic, offsetX, relativeFromH, offsetY, relativeFromV, wrapText);
	}

	protected void visitInline(Visitor v, CTInline inline) throws Exception {
		CTGraphicalObject graphic = inline.getGraphic();
		visitGraphicalObject(v, graphic, null, null, null, null, null);
	}

	private void visitGraphicalObject(Visitor v, CTGraphicalObject graphic, Float offsetX, STRelFromH.Enum relativeFromH, Float offsetY,
			STRelFromV.Enum relativeFromV, STWrapText.Enum wrapText) throws Exception {
		if (graphic != null) {
			CTGraphicalObjectData graphicData = graphic.getGraphicData();
			if (graphicData != null) {
				XmlCursor c = graphicData.newCursor();
				c.selectPath("./*");
				while (c.toNextSelection()) {
					XmlObject o = c.getObject();
					if (o instanceof CTPicture) {
						CTPicture picture = (CTPicture) o;
						// extract the picture if needed
						XWPFPictureData data = getPictureData(picture);
						// visit the picture.
						v.visitPicture(picture, data, offsetX, relativeFromH, offsetY, relativeFromV, wrapText);
					}
				}
				c.dispose();
			}
		}
	}

	protected XWPFPictureData getPictureDataByID(String blipId) {
		return doc.getPictureDataByID(blipId);
	}

	public XWPFPictureData getPictureData(CTPicture picture) {
		String blipId = picture.getBlipFill().getBlip().getEmbed();
		return getPictureDataByID(blipId);
	}

	public void start(Visitor v) throws Exception {
		v.startVisitDocument();
		for (XWPFHeader h : doc.getHeaderList()) {
			visitBodyElements(v, h.getBodyElements());
		}
		//
		List<IBodyElement> bodyElements = doc.getBodyElements();
		visitBodyElements(v, bodyElements);
		//
		for (XWPFFooter h : doc.getFooterList()) {
			visitBodyElements(v, h.getBodyElements());
		}
		for (XWPFFootnote n : doc.getFootnotes()) {
			visitBodyElements(v, n.getBodyElements());
		}

		v.endVisitDocument();
	}

}
