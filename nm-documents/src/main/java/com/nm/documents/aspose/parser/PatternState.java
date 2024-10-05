package com.nm.documents.aspose.parser;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.aspose.words.Node;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PatternState {
	@Override
	public String toString() {
		return "PatternState [" + (text != null ? "text=" + text + ", " : "") + (paragraph != null ? "paragraph=" + paragraph + ", " : "")
				+ (run != null ? "run=" + run + ", " : "") + "indexParagraph=" + indexParagraph + ", indexRun=" + indexRun + "]";
	}

	private String text;
	// ASPOSE
	private Node node;
	// POI
	private XWPFParagraph paragraph;
	private XWPFRun run;
	private int indexParagraph;
	private int indexRun;

	public PatternState() {
	}

	public PatternState(String text, XWPFParagraph paragraph, XWPFRun run, int indexParagraph, int indexRun) {
		super();
		this.text = text;
		this.paragraph = paragraph;
		this.run = run;
		this.indexParagraph = indexParagraph;
		this.indexRun = indexRun;
	}

	public PatternState(String text, Node node) {
		super();
		this.text = text;
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public XWPFParagraph getParagraph() {
		return paragraph;
	}

	public void setParagraph(XWPFParagraph paragraph) {
		this.paragraph = paragraph;
	}

	public XWPFRun getRun() {
		return run;
	}

	public void setRun(XWPFRun run) {
		this.run = run;
	}

	public int getIndexParagraph() {
		return indexParagraph;
	}

	public void setIndexParagraph(int indexParagraph) {
		this.indexParagraph = indexParagraph;
	}

	public int getIndexRun() {
		return indexRun;
	}

	public void setIndexRun(int indexRun) {
		this.indexRun = indexRun;
	}

}
