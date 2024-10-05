package com.nm.documents.args;

import java.util.ArrayList;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainArgumentsList extends ArrayList<CommandChainArgumentsImpl> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommandChainArguments create() {
		CommandChainArgumentsImpl arg = new CommandChainArgumentsImpl();
		this.add(arg);
		return arg;
	}

}
