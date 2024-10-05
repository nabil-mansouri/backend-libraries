package com.nm.documents.poi.cmd;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFFieldRun;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRunTrackChange;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSmartTagRun;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class MyXWPFParagraph extends XWPFParagraph {
	private final XmlAnyTypeImpl any;

	@SuppressWarnings("deprecation")
	public MyXWPFParagraph(XmlObject xml, IBody part) throws Exception {
		super(CTP.Factory.parse(xml.newInputStream()), part);
		System.out.println(xml.xmlText());
		this.any = (XmlAnyTypeImpl) xml;
		if (runs.isEmpty()) {
			XmlCursor c = super.getCTP().newCursor();
			c.selectPath("child::*");
			while (c.toNextSelection()) {
				XmlObject o = c.getObject();
				if (o instanceof CTR) {
					XWPFRun r = new XWPFRun((CTR) o, this);
					runs.add(r);
					iruns.add(r);
				}
				if (o instanceof CTHyperlink) {
					CTHyperlink link = (CTHyperlink) o;
					for (CTR r : link.getRArray()) {
						XWPFHyperlinkRun hr = new XWPFHyperlinkRun(link, r, this);
						runs.add(hr);
						iruns.add(hr);
					}
				}
				if (o instanceof CTSimpleField) {
					CTSimpleField field = (CTSimpleField) o;
					for (CTR r : field.getRArray()) {
						XWPFFieldRun fr = new XWPFFieldRun(field, r, this);
						runs.add(fr);
						iruns.add(fr);
					}
				}
				if (o instanceof CTSdtBlock) {
					XWPFSDT cc = new XWPFSDT((CTSdtBlock) o, part);
					iruns.add(cc);
				}
				if (o instanceof CTSdtRun) {
					XWPFSDT cc = new XWPFSDT((CTSdtRun) o, part);
					iruns.add(cc);
				}
				if (o instanceof CTRunTrackChange) {
					for (CTR r : ((CTRunTrackChange) o).getRArray()) {
						XWPFRun cr = new XWPFRun(r, this);
						runs.add(cr);
						iruns.add(cr);
					}
				}
				if (o instanceof CTSmartTagRun) {
					// Smart Tags can be nested many times.
					// This implementation does not preserve the tagging
					// information
					// buildRunsInOrderFromXml(o);
				}
				System.out.println(o.getClass());
			}
			c.dispose();
		}

	}

	public void setText(String text) {
		XmlObject[] textBoxObjects = any
				.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:t");
		int i = 0;
		for (XmlObject o : textBoxObjects) {
			XmlAnyTypeImpl a = (XmlAnyTypeImpl) o;
			if (i == 0) {
				a.setStringValue(text);
			} else {
				a.setStringValue("");
			}
			i++;
		}
	}

}
