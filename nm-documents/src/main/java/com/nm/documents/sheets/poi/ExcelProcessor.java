package com.nm.documents.sheets.poi;

import java.io.InputStream;
import java.io.OutputStream;

import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandChainProcessor;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class ExcelProcessor extends CommandChainProcessor {

	public void process(CommandChainArguments args, InputStream input, OutputStream output) throws Exception {
		ExcelBuilderPoi word = new ExcelBuilderPoi();
		word.createDoc(input);
		SheetIterator it = new SheetIterator(word.getWorkBook());
		//
		boolean redo = true;
		while (redo) {
			VisitorCollection col = new VisitorCollection();
			//
			col.add(new VisitorCmdTable(word, args));
			col.add(new VisitorCmdRow(word, args));
			col.add(new VisitorCmdVar(word, args));
			//
			it.start(col);
			redo = false;
		}
		// FINAL -> CLEAR
		{
			VisitorCollection col = new VisitorCollection();
			if (args.hasTranslation()) {
				col.add(new VisitorTranslate(args.getTranslation()));
			}
			it.start(col);
		}
		word.write(output);
		word.close();
	}
}
