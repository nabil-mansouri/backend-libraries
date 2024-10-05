package com.nm.documents.poi.cmd;

import java.util.Collection;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import com.nm.documents.TableModel;
import com.nm.documents.TableModelRow;
import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandRow;
import com.nm.documents.poi.WordBuilderPoi;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdRow extends VisitorCmd {
	private final WordBuilder manager;
	private final CommandChainArguments args;
	private XWPFTableRow lastRow;
	private int colNum;

	public VisitorCmdRow(WordBuilder manager, CommandChainArguments args) {
		this.manager = manager;
		this.args = args;
	}

	@Override
	public void startVisitRow(XWPFTableRow row, int rowIndex, boolean headerRow) {
		super.startVisitRow(row, rowIndex, headerRow);
		lastRow = row;
		colNum = -1;
	}

	@Override
	public void startVisitTableCell(XWPFTableCell cell, boolean firstRow, boolean lastRow, boolean firstCol, boolean lastCol,
			List<XWPFTableCell> vMergedCells) {
		super.startVisitTableCell(cell, firstRow, lastRow, firstCol, lastCol, vMergedCells);
		colNum++;
	}

	@Override
	protected void onCmd(CTText ctText, XWPFRun run, int index, Collection<CmdResult> m) {

	}

	@Override
	protected void onCmd(String text, XWPFParagraph paragraph, int index, Collection<CmdResult> m) {
		for (CmdResult c : m) {
			if (c.parsed instanceof CommandRow) {
				CommandRow my = (CommandRow) c.parsed;
				try {
					WordBuilderPoi poi = (WordBuilderPoi) manager;
					// CLEAR
					WordBuilderPoi.removeParagraph(paragraph);
					//
					TableModelRow model = my.getModelRow(args);
					TableModel table = my.getModel(args);
					for (int cpt = 0; cpt < model.size() && colNum < lastRow.getTableCells().size(); cpt++, colNum++) {
						poi.styleCell(lastRow.getTableCells().get(colNum), model.get(cpt), table, false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
