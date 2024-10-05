package com.nm.documents.poi.cmd;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandVar;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdVar extends VisitorCmd {
	protected final WordBuilder manager;
	protected final CommandChainArguments args;

	public VisitorCmdVar(WordBuilder manager, CommandChainArguments args) {
		this.manager = manager;
		this.args = args;
	}

	@Override
	protected void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m) {
		// DO NOTHING
		// for (CmdResult c : m) {
		// if (c.parsed instanceof CommandVar) {
		// CommandVar my = (CommandVar) c.parsed;
		// //
		// String text = ctText.getStringValue();
		// try {
		// text = StringUtils.replace(text, c.cmd, my.getValue(args));
		// XWPFRun before = run;
		// run = run.getParagraph().insertNewRun(index);
		// CTRPr rPr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() :
		// run.getCTR().addNewRPr();
		// rPr.set(before.getCTR().getRPr());
		// run.setText(text);
		// //
		// run.getParagraph().removeRun(index + 1);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
	}

	@Override
	protected void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> m) {
		for (CmdResult c : m) {
			if (can(c)) {
				XWPFRun newRun = null;
				XWPFRun oldRun = null;
				try {
					TextSegement s = paragraph.searchText(c.cmd, new PositionInParagraph());
					if (paragraph instanceof MyXWPFParagraph) {
						MyXWPFParagraph my = (MyXWPFParagraph) paragraph;
						my.setText(value(c));
					} else if (s == null) {
						if (StringUtils.containsIgnoreCase(paragraph.getFootnoteText(), c.cmd)) {
							// TODO update footer note
						} else {
							oldRun = newRun = paragraph.insertNewRun(0);
							//
							CTRPr rPr = newRun.getCTR().isSetRPr() ? newRun.getCTR().getRPr() : newRun.getCTR().addNewRPr();
							rPr.set(oldRun.getCTR().getRPr());
							newRun.setText(value(c));
						}
					} else {
						text = "";
						for (int i = s.getBeginRun(); i <= s.getEndRun(); i++) {
							text += paragraph.getRuns().get(i);
						}
						oldRun = paragraph.getRuns().get(s.getBeginRun());
						newRun = paragraph.insertNewRun(s.getEndRun() + 1);
						//
						CTRPr rPr = newRun.getCTR().isSetRPr() ? newRun.getCTR().getRPr() : newRun.getCTR().addNewRPr();
						rPr.set(oldRun.getCTR().getRPr());
						newRun.setText(StringUtils.replace(text, c.cmd, value(c)));
						//
						for (int i = s.getEndRun(); i >= s.getBeginRun(); i--) {
							paragraph.removeRun(i);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected String value(CmdResult c) throws Exception {
		CommandVar my = (CommandVar) c.parsed;
		return my.getValue(args);
	}

	protected boolean can(CmdResult c) {
		return c.parsed instanceof CommandVar;
	}
}
