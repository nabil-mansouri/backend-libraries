package com.nm.documents.aspose.cmd;

import java.util.ArrayList;
import java.util.Collection;

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
public class CommandChainVar extends CommandChain {

	@Override
	protected CommandChainContext doCmd(CommandChainContext context) throws Exception {
		String src = StringUtils.trim(context.getCmd().getFirst("name"));
		String var = context.getArgs().getVars(src);
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
		if (StringUtils.equalsIgnoreCase(context.getCmd().getRoot(), "var")) {
			return true;
		}
		return false;
	}

}
