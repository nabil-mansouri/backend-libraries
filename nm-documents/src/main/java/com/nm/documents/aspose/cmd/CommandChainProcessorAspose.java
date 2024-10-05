package com.nm.documents.aspose.cmd;

import java.io.InputStream;
import java.io.OutputStream;

import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.args.CommandChainContext;
import com.nm.documents.aspose.parser.PatternFinderTag;
import com.nm.documents.aspose.parser.PatternStateTransaction;
import com.nm.documents.cmd.CommandChainProcessor;
import com.nm.documents.cmd.CommandParser;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainProcessorAspose extends CommandChainProcessor {

	/* (non-Javadoc)
	 * @see com.intranet.documents.aspose.cmd.CommandChainProcessor#process(com.intranet.documents.aspose.cmd.CommandChainArguments, java.io.InputStream, java.io.OutputStream)
	 */
	public void process(CommandChainArguments args, InputStream input, OutputStream output) throws Exception {
		WordBuilder word = WordBuilder.aspose();
		word.createDoc(input);
		PatternFinderTag tags = new PatternFinderTag();
		word.iterate(tags);
		word.close();
		//
		CommandChain chain = CommandChain.create();
		CommandChainContext previous = null;
		for (PatternStateTransaction tr : tags.getManager().getHistory()) {
			CommandChainContext context = new CommandChainContext();
			context.setPrevious(previous);
			context.setArgs(args);
			context.setCmd(CommandParser.getCmdParsed(tr, tags.getStart(), tags.getEnd()));
			context.setTransaction(tr);
			context.setWord(word);
			chain.compute(context);
			previous = context;
		}
		//
		word.write(output);
	}
}
