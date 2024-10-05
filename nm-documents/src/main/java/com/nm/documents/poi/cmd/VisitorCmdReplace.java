package com.nm.documents.poi.cmd;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

public class VisitorCmdReplace extends VisitorCmd {

	public VisitorCmdReplace() {
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m) {

		String text = ctText.getStringValue();
		for (CmdResult c : m) {
			// System.out.println("FOUNDED:" + s + "/" + b);
			text = StringUtils.replace(text, c.cmd, "YEAH");
		}
		if (!m.isEmpty()) {
			XWPFRun before = run;
			run = run.getParagraph().insertNewRun(index);
			CTRPr rPr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
			rPr.set(before.getCTR().getRPr());
			run.setText(text);
			//
			run.getParagraph().removeRun(index + 1);
		}
	}

	@Override
	protected void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> all) {
		 
	}
}
