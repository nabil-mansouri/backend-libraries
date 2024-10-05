package com.nm.documents.aspose.parser;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PatternStateTransactionManager {
	private boolean editable;
	private PatternStateTransaction transaction = new PatternStateTransaction();
	private Collection<PatternStateTransaction> history = new ArrayList<PatternStateTransaction>();

	public Collection<PatternStateTransaction> getHistory() {
		return history;
	}

	public void start() {
		this.editable = true;
		transaction = new PatternStateTransaction();
	}

	public boolean isEditable() {
		return editable;
	}

	public void push(PatternState state) {
		if (isEditable()) {
			transaction.getStates().add(state);
		}
	}

	public void commit() {
		this.editable = false;
		this.history.add(transaction);
	}

	@Override
	public String toString() {
		return "PatternStateTransactionManager [editable=" + editable + ", "
				+ (transaction != null ? "transaction=" + transaction + ", " : "") + (history != null ? "history=" + history : "") + "]";
	}
}
