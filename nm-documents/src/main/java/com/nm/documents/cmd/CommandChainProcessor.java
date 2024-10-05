package com.nm.documents.cmd;

import java.io.InputStream;
import java.io.OutputStream;

import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.aspose.cmd.CommandChainProcessorAspose;
import com.nm.documents.poi.cmd.WordProcessor;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class CommandChainProcessor {
	public static CommandChainProcessor aspose() {
		return new CommandChainProcessorAspose();
	}

	public static CommandChainProcessor poi() {
		return new WordProcessor();
	}

	public abstract void process(CommandChainArguments args, InputStream input, OutputStream output) throws Exception;

}