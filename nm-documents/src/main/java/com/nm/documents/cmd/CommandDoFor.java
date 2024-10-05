package com.nm.documents.cmd;

import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandFor.ForContext;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandDoFor extends ICommand {

	public void merge(ForContext context, CommandChainArguments root) {
		String key = this.getParsed().getFirst("dofor");
		CommandChainArguments current = context.get(key);
		root.merge(current);
		root.putVarsUnsafe("$index", context.getIndex(key) + "");
		root.putVarsUnsafe("$index_1", (context.getIndex(key) + 1) + "");
	}

	public CommandDoFor(CommandParserResult parsed) {
		super(parsed);
	}

}
