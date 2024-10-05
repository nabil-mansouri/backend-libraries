package com.nm.documents.sheets.poi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;

import com.nm.documents.cmd.ICommand;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class VisitorCmd extends VisitorDefault {
	private String start = "{{";
	private String end = "}}";
	private String pattern = "\\{\\{(.*?)\\}\\}";

	protected class CmdResult {
		protected String cmd;
		protected String inner;
		protected ICommand parsed;

		public CmdResult(String cmd, String inner) {
			super();
			this.cmd = cmd;
			this.inner = inner;
			this.parsed = ICommand.create(cmd, start, end);
		}

	}

	public void startVisitCell(Cell cell, int index) {
		try {
			System.out.println(String.format("TESTING RUN: %s", cell.getStringCellValue()));
			Pattern MY_PATTERN = Pattern.compile(pattern);
			Matcher m = MY_PATTERN.matcher(cell.getStringCellValue());
			Collection<CmdResult> all = new ArrayList<CmdResult>();
			while (m.find()) {
				String cmd = m.group(0);
				String inner = m.group(1);
				all.add(new CmdResult(cmd, inner));
			}
			onCmd(cell.getStringCellValue(), cell, index, all);
		} catch (Exception e) {
			// DISCONNECT NODE OR FORMULA CELL
		}
	}

	protected abstract void onCmd(String value, Cell cell, int index, Collection<CmdResult> m);

}
