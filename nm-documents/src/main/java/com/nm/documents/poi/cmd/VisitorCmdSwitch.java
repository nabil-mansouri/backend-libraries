package com.nm.documents.poi.cmd;

import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandSwitch;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorCmdSwitch extends VisitorCmdVar {

	public VisitorCmdSwitch(WordBuilder manager, CommandChainArguments args) {
		super(manager, args);
	}

	@Override
	protected boolean can(CmdResult c) {
		return c.parsed instanceof CommandSwitch;
	}

	protected String value(CmdResult c) throws Exception {
		CommandSwitch my = (CommandSwitch) c.parsed;
		String value = my.value(args);
		return (value != null) ? value : c.cmd;
	};

}
