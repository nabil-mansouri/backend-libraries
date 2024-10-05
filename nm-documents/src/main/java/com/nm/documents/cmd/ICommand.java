package com.nm.documents.cmd;

/**
 * 
 * @author MANSOURI
 *
 */
public abstract class ICommand {

	protected CommandParserResult parsed;

	public ICommand(CommandParserResult parsed) {
		super();
		this.parsed = parsed;
	}

	public CommandParserResult getParsed() {
		return parsed;
	}

	public void setParsed(CommandParserResult parsed) {
		this.parsed = parsed;
	}

	public static ICommand create(String pattern, String start, String end) {
		CommandParserResult p = CommandParser.getCmdParsed(pattern, start, end);
		if (p.getRoot().trim().equalsIgnoreCase("img")) {
			return new CommandImg(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("table")) {
			return new CommandTable(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("row")) {
			return new CommandRow(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("var")) {
			return new CommandVar(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("positive")) {
			return new CommandPositive(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("istrue")) {
			return new CommandIsTrue(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("if")) {
			return new CommandIf(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("endif")) {
			return new CommandEndIf(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("for")) {
			return new CommandFor(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("endfor")) {
			return new CommandEndFor(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("dofor")) {
			return new CommandDoFor(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("enddofor")) {
			return new CommandEndDoFor(p);
		}
		if (p.getRoot().trim().equalsIgnoreCase("switch")) {
			return new CommandSwitch(p);
		}
		return null;
	}
}
