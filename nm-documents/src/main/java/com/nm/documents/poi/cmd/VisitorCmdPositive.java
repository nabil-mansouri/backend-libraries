package com.nm.documents.poi.cmd;

import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandPositive;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdPositive extends VisitorCmdVar {

	public VisitorCmdPositive(WordBuilder manager, CommandChainArguments args) {
		super(manager, args);
	}

	@Override
	protected boolean can(CmdResult c) { 
		return c.parsed instanceof CommandPositive;
	}

	protected String value(CmdResult c) throws Exception {
		CommandPositive my = (CommandPositive) c.parsed;
		return my.getValue(args);
	};

}
