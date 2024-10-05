package com.nm.documents.poi.cmd;

import java.util.Collection;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandDoFor;
import com.nm.documents.cmd.CommandEndDoFor;
import com.nm.documents.cmd.CommandFor;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdDoFor extends VisitorCmd {
	protected final WordBuilder manager;
	private final CommandChainArguments args;
	private final CommandFor.ForContext forContext;

	public VisitorCmdDoFor(WordBuilder manager, CommandChainArguments args, CommandFor.ForContext forContext) {
		this.manager = manager;
		this.args = args;
		this.forContext = forContext;
	}

	@Override
	protected void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> m) {
		for (CmdResult c : m) {
			if (c.parsed instanceof CommandDoFor) {
				((CommandDoFor) c.parsed).merge(forContext, args);
				removeParagraph(paragraph);
			} else if (c.parsed instanceof CommandEndDoFor) {
				((CommandEndDoFor) c.parsed).unmerge(forContext, args);
				removeParagraph(paragraph);
			}
		}
	}

	private static void removeParagraph(XWPFParagraph paragraph) {
		System.out.println(paragraph.getText());
		for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
			try {
				paragraph.removeRun(i);
			} catch (Exception e) {

			}
		}
		System.out.println(paragraph.getText());
	}

	@Override
	protected void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m) {

	}
}
