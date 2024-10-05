package com.nm.documents;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.nm.documents.aspose.WordBuilderAspose;
import com.nm.documents.aspose.parser.PatternFinder;
import com.nm.documents.aspose.parser.PatternState;
import com.nm.documents.poi.WordBuilderPoi;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class WordBuilder {
	public static WordBuilder poi() {
		return new WordBuilderPoi();
	}

	public static WordBuilder aspose() {
		return new WordBuilderAspose();
	}

	public abstract void removeParagraph(PositionModel index);

	public abstract void clear(PatternState state);

	public abstract void insertText(String text) throws Exception;

	public abstract void close() throws IOException;

	public abstract void iterate(PatternFinder finder) throws Exception;

	public abstract void removeRowHaving(String pattern);

	public abstract void importHtml(String html) throws Exception;

	public abstract void replaceTextbeforeRun(PositionModel position, String before, String after) throws Exception;

	public abstract void insertTextbeforeRun(PositionModel position, String text) throws Exception;

	public abstract void moveBeforeParagraph(PositionModel position) throws Exception;

	public abstract void buildTable(PositionModel position, TableModel tableModel) throws Exception;

	public abstract void moveAfterParagraph(PositionModel position) throws Exception;

	public abstract void createDoc() throws Exception;

	public abstract void createDoc(InputStream input) throws Exception;

	public abstract void newPage() throws Exception;

	public abstract void pushPngImage(PositionModel position, InputStream is, String name) throws Exception;

	public abstract void pushPngImage(PositionModel position, InputStream is, String name, int height) throws Exception;

	public abstract File writeFile() throws Exception;

	public abstract void write(OutputStream output) throws Exception;

	public abstract void writeAsHtml(OutputStream output) throws Exception;

	public abstract void pushPngImage(InputStream fileInputStream, String string) throws Exception;

	public void removePicture(PositionModel positionModel) {

	}

	public void pushPngImage(PositionModel position, ByteArrayInputStream is, String name, Double heightRatio) throws Exception {
		 
	}

}