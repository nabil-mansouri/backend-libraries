package com.nm.documents.poi.cmd;

import java.util.Collection;
import java.util.List;

import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.google.common.collect.Lists;
import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandEndIf;
import com.nm.documents.cmd.CommandIf;
import com.nm.documents.poi.WordBuilderPoi;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdIf extends VisitorCmd {
	protected final WordBuilder manager;
	private final CommandChainArguments args;
	private boolean delete = false;
	private boolean beginDelete = false;
	private Collection<Object> toDel = Lists.newArrayList();

	public VisitorCmdIf(WordBuilder manager, CommandChainArguments args) {
		this.manager = manager;
		this.args = args;
	}

	@Override
	protected void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m) {

	}

	public void endVisitTable(XWPFTable table, float[] colWidths) {
		super.endVisitTable(table, colWidths);
		if (delete) {
			toDel.add(table);
		}
	}

	@Override
	public void startVisitParagraph(XWPFParagraph paragraph, int index) {
		super.startVisitParagraph(paragraph, index);
		if (!beginDelete) {
			if (delete) {
				toDel.add(paragraph);
			}
		}
		beginDelete = false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void endVisitRun(XWPFRun run, int index) {
		super.startVisitRun(run, index);
		if (delete) {
			run.getParagraph().removeRun(index);
		}
	}

	private static class State {
		CommandIf begin;
		CommandEndIf end;

	}

	@Override
	protected void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> m) {
		List<State> states = Lists.newArrayList();
		for (CmdResult c : m) {
			if (c.parsed instanceof CommandIf) {
				CommandIf my = (CommandIf) c.parsed;
				try {
					if (!my.test(args)) {
						beginDelete = true;
						delete = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				State s = new State();
				s.begin = my;
				states.add(s);
			} else if (c.parsed instanceof CommandEndIf) {
				CommandEndIf my = (CommandEndIf) c.parsed;
				if (states.isEmpty()) {
					State s = new State();
					s.end = my;
					states.add(s);
				} else {
					states.get(states.size() - 1).end = my;
				}
				delete = false;
			}
		}
		//

		//
		for (State s : states) {
			if (s.begin != null && s.end != null) {
				TextSegement seg1 = paragraph.searchText(s.begin.getParsed().getFull(), new PositionInParagraph());
				TextSegement seg2 = paragraph.searchText(s.end.getParsed().getFull(), new PositionInParagraph());
				for (int i = seg2.getEndRun(); i >= seg1.getBeginRun(); i--) {
					paragraph.removeRun(i);
				}
			} else if (s.begin != null) {
				TextSegement seg = paragraph.searchText(s.begin.getParsed().getFull(), new PositionInParagraph());
				for (int i = seg.getEndRun(); i >= seg.getBeginRun(); i--) {
					paragraph.removeRun(i);
				}
			} else if (s.end != null) {
				TextSegement seg = paragraph.searchText(s.end.getParsed().getFull(), new PositionInParagraph());
				for (int i = seg.getEndRun(); i >= seg.getBeginRun(); i--) {
					paragraph.removeRun(i);
				}
			}
		}
	}

	@Override
	public void endVisitDocument() {
		super.endVisitDocument();
		WordBuilderPoi poi = (WordBuilderPoi) manager;
		for (Object body : toDel) {
			if (body instanceof XWPFParagraph) {
				for (int i = (((XWPFParagraph) body).getRuns().size() - 1); i >= 0; i--) {
					((XWPFParagraph) body).removeRun(i);
				}
				int pos = poi.getDocument().getPosOfParagraph((XWPFParagraph) body);
				poi.getDocument().removeBodyElement(pos);
			}
			if (body instanceof XWPFTable) {
				int pos = poi.getDocument().getPosOfTable((XWPFTable) body);
				poi.getDocument().removeBodyElement(pos);
			}
		}
	}
}
