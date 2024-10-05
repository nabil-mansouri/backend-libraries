package com.nm.documents.aspose.cmd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.aspose.words.Node;
import com.nm.documents.PositionModel;
import com.nm.documents.args.CommandChainContext;
import com.nm.documents.aspose.parser.PatternState;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainPositive extends CommandChain {

	@Override
	protected CommandChainContext doCmd(CommandChainContext context) throws Exception {
		Collection<String> keys = context.getCmd().getKeys();
		String key = keys.iterator().next();
		//
		List<String> all = context.getCmd().getAll(key);
		String var = context.getArgs().getVars(key);
		Double value = Double.parseDouble(var);
		if (value >= 0) {
			var = all.get(0);
		} else {
			var = all.get(1);
		}
		//
		Collection<Node> n = new ArrayList<Node>();
		for (PatternState s : context.getTransaction().getStates()) {
			n.add(s.getNode());
		}
		context.getWord().replaceTextbeforeRun(new PositionModel(n), context.getCmd().getFull(), var);
		//
		for (PatternState s : context.getTransaction().getStates()) {
			context.getWord().clear(s);
		}
		return context;
	}

	@Override
	protected boolean accept(CommandChainContext context) {
		if (StringUtils.equalsIgnoreCase(context.getCmd().getRoot(), "positive")) {
			return true;
		}
		return false;
	}

}
