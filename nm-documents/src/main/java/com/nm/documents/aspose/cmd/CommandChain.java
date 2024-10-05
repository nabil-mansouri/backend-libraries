package com.nm.documents.aspose.cmd;

import java.util.ArrayList;
import java.util.List;

import com.nm.documents.args.CommandChainContext;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class CommandChain {
	protected abstract CommandChainContext doCmd(CommandChainContext context) throws Exception;

	protected CommandChain next;

	public static CommandChain create() {
		List<CommandChain> chain = new ArrayList<CommandChain>();
		chain.add(new CommandChainImg());
		chain.add(new CommandChainTable());
		chain.add(new CommandChainVar());
		chain.add(new CommandChainPositive());
//		chain.add(new CommandChainIf());
		//
		CommandChain previous = null;
		for (CommandChain c : chain) {
			c.setNext(null);
			if (previous != null) {
				previous.setNext(c);
			}
			previous = c;
		}
		return chain.get(0);
	}

	public CommandChainContext compute(CommandChainContext transaction) throws Exception {
		if (accept(transaction)) {
			doCmd(transaction);
		}
		if (hasNext()) {
			return this.next.compute(transaction);
		} else {
			return transaction;
		}
	}

	public void setNext(CommandChain next) {
		this.next = next;
	}

	protected boolean hasNext() {
		return this.next != null;
	}

	protected abstract boolean accept(CommandChainContext transaction);
}
