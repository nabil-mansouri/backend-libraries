package com.nm.documents.sheets.poi;

import java.util.Collection;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.common.collect.Maps;
import com.nm.documents.TableModel;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandTable;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdTable extends VisitorCmd {
	private final CommandChainArguments args;
	private final ExcelBuilderPoi manager;
	//PRESERVE ORDER OF TABLES
	private Map<Cell, CommandTable> stack = Maps.newLinkedHashMap();

	public VisitorCmdTable(ExcelBuilderPoi builder, CommandChainArguments args) {
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
			if (c.parsed instanceof CommandTable) {
				CommandTable my = (CommandTable) c.parsed;
				stack.put(cell, my);
			}
		}
	}

	@Override
	public void endVisitWorkBook(Workbook workBook) {
		super.endVisitWorkBook(workBook);
		for (Cell cell : stack.keySet()) {
			try {
				CommandTable my = stack.get(cell);
				TableModel model = my.getModel(args);
				cell.setCellValue("");
				manager.buildTable(cell, model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
