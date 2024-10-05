package com.nm.documents.cmd;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.nm.documents.aspose.parser.PatternStateTransaction;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandParser {
	private static final String SEP = "\\|";
	private static final String SUB_SEP = ":";

	public static String getCmdStripSeparator(String cmd, String start, String end) {
		cmd = StringUtils.substringAfter(StringUtils.trim(cmd), start);
		cmd = StringUtils.substringBefore(StringUtils.trim(cmd), end);
		return cmd;
	}

	public static String getCmdStripSeparator(PatternStateTransaction transaction, String start, String end) {
		String cmd = transaction.getCmd();
		cmd = StringUtils.substringAfter(StringUtils.trim(cmd), start);
		cmd = StringUtils.substringBefore(StringUtils.trim(cmd), end);
		return cmd;
	}

	public static String getCmdWithSeparator(String cmd, String start, String end) {
		cmd = start + StringUtils.substringAfter(StringUtils.trim(cmd), start);
		cmd = StringUtils.substringBefore(StringUtils.trim(cmd), end) + end;
		return cmd;
	}

	public static String getCmdWithSeparator(PatternStateTransaction transaction, String start, String end) {
		String cmd = transaction.getCmd();
		cmd = start + StringUtils.substringAfter(StringUtils.trim(cmd), start);
		cmd = StringUtils.substringBefore(StringUtils.trim(cmd), end) + end;
		return cmd;
	}

	public static String getCmdRoot(PatternStateTransaction transaction, String start, String end) {
		String cmd = getCmdStripSeparator(transaction, start, end);
		String[] s = cmd.split(SEP);
		return (s.length > 0) ? StringUtils.trim(s[0]) : null;
	}

	public static void main(String[] args) {
		// System.out.println(getCmdParsed(transaction, start, end));
	}

	public static CommandParserResult getCmdParsed(String transaction, String start, String end) {
		String cmd = getCmdStripSeparator(transaction, start, end);
		String[] s = cmd.split(SEP);
		CommandParserResult cmdO = new CommandParserResult();
		cmdO.setBefore(transaction);
		cmdO.setFull(getCmdWithSeparator(transaction, start, end));
		Assert.isTrue(s.length > 0);
		cmdO.setRoot(StringUtils.substringBefore(s[0], SUB_SEP));
		if (cmdO.getRoot().equals("if")) {
			System.out.println();
		}
		for (int i = 0; i < s.length; i++) {
			String[] ss = s[i].split(SUB_SEP);
			for (int j = 1; j < ss.length; j++) {
				cmdO.put(ss[0], ss[j]);
			}
		}
		return cmdO;
	}

	public static CommandParserResult getCmdParsed(PatternStateTransaction transaction, String start, String end) {
		String cmd = getCmdStripSeparator(transaction, start, end);
		String[] s = cmd.split(SEP);
		CommandParserResult cmdO = new CommandParserResult();
		cmdO.setBefore(transaction.getCmd());
		cmdO.setFull(getCmdWithSeparator(transaction, start, end));
		Assert.isTrue(s.length > 0);
		cmdO.setRoot(s[0]);
		for (int i = 1; i < s.length; i++) {
			String[] ss = s[1].split(SUB_SEP);
			Assert.isTrue(s.length >= 2);
			for (int j = 1; j < ss.length; j++) {
				cmdO.put(ss[0], ss[j]);
			}
		}
		return cmdO;
	}

	public static boolean isCmdRoot(String toCompare, PatternStateTransaction transaction, String start, String end) {
		return StringUtils.equalsIgnoreCase(getCmdRoot(transaction, start, end), StringUtils.trim(toCompare));
	}
}
