package com.nm.documents.cmd;

import org.apache.commons.lang3.StringUtils;

import com.nm.documents.args.CommandChainArguments;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandVar extends ICommand {

	public CommandVar(CommandParserResult parsed) {
		super(parsed);
	}

	public String getValue(CommandChainArguments args) throws Exception {
		if (parsed.containsKey("name")) {
			String src = StringUtils.trim(parsed.getFirst("name"));
			return args.getVars(src);
		}else if (parsed.containsKey("var")) {
			String src = StringUtils.trim(parsed.getFirst("var"));
			return args.getVars(src);
		} else {
			String src = StringUtils.trim(parsed.getFirst("n"));
			return args.getVars(src);
		}
	}

}
