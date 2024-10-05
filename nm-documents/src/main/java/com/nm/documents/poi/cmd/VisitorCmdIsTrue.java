package com.nm.documents.poi.cmd;

import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandIsTrue;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdIsTrue extends VisitorCmdVar {

	public VisitorCmdIsTrue(WordBuilder manager, CommandChainArguments args) {
		super(manager, args);
	}

	@Override
	protected boolean can(CmdResult c) { 
		return c.parsed instanceof CommandIsTrue;
	}

	protected String value(CmdResult c) throws Exception {
		CommandIsTrue my = (CommandIsTrue) c.parsed;
		return my.getValue(args);
	};

}
