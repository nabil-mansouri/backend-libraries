package com.nm.documents.aspose.parser;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PatternStateTransaction {
	private Collection<PatternState> states = new ArrayList<PatternState>();

	public Collection<PatternState> getStates() {
		return states;
	}

	public void setStates(Collection<PatternState> states) {
		this.states = states;
	}

	public String getCmd() {
		Collection<String> all = new ArrayList<String>();
		for (PatternState s : this.states) {
			all.add(StringUtils.trim(s.getText()));
		}
		return StringUtils.join(all, "");
	}

	@Override
	public String toString() {
		return "PatternStateTransaction [" + (states != null ? "states=" + states : "") + "]\n";
	}

}
