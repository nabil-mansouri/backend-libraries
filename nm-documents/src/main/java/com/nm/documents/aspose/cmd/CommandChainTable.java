package com.nm.documents.aspose.cmd;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.aspose.words.Node;
import com.nm.documents.PositionModel;
import com.nm.documents.TableModel;
import com.nm.documents.args.CommandChainContext;
import com.nm.documents.aspose.parser.PatternState;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainTable extends CommandChain {

	@Override
	protected CommandChainContext doCmd(CommandChainContext context) throws Exception {
		String src = context.getCmd().getFirst("src");
		TableModel table = context.getArgs().getTableModel(src);
		//
		Collection<Node> n = new ArrayList<Node>();
		for (PatternState s : context.getTransaction().getStates()) {
			n.add(s.getNode());
		} 
		context.getWord().buildTable(new PositionModel(n),table);
		//
		for (PatternState s : context.getTransaction().getStates()) {
			context.getWord().clear(s);
		}
		return context;
	}

	@Override
	protected boolean accept(CommandChainContext context) {
		if (StringUtils.equalsIgnoreCase(context.getCmd().getRoot(), "table")) {
			return true;
		}
		return false;
	}

}
