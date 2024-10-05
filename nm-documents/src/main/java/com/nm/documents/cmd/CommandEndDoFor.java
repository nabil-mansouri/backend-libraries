package com.nm.documents.cmd;

import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandFor.ForContext;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandEndDoFor extends ICommand {

	public void unmerge(ForContext context, CommandChainArguments root) {

	}

	public CommandEndDoFor(CommandParserResult parsed) {
		super(parsed);
	}

}
