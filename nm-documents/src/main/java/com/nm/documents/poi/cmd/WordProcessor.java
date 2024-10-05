package com.nm.documents.poi.cmd;

import java.io.InputStream;
import java.io.OutputStream;

import com.nm.documents.WordBuilder;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.cmd.CommandChainProcessor;
import com.nm.documents.cmd.CommandFor.ForContext;
import com.nm.documents.poi.WordBuilderPoi;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class WordProcessor extends CommandChainProcessor {

	public void process(CommandChainArguments args, InputStream input, OutputStream output) throws Exception {
		WordBuilderPoi word = (WordBuilderPoi) WordBuilder.poi();
		word.createDoc(input);
		POIIterator it = new POIIterator(word.getDocument());
		ForContext forContext = new ForContext();
		//
		boolean redo = true;
		while (redo) {
			VisitorCmdFor v = null;
			VisitorCollection col = new VisitorCollection();
			col.add(new VisitorPrint());
			// MULTI STEP (avoid disconnected values)
			col.add(new VisitorCmdIf(word, args));
			it.start(col);
			// MULTI STEP (avoid disconnected values)
			col.add(v = new VisitorCmdFor(word, args, forContext));
			it.start(col);
			//
			col.add(new VisitorCmdImg(word, args));
			col.add(new VisitorCmdTable(word, args));
			col.add(new VisitorCmdRow(word, args));
			col.add(new VisitorCmdVar(word, args));
			col.add(new VisitorCmdPositive(word, args));
			col.add(new VisitorCmdIsTrue(word, args));
			col.add(new VisitorCmdSwitch(word, args));
			col.add(new VisitorCmdDoFor(word, args, forContext));
			//
			it.start(col);
			redo = v.isRedo();
		}
		// FINAL -> CLEAR
		{
			VisitorCollection col = new VisitorCollection();
			col.add(new VisitorPrint());
			col.add(new VisitorClearBlankPages());
			col.add(new VisitorClearFont("Arial"));
			if (args.hasTranslation()) {
				col.add(new VisitorCmdTranslate(args.getTranslation()));
			}
			it.start(col);
		}
		word.write(output);
		word.close();
		// //UPDATE TOC
		// ByteArrayOutputStream temp = new ByteArrayOutputStream();
		// WordBuilder w = WordBuilder.aspose();
		// w.createDoc(new ByteArrayInputStream(temp.toByteArray()));
		// w.write(output);
		// w.close();

	}
}
