package com.nm.documents.aspose.cmd;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.aspose.words.Node;
import com.nm.documents.PositionModel;
import com.nm.documents.args.CommandChainContext;
import com.nm.documents.aspose.parser.PatternFinder;
import com.nm.documents.aspose.parser.PatternState;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainIf extends CommandChain {

	@Override
	protected CommandChainContext doCmd(final CommandChainContext context) throws Exception {
		if (context.getCmd().getRoot().equalsIgnoreCase("endif")) {
			Collection<String> keys = context.getPrevious().getCmd().getKeys();
			String key = keys.iterator().next();
			String var = context.getArgs().getVars(key);
			if (BooleanUtils.toBoolean(var)) {
				{
					Collection<Node> n = new ArrayList<Node>();
					for (PatternState s : context.getTransaction().getStates()) {
						n.add(s.getNode());
					}
					context.getWord().replaceTextbeforeRun(new PositionModel(n), context.getCmd().getFull(), "");
				}
				//
				{
					Collection<Node> n = new ArrayList<Node>();
					for (PatternState s : context.getTransaction().getStates()) {
						n.add(s.getNode());
					}
					context.getWord().replaceTextbeforeRun(new PositionModel(n), context.getCmd().getFull(), "");
				}
			} else {
				final Collection<Node> nodes = new ArrayList<Node>();
				context.getWord().iterate(new PatternFinder() {
					private boolean start;

					public void accept(PatternState state) {
						if (state.getNode().equals(context.getPrevious().getTransaction().getStates().iterator().next().getNode())) {
							start = true;
							for (PatternState s : context.getPrevious().getTransaction().getStates()) {
								nodes.add(s.getNode());
							}
						}
						if (state.getNode().equals(context.getTransaction().getStates().iterator().next().getNode())) {
							start = false;
							for (PatternState s : context.getTransaction().getStates()) {
								nodes.add(s.getNode());
							}
						}
						//
						if (start) {
							nodes.add(state.getNode());
						}
					}
				});
				for (Node n : nodes) {
					if (n.getParentNode() != null) {
						n.remove();
					}
				}
			}
		}
		return context;
	}

	@Override
	protected boolean accept(CommandChainContext context) {
		if (StringUtils.equalsIgnoreCase(context.getCmd().getRoot(), "if")
				|| StringUtils.equalsIgnoreCase(context.getCmd().getRoot(), "endif")) {
			return true;
		}
		return false;
	}

}
