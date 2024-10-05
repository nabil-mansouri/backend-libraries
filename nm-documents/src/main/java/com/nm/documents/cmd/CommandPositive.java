package com.nm.documents.cmd;

import java.util.Collection;
import java.util.List;

import com.nm.documents.args.CommandChainArguments;
import com.nm.utils.MathUtilExt;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandPositive extends ICommand {

	public CommandPositive(CommandParserResult parsed) {
		super(parsed);
	}

	public String getValue(CommandChainArguments args) throws Exception {
		Collection<String> keys = parsed.getKeys();
		String key = keys.iterator().next(); 
		List<String> all = parsed.getAll(key);
		String var = args.getVars(key);
		Double value = MathUtilExt.toDouble(var);
		if (value >= 0) {
			var = all.get(0);
		} else {
			var = all.get(1);
		}
		return var;
	}

}
