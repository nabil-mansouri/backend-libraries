package com.nm.documents.cmd;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.nm.documents.args.CommandChainArguments;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandIsTrue extends ICommand {

	public CommandIsTrue(CommandParserResult parsed) {
		super(parsed);
	}

	public String getValue(CommandChainArguments args) throws Exception {
		Collection<String> keys = parsed.getKeys();
		String key = keys.iterator().next();
		List<String> all = parsed.getAll(key);
		String var = args.getVars(key);
		if (BooleanUtils.toBoolean(var)) {
			var = all.get(0);
		} else {
			var = all.get(1);
		}
		//
		if (args.getVars(var) != null) {
			return args.getVars(var);
		}
		return var;
	}

}
