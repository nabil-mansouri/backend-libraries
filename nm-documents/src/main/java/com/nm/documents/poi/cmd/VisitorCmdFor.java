package com.nm.documents.poi.cmd;

import java.util.Collection;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.google.common.collect.Lists;
import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.args.CommandChainArgumentsImpl;
import com.nm.documents.cmd.CommandEndFor;
import com.nm.documents.cmd.CommandFor;
import com.nm.documents.poi.WordBuilderPoi;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdFor extends VisitorCmd {
	private boolean begin = false;
	private boolean pauseParagraph;
	private boolean skip;
	protected final WordBuilder manager;
	private final CommandChainArguments args;
	private final CommandFor.ForContext forContext;
	private BlockFounded current;
	private List<BlockFounded> blocks = Lists.newArrayList();
	private boolean redo = false;

	private static class BlockFounded {
		XWPFParagraph startAT;
		XWPFParagraph endAt;
		CommandFor begin;
		List<Object> toClone = Lists.newArrayList();
		List<Object> toRemove = Lists.newArrayList();
	}

	public VisitorCmdFor(WordBuilder manager, CommandChainArguments args, CommandFor.ForContext forContext) {
		this.manager = manager;
		this.args = args;
		this.forContext = forContext;
	}

	@Override
	protected void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m) {

	}

	@Override
	public void startVisitTable(XWPFTable table, float[] colWidths) {
		super.startVisitTable(table, colWidths);
		if (begin) {
			current.toClone.add(table);
			current.toRemove.add(table);
		}
		pauseParagraph = true;
	}

	public void endVisitTable(XWPFTable table, float[] colWidths) {
		super.endVisitTable(table, colWidths);
		pauseParagraph = false;
	}

	@Override
	public void startVisitParagraph(XWPFParagraph paragraph, int index) {
		super.startVisitParagraph(paragraph, index);
		if (begin && !pauseParagraph && !skip) {
			current.toClone.add(paragraph);
			current.toRemove.add(paragraph);
		}
		skip = false;
	}

	@Override
	protected void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> m) {
		for (CmdResult c : m) {
			if (c.parsed instanceof CommandFor) {
				current = new BlockFounded();
				blocks.add(current);
				begin = true;
				current.begin = (CommandFor) c.parsed;
				current.startAT = paragraph;
				WordBuilderPoi.removeParagraph(current.startAT);
				current.toRemove.add(paragraph);
				skip = true;
			} else if (c.parsed instanceof CommandEndFor) {
				begin = false;
				current.endAt = paragraph;
				WordBuilderPoi.removeParagraph(current.endAt);
				current.toRemove.add(paragraph);
				skip = true;
			}
		}
	}

	protected void doClones() {
		// DO CLONE AT END OF VISIT
		WordBuilderPoi poi = (WordBuilderPoi) manager;
		for (BlockFounded s : blocks) {
			s.endAt.getCTP().newCursor();
			List<CommandChainArgumentsImpl> args = s.begin.getArgs(this.args);
			//
			for (int i = 0; i < args.size(); i++) {
				CommandChainArgumentsImpl current = args.get(i);
				String key = forContext.put(current);
				forContext.put(key, i);
				int m = s.toClone.size() - 1;
				{
					final XmlCursor cursor = s.endAt.getCTP().newCursor();
					XWPFParagraph clone = poi.getDocument().insertNewParagraph(cursor);
					clone.createRun().setText(String.format("{{dofor:%s}}", key));
					cursor.dispose();
				}
				for (int j = 0; j < m; j++) {
					Object body = s.toClone.get(j);
					final XmlCursor cursor = s.endAt.getCTP().newCursor();
					if (body instanceof XWPFParagraph) {
						XWPFParagraph p = (XWPFParagraph) body;
						XWPFParagraph clone = poi.getDocument().insertNewParagraph(cursor);
						WordBuilderPoi.cloneParagraph(clone, p);
					}
					if (body instanceof XWPFTable) {
						XWPFTable p = (XWPFTable) body;
						XWPFTable clone = poi.getDocument().insertNewTbl(cursor);
						WordBuilderPoi.cloneTable(clone, p);
					}
					cursor.dispose();
				}
				{
					final XmlCursor cursor = s.endAt.getCTP().newCursor();
					XWPFParagraph clone = poi.getDocument().insertNewParagraph(cursor);
					clone.createRun().setText(String.format("{{enddofor:%s}}", key));
					cursor.dispose();
				}
			}
		}
	}

	protected void doRemove() {
		WordBuilderPoi poi = (WordBuilderPoi) manager;
		for (BlockFounded s : blocks) {
			System.out.println(s.startAT.getText());
			// removeParagraph(s.endAt);
			// removeParagraph(s.startAT);
			for (Object body : s.toRemove) {
				if (body instanceof XWPFParagraph) {
					WordBuilderPoi.removeParagraph((XWPFParagraph) body);
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

	@Override
	public void endVisitDocument() {
		super.endVisitDocument();
		doClones();
		doRemove();
		redo = false;
		if (!blocks.isEmpty()) {
			doClear();
			redo = true;
		}
	}

	public boolean isRedo() {
		return redo;
	}

	private void doClear() {
		this.blocks.clear();
	}
}
