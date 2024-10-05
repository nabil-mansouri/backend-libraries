package com.nm.documents;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.aspose.words.Node;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PositionModel {

	private int paragraphIndex;
	private int runIndex;
	private XWPFRun run;
	private Collection<Node> node = new ArrayList<Node>();
	private XWPFParagraph pargraph;

	public XWPFParagraph getPargraph() {
		return pargraph;
	}

	public void setPargraph(XWPFParagraph pargraph) {
		this.pargraph = pargraph;
	}

	public PositionModel(XWPFRun run) {
		super();
		this.run = run;
	}
	public PositionModel(XWPFParagraph run) {
		super();
		this.pargraph = run;
	}

	public XWPFRun getRun() {
		return run;
	}

	public void setRun(XWPFRun run) {
		this.run = run;
	}

	public PositionModel(Collection<Node> node) {
		super();
		this.node = node;
	}

	public PositionModel(Node node) {
		super();
		this.node.add(node);
	}

	public PositionModel(int paragraphIndex, int runIndex) {
		super();
		this.paragraphIndex = paragraphIndex;
		this.runIndex = runIndex;
	}

	public int getParagraphIndex() {
		return paragraphIndex;
	}

	public void setParagraphIndex(int paragraphIndex) {
		this.paragraphIndex = paragraphIndex;
	}

	public int getRunIndex() {
		return runIndex;
	}

	public void setRunIndex(int runIndex) {
		this.runIndex = runIndex;
	}

	public Collection<Node> getNode() {
		return node;
	}

	public void setNode(Collection<Node> node) {
		this.node = node;
	}

}
