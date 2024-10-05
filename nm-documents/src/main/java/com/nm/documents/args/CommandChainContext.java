package com.nm.documents.args;

import com.nm.documents.WordBuilder;
import com.nm.documents.aspose.parser.PatternStateTransaction;
import com.nm.documents.cmd.CommandParserResult;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainContext {
	private CommandParserResult cmd;
	private WordBuilder word;
	private CommandChainArguments args;
	private PatternStateTransaction transaction;
	private CommandChainContext previous;

	public CommandChainContext getPrevious() {
		return previous;
	}

	public void setPrevious(CommandChainContext previous) {
		this.previous = previous;
	}

	public CommandChainContext() {
	}

	public CommandChainArguments getArgs() {
		return args;
	}

	public void setArgs(CommandChainArguments args) {
		this.args = args;
	}

	public WordBuilder getWord() {
		return word;
	}

	public void setWord(WordBuilder word) {
		this.word = word;
	}

	public CommandParserResult getCmd() {
		return cmd;
	}

	public void setCmd(CommandParserResult cmd) {
		this.cmd = cmd;
	}

	public PatternStateTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(PatternStateTransaction transaction) {
		this.transaction = transaction;
	}
}
