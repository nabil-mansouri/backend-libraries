package com.nm.documents.sheets.poi;

import java.util.Collection;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.common.collect.Maps;
import com.nm.documents.TableModel;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandRow;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdRow extends VisitorCmd {
	private final CommandChainArguments args;
	private final ExcelBuilderPoi manager;
	private Map<Cell, CommandRow> stack = Maps.newHashMap();

	public VisitorCmdRow(ExcelBuilderPoi builder, CommandChainArguments args) {
		this.args = args;
		this.manager = builder;
	}

	@Override
	public void startVisitWorkBook(Workbook workBook) {
		super.startVisitWorkBook(workBook);
		stack.clear();
	}

	@Override
	protected void onCmd(String value, Cell cell, int index, Collection<CmdResult> m) {
		for (CmdResult c : m) {
			if (c.parsed instanceof CommandRow) {
				CommandRow my = (CommandRow) c.parsed;
				stack.put(cell, my);
			}
		}
	}

	@Override
	public void endVisitWorkBook(Workbook workBook) {
		super.endVisitWorkBook(workBook);
		for (Cell cell : stack.keySet()) {
			try {
				CommandRow my = stack.get(cell);
				TableModel table = my.getModel(args);
				cell.setCellValue("");
				manager.buildTable(cell, table);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
