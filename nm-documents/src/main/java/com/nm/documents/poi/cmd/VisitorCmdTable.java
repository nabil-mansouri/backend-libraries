package com.nm.documents.poi.cmd;

import java.util.Collection;

import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.nm.documents.PositionModel;
import com.nm.documents.TableModel;
import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandTable;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdTable extends VisitorCmd {
	private final WordBuilder manager;
	private final CommandChainArguments args;

	public VisitorCmdTable(WordBuilder manager, CommandChainArguments args) {
		this.manager = manager;
		this.args = args;
	}

	@Override
	protected void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m) {

	}

	@Override
	protected void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> m) {
		for (CmdResult c : m) {
			if (c.parsed instanceof CommandTable) {
				CommandTable my = (CommandTable) c.parsed;
				try {
					TableModel model= my.getModel(args);
					TextSegement s = paragraph.searchText(c.cmd, new PositionInParagraph());
					if (s != null) {
						for (int i = s.getEndRun(); i >= s.getBeginRun(); i--) {
							paragraph.removeRun(i);
						}
					}
					XWPFRun run = paragraph.createRun();
					manager.buildTable(new PositionModel(run),model);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
