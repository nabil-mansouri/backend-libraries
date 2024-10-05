package com.nm.documents.cmd;

import org.apache.commons.lang3.BooleanUtils;

import com.google.common.base.Strings;
import com.nm.documents.args.CommandChainArguments;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandIf extends ICommand {

	public CommandIf(CommandParserResult parsed) {
		super(parsed);
	}

	public boolean test(CommandChainArguments args) throws Exception { 
		String cond = parsed.getFirst("if");
		String var = args.getVars(cond);
		if(Strings.isNullOrEmpty(var)){
			return BooleanUtils.toBoolean(cond);
		}else{
			return BooleanUtils.toBoolean(var);
		}
	}

}
