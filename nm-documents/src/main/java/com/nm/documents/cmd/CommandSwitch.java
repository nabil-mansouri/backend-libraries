package com.nm.documents.cmd;

import com.google.common.base.Strings;
import com.nm.documents.args.CommandChainArguments;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandSwitch extends ICommand {

	public CommandSwitch(CommandParserResult parsed) {
		super(parsed);
	}

	public String value(CommandChainArguments args) throws Exception { 
		String cond = parsed.getFirst("switch"); 
		String var = args.getVars(cond);
		if(Strings.isNullOrEmpty(var)){
			return null;
		}else{
			return parsed.getFirst(var);
		}
	}

}
