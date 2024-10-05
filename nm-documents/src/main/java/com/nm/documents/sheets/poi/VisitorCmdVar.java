package com.nm.documents.sheets.poi;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandVar;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdVar extends VisitorCmd {
	private final CommandChainArguments args;

	public VisitorCmdVar(ExcelBuilderPoi builder, CommandChainArguments args) {
		this.args = args;
	}

	@Override
	protected void onCmd(String value, Cell cell, int index, Collection<CmdResult> m) {
		for (CmdResult c : m) {
			if (can(c)) {
				try {
					String res = StringUtils.replace(cell.getStringCellValue(), c.cmd, value(c));
					ExcelBuilderPoi.setCell(cell, res);
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
