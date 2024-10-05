package com.nm.documents.aspose.parser;

import org.apache.commons.lang3.StringUtils;

import com.aspose.words.NodeType;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PatternFinderTag implements PatternFinder {
	private String start = "{{";
	private String end = "}}";
	private PatternStateTransactionManager manager = new PatternStateTransactionManager();

	public PatternFinderTag() {

	}

	public PatternFinderTag(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public void accept(PatternState state) {
		if (state.getNode().getNodeType() == NodeType.RUN) {
			state.setText(StringUtils.trim(state.getText()));
			//
			if (StringUtils.containsIgnoreCase(state.getText(), start)) {
				manager.start();
			}
			manager.push(state);
			//
			if (StringUtils.containsIgnoreCase(state.getText(), end)) {
				manager.commit();
			}
		}
	}

	public PatternStateTransactionManager getManager() {
		return manager;
	}

	public String getEnd() {
		return end;
	}

	public String getStart() {
		return start;
	}

	@Override
	public String toString() {
		return "PatternFinderTag [" + (start != null ? "start=" + start + ", " : "") + (end != null ? "end=" + end + ", " : "")
				+ (manager != null ? "manager=" + manager : "") + "]";
	}

}
